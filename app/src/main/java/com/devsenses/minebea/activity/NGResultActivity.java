package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogBreakReason;
import com.devsenses.minebea.dialog.DialogContinueWork;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnApiGetReasonListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.manager.NGManager;
import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.task.TaskBreak;
import com.devsenses.minebea.task.TaskFinish;
import com.devsenses.minebea.utils.Utils;

/**
 * Created by pong.p on 2/2/2016.
 */
public class NGResultActivity extends BaseModelActivity {

    private EditText editOK;
    private TextView textSumNG;
    private TextView textResult;
    private EditText editLastSN;

    private LinearLayout btnDoneLayout;
    private NGManager ngManager;
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
        Utils.clearKeyboard(this);

        if (savedInstanceState == null) {
            initNGManager();
        }
    }

    private void initNGManager() {
        LinearLayout layoutNGList = (LinearLayout) findViewById(R.id.layout_ng_list);
        TextView textAddNG = (TextView) findViewById(R.id.text_add_ng);
        ngManager = new NGManager(NGResultActivity.this, layoutNGList, textAddNG, new NGManager.OnUpdateNGNumberListener() {
            @Override
            public void onUpdateQuantity() {
                textSumNG.setText(String.valueOf(ngManager.getSumQuantityFromNGList()));
                textResult.setText(String.valueOf(getOKQuantity() + getSumNGQuantity()));
            }
        });
        ngManager.loadNGList(employeeNo);
    }

    private void initUI() {
        btnDoneLayout = (LinearLayout) findViewById(R.id.btn_done_layout);

        editOK = (EditText) findViewById(R.id.edit_ok_qty);
        textSumNG = (TextView) findViewById(R.id.text_ng_qty);
        textResult = (TextView) findViewById(R.id.text_result);
        editLastSN = (EditText) findViewById(R.id.edit_last_sn);
    }

    private void initEvent() {
        btnDoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNGProcessCondition()) {
                    sendNGDataToServer();
                }
            }
        });
        editOK.addTextChangedListener(new CustomTextWatcher());
    }

    private boolean checkNGProcessCondition() {
        if (ngManager != null && !ngManager.isNGListComplete()) return false;
        if (getOKQuantity() == 0) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add OK quantity.");
            return false;
        }
        if (editLastSN.getText().toString().isEmpty()) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add last serial number.");
            return false;
        }
        return true;
    }

    private class CustomTextWatcher implements TextWatcher {
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
    }

    private void sendNGDataToServer() {
        TaskFinish.finishProcess(NGResultActivity.this, employeeNo, getOKQuantity(),
                editLastSN.getText().toString(), ngManager.getNGListJsonFormat(), new OnBaseApi() {
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

    private int getNumberFromString(String str) {
        str = str.replaceAll("[\\D]", "");
        return str.isEmpty() ? 0 : Integer.parseInt(str);
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
