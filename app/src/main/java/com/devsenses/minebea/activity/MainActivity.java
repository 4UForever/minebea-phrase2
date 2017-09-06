package com.devsenses.minebea.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogBreakReason;
import com.devsenses.minebea.dialog.DialogStopRunning;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnApiGetReasonListener;
import com.devsenses.minebea.listener.OnBaseApi;
import com.devsenses.minebea.listener.OnButtonAddNumberClickedListener;
import com.devsenses.minebea.listener.OnButtonDeleteNumberClickedListener;
import com.devsenses.minebea.listener.OnDialogStopProcessListener;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.task.TaskBreak;
import com.devsenses.minebea.task.TaskProcess;

/**
 * Created by Horus on 1/30/2015.
 */
public class MainActivity extends ReportActivity {
    private static final String TAG = "MainActivity";

    private CheckBox processCheckBox;
    private TextView lbStatus;

    private BreakReasonData currentBreakReasonData;
    private EditText editSetup;
    private EditText editDt;

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

        initUI();
        setEventLogout();
        initEvent();
        checkProcessStatus(BundleManager.getOnBreak(bundle));

        if (savedInstanceState == null) {
            initDocumentManager();
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

    private void startProcess(final long processId) {
        TaskProcess.startProcess(MainActivity.this, employeeNo, modelId, lineId, processId, new TaskProcess.StartProcessListener() {
            @Override
            public void onStartSuccess() {
                setStatusToRunning();
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(MainActivity.this, reason);
                setStatusToBreak();
            }
        });
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

    private void goToNGPage() {
        Intent intent = new Intent(MainActivity.this, NGResultActivity.class);
        intent.putExtras(bundle);
        this.finish();
        this.startActivity(intent);
    }

    private void loadReasonList() {
        TaskBreak.getBreakReasonList(MainActivity.this, employeeNo, new OnApiGetReasonListener() {
            @Override
            public void onSuccess(BreakReasonData breakReasonData) {
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
    }

    private void showBreakDialog(BreakReasonData breakReasonData) {
        this.currentBreakReasonData = breakReasonData;
        new DialogBreakReason(MainActivity.this, getFormattedProcessText(), breakReasonData,
                new DialogBreakReason.OnBreakReasonDialogListener() {
                    @Override
                    public void onStop(BreakReason breakReason) {
                        startBreak(breakReason);
                    }

                    @Override
                    public void onCancel() {
                        showStopRunningDialog();
                    }
                }).show();
    }

    private void startBreak(BreakReason breakReason) {
        TaskBreak.startBreak(MainActivity.this, employeeNo, breakReason.getId(), new OnBaseApi() {
            @Override
            public void onSuccess() {
                setStatusToBreak();
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(MainActivity.this, reason, true,
                        new DialogWithText.OnClickListener() {
                            @Override
                            public void onClick() {
                                if (currentBreakReasonData != null) {
                                    showBreakDialog(currentBreakReasonData);
                                }
                            }
                        });
                setStatusToRunning();
            }
        });
    }
}
