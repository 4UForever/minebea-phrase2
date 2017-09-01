package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogEmp_No;
import com.devsenses.minebea.dialog.DialogModelSelect;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.fragment.ScanQRFragment;
import com.devsenses.minebea.listener.OnApiFailureRecoverProcessListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.listener.OnDialogEmp_NoListener;
import com.devsenses.minebea.listener.OnQRCodeHelperListener;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.manager.LoginManager;
import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.LoginModel;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;
import com.devsenses.minebea.model.loginmodel.ProcessLog;
import com.devsenses.minebea.model.loginmodel.SelectedModel;
import com.devsenses.minebea.model.loginmodel.Shift;
import com.devsenses.minebea.model.loginmodel.UserPermission;
import com.devsenses.minebea.model.partmodel.RecoverPartModel;
import com.devsenses.minebea.task.TaskLogin;
import com.devsenses.minebea.task.TaskModel;
import com.devsenses.minebea.utils.Utils;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class ScanQrActivity extends FragmentActivity {
    public static final String EMP_NO_KEY = "empNoKey";

    private String TAG = "ScanQrActivity";

    private ScanQRFragment scanQRFragment;

    private String employeeNo;
    private Bundle bundle;

    private List<Shift> shiftList;
    private List<Model> modelList;
    private DialogEmp_No dialogEmp_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_scan_qr);

        bundle = new Bundle();

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            scanQRFragment = ScanQRFragment.newInstance(ScanQRFragment.SCAN_EMPLOYEE_NO);
            ft.add(R.id.container, scanQRFragment);
            ft.addToBackStack(null);
            ft.commit();

            scanQRFragment.setViewCreatedListener(new ScanQRFragment.OnCompleteListener() {
                @Override
                public void onComplete(ScanQRFragment fragment) {
                    String emp = getIntent().getStringExtra(EMP_NO_KEY);
                    Log.d(TAG, "onComplete: " + emp);
                    if (emp != null) {
                        scanQRFragment.stopCamera();
                        setEmployeeIdAndCallLogin(emp);
                    }
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        initEvent();
    }

    private void initEvent() {
        if (scanQRFragment != null) {
            scanQRFragment.setOnQRReadListener(new OnQRCodeHelperListener() {
                @Override
                public void onQRCodeReadListener(String code) {
                    setEmployeeIdAndCallLogin(code);
                }
            });
            scanQRFragment.setOnTopRightCornerViewClickListener(scanQRFragment.getTopRightCornerView(),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: ");
                            showInputEmployerNODialog();
                        }
                    });
        }
    }

    public void setEmployeeIdAndCallLogin(String qrCode) {
        employeeNo = qrCode;
        callLogin(qrCode);
    }

    private void showInputEmployerNODialog() {
        if (dialogEmp_no == null) {
            dialogEmp_no = new DialogEmp_No(ScanQrActivity.this, new OnDialogEmp_NoListener() {
                @Override
                public void onClickDialog_OK(String emp_no) {
                    Utils.clearKeyboard(ScanQrActivity.this);
                    setEmployeeIdAndCallLogin(emp_no);
                }

                @Override
                public void onClickDialog_Cancel() {
                    scanQRFragment.startCamera();
                }
            });
        }
        if (!dialogEmp_no.isShow()) {
            dialogEmp_no.show();
        }
    }

    private void callLogin(final String qrCode) {
        if (scanQRFragment != null) scanQRFragment.disableCornerButton();
        Utils.clearKeyboard(this);
        LoginManager.loginWithQRCode(ScanQrActivity.this, qrCode, new LoginManager.LoginListener() {
            @Override
            public void onLoginSuccess(LoginModel modelReturn) {
                Utils.clearKeyboard(ScanQrActivity.this);
                UserPermission permission = modelReturn.getDatum().getUserPermission();
                bundle = BundleManager.putUserDataToBundle(bundle, qrCode,
                        permission.getIsWork(), permission.getIsView());

                shiftList = modelReturn.getDatum().getShifts();
                modelList = modelReturn.getDatum().getModels();
                checkForUnfinishedProcess(modelReturn);
            }

            @Override
            public void onFailure(String reason) {
                if (scanQRFragment != null) scanQRFragment.enableCornerButton();
                DialogWithText.showMessage(ScanQrActivity.this, reason, new DialogWithText.OnClickListener() {
                    @Override
                    public void onClick() {
                        scanQRFragment.startCamera();
                    }
                });
            }
        });
    }

    private void checkForUnfinishedProcess(LoginModel modelReturn) {
        if (modelReturn.getDatum().getOnProcess() != null) {
            loadRecoverProcess(modelReturn.getDatum().getOnProcess());
        } else {
            showModelDialog(modelReturn.getDatum().getShifts(), modelReturn.getDatum().getModels(), BundleManager.getIsWork(bundle),
                    BundleManager.getIsView(bundle));
        }
    }

    private void loadRecoverProcess(final Long onProcess) {
        TaskLogin.loadRecoverProcess(ScanQrActivity.this, employeeNo, onProcess,
                new OnApiFailureRecoverProcessListener() {
                    @Override
                    public void onSuccess(ProcessLog processLog) {
                        Log.d("Task", "is working page?" + processLog.isOnWorkingPage());
                        SelectedModel selectedModel = new SelectedModel().initProcessLog(processLog);
                        bundle = BundleManager.putSelectedModelDataToBundle(bundle, selectedModel);
                        if (processLog.isOnWorkingPage()) {
                            startMainActivity(bundle);
                        } else {
                            RecoverPartModel recoverPartModel = new RecoverPartModel(processLog.getPart(), processLog.getWipLot());
                            Log.d("Task","recoverPartModel : "+recoverPartModel.toString());
                            bundle = BundleManager.putRecoverPartAndWIPData(bundle, recoverPartModel);
                            startPartAndWIPActivity(bundle);
                        }
                    }

                    @Override
                    public void onFailure(String reason) {
                        if (scanQRFragment != null) {
                            scanQRFragment.enableCornerButton();
                            scanQRFragment.startCamera();
                        }

                        DialogWithText.showMessage(ScanQrActivity.this,
                                reason + "\nPlease try again or contact administrator.", true,
                                new DialogWithText.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        if (scanQRFragment != null) {
                                            scanQRFragment.disableCornerButton();
                                            scanQRFragment.stopCamera();
                                        }
                                        loadRecoverProcess(onProcess);
                                    }
                                });
                    }
                });
    }

    private void showModelDialog(List<Shift> shiftList, List<Model> modelList, final boolean isWork, final boolean isView) {
        scanQRFragment.stopCamera();
        new DialogModelSelect(this, employeeNo, isWork, isView, shiftList, modelList, new DialogModelSelect.OnSelectedListener() {
            @Override
            public void onWork(Shift shift, Model model, Line line, Process process) {
                Utils.clearKeyboard(ScanQrActivity.this);

                SelectedModel selectedModel = new SelectedModel()
                        .initShift(shift)
                        .initModel(model)
                        .initLine(line)
                        .initProcess(process);
                selectedModel.setOnBreak((long) 0);

                bundle = BundleManager.putSelectedModelDataToBundle(bundle, selectedModel);

                sendSelectModelToServer(selectedModel);
            }

            @Override
            public void onView(Shift shift, Model model, Line line, Process process) {
                Utils.clearKeyboard(ScanQrActivity.this);
                bundle = BundleManager.putLoginModelDataToBundle(bundle, shift, model, line, process);
                startReportActivity(bundle);
            }
        }).show();
    }

    private void sendSelectModelToServer(final SelectedModel selectedModel) {
        TaskModel.sendModel(ScanQrActivity.this, employeeNo, selectedModel, new OnBaseApi() {
            @Override
            public void onSuccess() {
                startPartAndWIPActivity(bundle);
            }

            @Override
            public void onFailure(String reason) {
                if (scanQRFragment != null) scanQRFragment.enableCornerButton();
                DialogWithText.showMessage(ScanQrActivity.this, reason, new DialogWithText.OnClickListener() {
                    @Override
                    public void onClick() {
                        showModelDialog(shiftList, modelList, BundleManager.getIsWork(bundle), BundleManager.getIsView(bundle));
                    }
                });
            }
        });
    }

    private void startReportActivity(final Bundle bundle) {
        startNewIntent(getIntent(bundle, ReportActivity.class));
    }

    private void startMainActivity(final Bundle bundle) {
        startNewIntent(getIntent(bundle, MainActivity.class));
    }

    private void startPartAndWIPActivity(final Bundle bundle) {
        startNewIntent(getIntent(bundle, PartAndWIPActivity.class));
    }

    private Intent getIntent(final Bundle bundle, Class<?> cls) {
        Intent intent = new Intent(ScanQrActivity.this, cls);
        intent.putExtras(bundle);
        return intent;
    }

    private void startNewIntent(Intent intent) {
        this.finish();
        ScanQrActivity.this.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }

}