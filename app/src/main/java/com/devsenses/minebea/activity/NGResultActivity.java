package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogBreakReason;
import com.devsenses.minebea.dialog.DialogContinueWork;
import com.devsenses.minebea.dialog.DialogNgRemark;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnApiGetReasonListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.manager.NGDetailListManager;
import com.devsenses.minebea.model.FinishModel;
import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.task.TaskBreak;
import com.devsenses.minebea.task.TaskFinish;
import com.devsenses.minebea.utils.Utils;

/**
 * Created by pong.p on 2/2/2016.
 */
public class NGResultActivity extends BaseModelActivity {

    private EditText editSetup;
    private EditText editDt;
    private EditText editOK;
    private TextView textSumNG;
    private TextView textResult;
    private EditText editLastSN;

    private LinearLayout btnDoneLayout;
    private NGDetailListManager ngDetailListManager;
    private BreakReasonData currentBreakReasonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ng);

        initUI();
        initEvent();
        initSummaryWorkingData();
        Utils.clearKeyboard(this);

        if (savedInstanceState == null) {
            initNGDetailListManager();
        }
    }

    private void initNGDetailListManager() {
        ListView listView = (ListView) findViewById(R.id.list_result_ng1_and_ng2);
        ngDetailListManager = new NGDetailListManager(NGResultActivity.this, listView, BundleManager.getNg1List(bundle));
    }

    private void initUI() {
        btnDoneLayout = (LinearLayout) findViewById(R.id.btn_done_layout);

        editSetup = (EditText) findViewById(R.id.edit_work_result_setup);
        editDt = (EditText) findViewById(R.id.edit_work_result_dt);
        editOK = (EditText) findViewById(R.id.edit_ok_qty);
        textSumNG = (TextView) findViewById(R.id.text_ng_qty);
        textResult = (TextView) findViewById(R.id.text_result);
        editLastSN = (EditText) findViewById(R.id.edit_last_sn);
    }

    private void initSummaryWorkingData() {
        editSetup.setText(BundleManager.getSetup(bundle));
        editDt.setText(BundleManager.getDt(bundle));
    }

    private void initEvent() {
        btnDoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNGProcessCondition()) {
                    if (ngDetailListManager.isNg1AndNg2Matched()) {
                        sendNGDataToServer("");
                    } else {
                        showNgRemarkDialog();
                    }
                }
            }
        });
        editOK.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                textResult.setText(String.valueOf(getOKQuantity() + getSumNGQuantity()));
            }
        });
    }

    private boolean checkNGProcessCondition() {
        if (getOKQuantity() == 0) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add OK quantity.");
            return false;
        }
        if (editLastSN.getText().toString().isEmpty()) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add last serial number.");
            return false;
        }
        if (ngDetailListManager.isNg2Empty()) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas input all NG2 value.");
            return false;
        }
        return true;
    }

    private void showNgRemarkDialog() {
        new DialogNgRemark(NGResultActivity.this, new DialogNgRemark.OnConfirmNgDialogListener() {
            @Override
            public void onConfirm(String remark) {
                sendNGDataToServer(remark);
            }
        }).show();
    }

    private void sendNGDataToServer(@Nullable String remark) {
        //TODO fixed ng1 and 2 send data
        FinishModel model = new FinishModel();
        model.setQrCode(employeeNo);
        model.setOkQty(getOKQuantity());
        model.setLastSerialNo(editLastSN.getText().toString());
        model.setSetup(getSetup());
        model.setDt(getDt());
        model.setNgs(ngDetailListManager.getNgSummaryJsonFormatted());
        model.setBreaks("[]");
        model.setRemark(remark);

        TaskFinish.finishProcess(NGResultActivity.this, model, new OnBaseApi() {
            @Override
            public void onSuccess() {
                showContinueDialog();
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showAlertWithBreak(NGResultActivity.this, reason, new DialogWithText.OnClickListener() {
                    @Override
                    public void onClick() {
                        loadReasonList();
                    }
                });
            }
        });
    }

    private int getOKQuantity() {
        return getNumberFromString(editOK.getText().toString());
    }

    private int getSumNGQuantity() {
        return getNumberFromString(textSumNG.getText().toString());
    }

    private int getSetup() {
        return getNumberFromString(editSetup.getText().toString());
    }

    private int getDt() {
        return getNumberFromString(editDt.getText().toString());
    }

    private int getNumberFromString(String str) {
        try {
            str = str.replaceAll("[\\D]", "");
            return str.isEmpty() ? 0 : Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void showContinueDialog() {
        new DialogContinueWork(NGResultActivity.this, BundleManager.getProcessTitle(bundle) + " : " +
                BundleManager.getProcessNumber(bundle), new DialogContinueWork.OnDialogContinueWorkListener() {
            @Override
            public void onContinue() {
                goToLoginPageWithInstanceLogin(employeeNo);
            }

            @Override
            public void onLogout() {
                goToLoginPage();
            }
        }).show();
    }

    private void goToLoginPageWithInstanceLogin(String empNo) {
        Intent intent = new Intent(NGResultActivity.this, ScanQrActivity.class);
        intent.putExtra(ScanQrActivity.EMP_NO_KEY, empNo);
        startNewIntent(intent);
    }

    private void goToLoginPage() {
        Intent intent = new Intent(NGResultActivity.this, ScanQrActivity.class);
        startNewIntent(intent);
    }

    private void startNewIntent(Intent intent) {
        this.finish();
        this.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void loadReasonList() {
        TaskBreak.getBreakReasonList(NGResultActivity.this, employeeNo, new OnApiGetReasonListener() {
            @Override
            public void onSuccess(BreakReasonData breakReasonData) {
                showBreakDialog(breakReasonData);
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(NGResultActivity.this, reason + "\nPlease try again.", true, null);
            }
        });
    }

    private void showBreakDialog(BreakReasonData breakReasonData) {
        this.currentBreakReasonData = breakReasonData;
        new DialogBreakReason(NGResultActivity.this, getFormattedProcessText(), breakReasonData,
                new DialogBreakReason.OnBreakReasonDialogListener() {
                    @Override
                    public void onStop(BreakReason breakReason, String description) {
                        startBreak(breakReason, description);
                    }

                    @Override
                    public void onCancel() {
//                        showStopRunningDialog();
                    }
                }).show();
    }

    private void startBreak(BreakReason breakReason, String description) {
        TaskBreak.startBreak(NGResultActivity.this, employeeNo, breakReason.getId(), description, new OnBaseApi() {
            @Override
            public void onSuccess() {
                DialogWithText.showMessage(NGResultActivity.this, "You are breaking now.\n" +
                        "Please wait until the process before your are finish", new DialogWithText.OnClickListener() {
                    @Override
                    public void onClick() {
                        startMainActivity(bundle);
                    }
                });
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(NGResultActivity.this, reason + "\nPlease try again.", true,
                        new DialogWithText.OnClickListener() {
                            @Override
                            public void onClick() {
                                if (currentBreakReasonData != null) {
                                    showBreakDialog(currentBreakReasonData);
                                }
                            }
                        });
            }
        });
    }

    private void startMainActivity(final Bundle bundle) {
        Intent mainIntent = new Intent(NGResultActivity.this, MainActivity.class);
        mainIntent.putExtras(bundle);
        NGResultActivity.this.startActivity(mainIntent);
        this.finish();
    }
}
