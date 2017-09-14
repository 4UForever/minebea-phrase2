package com.devsenses.minebea.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogBreakReason;
import com.devsenses.minebea.dialog.DialogNgListDetail;
import com.devsenses.minebea.dialog.DialogStopRunning;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnApiGetReasonListener;
import com.devsenses.minebea.listener.OnButtonAddNumberClickedListener;
import com.devsenses.minebea.listener.OnButtonDeleteNumberClickedListener;
import com.devsenses.minebea.listener.OnDialogStopProcessListener;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.storage.DefaultValue;
import com.devsenses.minebea.storage.PreferenceHelper;
import com.devsenses.minebea.task.TaskBreak;
import com.devsenses.minebea.utils.NetworkUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Horus on 1/30/2015.
 */
public class MainActivity extends ReportActivity {
    private static final String TAG = "MainActivity";

    private CheckBox processCheckBox;
    private TextView lbStatus;

    private EditText editSetup;
    private EditText editDt;

    private NGListData baseNgListData;
    private List<NGDetail> selectedNgList;

    private String startDate;
    private PreferenceHelper preferenceHelper;

    /**
     * THIS CLASS EXTENDS FROM ReportActivity
     * So... the code look weird but it work well.
     * Please look in ReportActivity for fully work flow
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        baseNgListData = BundleManager.getBaseNgList(bundle);
        preferenceHelper = new PreferenceHelper(MainActivity.this, employeeNo);
        //TODO remove under line when production
//        preferenceHelper.clearPreference();

        restoreCacheIfHave();

        initUI();
        setEventLogout();
        initEvent();
        checkProcessStatus(BundleManager.getOnBreak(bundle));

        if (savedInstanceState == null) {
            initDocumentManager();
        }
    }

    private void restoreCacheIfHave() {
        startDate = preferenceHelper.getStartDate();
        selectedNgList = preferenceHelper.getNg1DetailList();
        if (baseNgListData == null) {
            baseNgListData = preferenceHelper.getBaseNgListData();
        }
    }

    private void initUI() {
        lbStatus = (TextView) findViewById(R.id.current_status_text);
        processCheckBox = (CheckBox) findViewById(R.id.process_checkbox);
        editSetup = (EditText) findViewById(R.id.edit_main_setup);
        editDt = (EditText) findViewById(R.id.edit_main_dt);
    }

    @Override
    protected void setEventLogout() {
        //don't want to use this super method.
        //just set make logout layout invisible.
        LinearLayout layoutLogout = (LinearLayout) findViewById(R.id.layoutLogout);
        layoutLogout.setVisibility(View.GONE);
    }

    protected void initEvent() {
        processCheckBox.setOnClickListener(new OnCheckProcessClickListener());

        Button btnSetupAdd = (Button) findViewById(R.id.btn_main_setup_add);
        Button btnSetupDelete = (Button) findViewById(R.id.btn_main_setup_delete);
        Button btnDtAdd = (Button) findViewById(R.id.btn_main_dt_add);
        Button btnDtRemove = (Button) findViewById(R.id.btn_main_dt_delete);

        btnSetupAdd.setOnClickListener(new OnButtonAddNumberClickedListener(editSetup));
        btnSetupDelete.setOnClickListener(new OnButtonDeleteNumberClickedListener(editSetup));

        btnDtAdd.setOnClickListener(new OnButtonAddNumberClickedListener(editDt));
        btnDtRemove.setOnClickListener(new OnButtonDeleteNumberClickedListener(editDt));

        Button btnAddNg1 = (Button) findViewById(R.id.btn_main_add_ng1);
        btnAddNg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNgListDialog();
            }
        });
    }

    private void showNgListDialog() {
        DialogNgListDetail dialog = new DialogNgListDetail(this, baseNgListData, selectedNgList, new DialogNgListDetail.OnDialogNgListListener() {
            @Override
            public void onSavedList(List<NGDetail> ngDetailList) {
                if (ngDetailList != null) {
                    selectedNgList = ngDetailList;
                    preferenceHelper.saveNg1DetailList(selectedNgList);
                }
            }
        });
        dialog.show();
    }

    private class OnCheckProcessClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (processCheckBox.isChecked()) {
                startProcess(processId);
            } else {
                showStopRunningDialog();
            }
        }
    }

    private void checkProcessStatus(Long breakId) {
        if (breakId >= 0) {
            setStatusToBreak();
        } else {
            setStatusToRunning();
        }
    }

    private void setStatusToRunning() {
        lbStatus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.green));
        lbStatus.setText(getResources().getString(R.string.running));
        processCheckBox.setChecked(true);
    }

    private void setStatusToBreak() {
        lbStatus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
        lbStatus.setText(getResources().getString(R.string.stop));
        processCheckBox.setChecked(false);// set button to stop running
    }

    private void setFailureText() {
        lbStatus.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
        lbStatus.setText(getResources().getString(R.string.failure));
    }

    private void showStopRunningDialog() {
        DialogStopRunning dialog = new DialogStopRunning(MainActivity.this, getFormattedProcessText(), new OnDialogStopProcessListener() {
            @Override
            public void onFinish() {
                goToNGPage();
            }

            @Override
            public void onBreak() {
                loadReasonList();
            }
        });
        dialog.cancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                setStatusToRunning();
            }
        });
        dialog.show();
    }

    private void loadReasonList() {
        if (NetworkUtils.isNetworkConnect(MainActivity.this)) {
            TaskBreak.getBreakReasonList(MainActivity.this, employeeNo, new OnApiGetReasonListener() {
                @Override
                public void onSuccess(BreakReasonData breakReasonData) {
                    preferenceHelper.saveBreakReasonData(breakReasonData);
                    showBreakDialog(breakReasonData);
                }

                @Override
                public void onFailure(String reason) {
                    DialogWithText.showMessage(MainActivity.this, reason + "\nPlease try again.", true,
                            new DialogWithText.OnClickListener() {
                                @Override
                                public void onClick() {
                                    showStopRunningDialog();
                                }
                            });
                }
            });
        } else {
            if (preferenceHelper.getBreakReasonData() != null) {
                showBreakDialog(preferenceHelper.getBreakReasonData());
            } else {
                showBreakDialog(DefaultValue.getDefaultBreakReasonData());
            }
        }
    }

    private void showBreakDialog(BreakReasonData breakReasonData) {
        new DialogBreakReason(MainActivity.this, getFormattedProcessText(), breakReasonData,
                new DialogBreakReason.OnBreakReasonDialogListener() {
                    @Override
                    public void onStop(BreakReason breakReason, String description) {
                        startBreak(breakReason, description);
                    }

                    @Override
                    public void onCancel() {
                        showStopRunningDialog();
                    }
                }).show();
    }

    private void startProcess(final long processId) {
        //TODO : save start date to storage
        if (startDate == null || startDate.isEmpty()) {
            startDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", Calendar.getInstance()).toString();
            preferenceHelper.saveStartDate(startDate);
        }
        setStatusToRunning();
    }

    private void startBreak(BreakReason breakReason, String remark) {
        //TODO : save break to storage
        setStatusToBreak();
//        TaskBreak.startBreak(MainActivity.this, employeeNo, breakReason.getId(), description, new OnBaseApi() {
//            @Override
//            public void onSuccess() {
//                setStatusToBreak();
//            }
//
//            @Override
//            public void onFailure(String reason) {
//                DialogWithText.showMessage(MainActivity.this, reason, true,
//                        new DialogWithText.OnClickListener() {
//                            @Override
//                            public void onClick() {
//                                if (currentBreakReasonData != null) {
//                                    showBreakDialog(currentBreakReasonData);
//                                }
//                            }
//                        });
//                setStatusToRunning();
//            }
//        });
    }

    private void goToNGPage() {
        String endDate = DateFormat.format("yyyy-MM-dd HH:mm:ss", Calendar.getInstance()).toString();

        Intent intent = new Intent(MainActivity.this, NGResultActivity.class);
        bundle = BundleManager.putSummaryWorkingData(bundle, selectedNgList, getSetup(), getDt(), startDate, endDate);
        //TODO add break list to bundle
//        bundle = BundleManager.putBreakList(bundle, breakList);
        intent.putExtras(bundle);
        this.finish();
        this.startActivity(intent);
    }

    private String getSetup() {
        return !editSetup.getText().toString().isEmpty() ? editSetup.getText().toString() : "0";
    }

    private String getDt() {
        return !editDt.getText().toString().isEmpty() ? editDt.getText().toString() : "0";
    }
}
