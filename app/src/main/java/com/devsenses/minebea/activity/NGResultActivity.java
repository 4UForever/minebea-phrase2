package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogContinueWork;
import com.devsenses.minebea.dialog.DialogNgRemark;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.manager.NGDetailListManager;
import com.devsenses.minebea.model.FinishModel;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.storage.PreferenceHelper;
import com.devsenses.minebea.task.TaskFinish;
import com.devsenses.minebea.utils.Utils;
import com.google.gson.Gson;

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
    private EditText editWipLastLot;
    private CheckBox checkWipLastLot;
    private Button btnAddNewNg;

    private LinearLayout btnDoneLayout;
    private NGDetailListManager ngDetailListManager;
    private BreakReasonData currentBreakReasonData;

    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ng);

        preferenceHelper = new PreferenceHelper(NGResultActivity.this, employeeNo);
        initUI();
        initEvent();
        initSummaryWorkingData();
        Utils.clearKeyboard(this);

        if (savedInstanceState == null) {
            initNGDetailListManager();
        }
    }

    private void initNGDetailListManager() {
        RecyclerView listView = (RecyclerView) findViewById(R.id.list_result_ng1_and_ng2);
        RecyclerView additionalListView = (RecyclerView) findViewById(R.id.list_result_additional_ng1_and_ng2);
        ngDetailListManager = new NGDetailListManager(listView, BundleManager.getNg1List(bundle),
                additionalListView, preferenceHelper.getBaseNgListData().getNGList());
        ngDetailListManager.setOnNg2ChangeListener(new NGDetailListManager.OnNg2SumChangeListener() {
            @Override
            public void onNg2SumUpdate(String sumNg2) {
                textSumNG.setText(sumNg2);
                updateResultQty();
            }
        });
    }

    private void initUI() {
        btnDoneLayout = (LinearLayout) findViewById(R.id.btn_done_layout);

        editSetup = (EditText) findViewById(R.id.edit_work_result_setup);
        editDt = (EditText) findViewById(R.id.edit_work_result_dt);
        editOK = (EditText) findViewById(R.id.edit_ok_qty);
        textSumNG = (TextView) findViewById(R.id.text_ng_qty);
        textResult = (TextView) findViewById(R.id.text_result);
        editLastSN = (EditText) findViewById(R.id.edit_last_sn);
        editWipLastLot = (EditText) findViewById(R.id.edit_finish_wip_last_lot);
        checkWipLastLot = (CheckBox) findViewById(R.id.check_finish_wip_last_lot);

        btnAddNewNg = (Button) findViewById(R.id.btn_result_add_new_ng);
    }

    private void initSummaryWorkingData() {
        editSetup.setText(BundleManager.getSetup(bundle));
        editDt.setText(BundleManager.getDt(bundle));
        editLastSN.setText(preferenceHelper.getLastSerialNo());
    }

    private void initEvent() {
        btnDoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNGProcessCondition()) {
                    if (ngDetailListManager.isNg1AndNg2Matched(ngDetailListManager.getFinalNgSummaryList())) {
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
                updateResultQty();
            }
        });
        checkWipLastLot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editWipLastLot.setEnabled(true);
                    editWipLastLot.setBackgroundColor(getResources().getColor(android.R.color.white));
                } else {
                    editWipLastLot.setText("");
                    editWipLastLot.setEnabled(false);
                    editWipLastLot.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                }
            }
        });
        btnAddNewNg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngDetailListManager.addNewAdditionalNgSummary();
            }
        });
    }

    private boolean checkNGProcessCondition() {
        if (getOKQuantity() == 0) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add OK quantity.");
            return false;
        } else if (editLastSN.getText().toString().isEmpty()) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas add last serial number.");
            return false;
//        }else if (ngDetailListManager.isNg2Empty()) {
//            DialogWithText.showMessage(NGResultActivity.this, "Pleas input all NG2 value.");
//            return false;
//        }else if (ngDetailListManager.isNg2WrongNumber()) {
//            DialogWithText.showMessage(NGResultActivity.this, "Pleas input only 1 or 0 at Ng2 value.");
//            return false;
        } else if (checkWipLastLot.isChecked() && getWipLastLot().isEmpty()) {
            DialogWithText.showMessage(NGResultActivity.this, "Pleas input WIP lot for last shift.");
            return false;
        }
        return true;
    }

    private void showNgRemarkDialog() {
        new DialogNgRemark(NGResultActivity.this, preferenceHelper.getRemark(), new DialogNgRemark.OnConfirmNgDialogListener() {
            @Override
            public void onConfirm(String remark) {
                preferenceHelper.saveRemark(remark);
                sendNGDataToServer(remark);
            }
        }).show();
    }

    private void sendNGDataToServer(@Nullable String remark) {
        Log.d("MineBea", ngDetailListManager.getNgSummaryJsonFormatted(ngDetailListManager.getFinalNgSummaryList()));
        FinishModel model = new FinishModel();
        model.setQrCode(employeeNo);
        model.setOkQty(getOKQuantity());
        model.setLastSerialNo(editLastSN.getText().toString());
        model.setSetup(getSetup());
        model.setDt(getDt());
        model.setNgs(ngDetailListManager.getNgSummaryJsonFormatted(ngDetailListManager.getFinalNgSummaryList()));
        model.setBreaks(new Gson().toJsonTree(BundleManager.getBreakStepList(bundle)).toString());
        model.setRemark(remark);
        model.setStartDate(BundleManager.getStartDate(bundle));
        model.setEndDate(BundleManager.getEndDate(bundle));
        model.setWipQty(getWipLastLot());

        TaskFinish.finishProcess(NGResultActivity.this, model, new OnBaseApi() {
            @Override
            public void onSuccess() {
                showContinueDialog();
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(NGResultActivity.this, reason);
            }
        });
    }

    private void updateResultQty() {
        textResult.setText(String.valueOf(getOKQuantity() + getSumNGQuantity()));
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

    private String getWipLastLot() {
        try {
            return editWipLastLot.getText().toString();
        } catch (NullPointerException e) {
            return "";
        }
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
        preferenceHelper.clearPreference();

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
}
