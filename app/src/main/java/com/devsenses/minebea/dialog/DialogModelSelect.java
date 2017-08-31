package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.SpinnerTitleAdapter;
import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Horus on 1/28/2015.
 */
public class DialogModelSelect extends MaterialDialog.Builder {
    private Spinner spinnerModel, spinnerLine, spinnerProcess;

    private List<Model> modelData;

    private OnSelectedListener listener;

    public DialogModelSelect(@NonNull Context context, String empNo, boolean isWork, boolean isView, List<Model> modelData,
                             OnSelectedListener listener) {
        super(context);
        this.modelData = modelData;
        this.listener = listener;

        initCustomView();
        initUIDialog();
        initDialogOption(isWork, isView);
        initSpinnerModel();

        if (isWork) {
            setPositiveAction();
        }
        if (isView) {
            setNegativeAction();
        }

        setEmployeeNo(empNo);
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_emp_listselect, null);
        customView(view, true);
    }

    private void initUIDialog() {
        spinnerModel = (Spinner) customView.findViewById(R.id.spinnerModel);
        spinnerLine = (Spinner) customView.findViewById(R.id.spinnerLineNo);
        spinnerProcess = (Spinner) customView.findViewById(R.id.spinnerProcessNo);
    }

    private void initDialogOption(boolean isWork, boolean isView) {
        if (isWork) {
            this.positiveText("PROCESS").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        }
        if (isView) {
            this.negativeText("VIEW");
        }
        this.cancelable(false);
        this.autoDismiss(false);
    }

    private void setEmployeeNo(String qrCode) {
        TextView textEmpNo = (TextView) customView.findViewById(R.id.textEmpNo);
        textEmpNo.setText(qrCode);
    }

    private void initSpinnerModel() {
        SpinnerTitleAdapter spinnerModelAdapter = new SpinnerTitleAdapter(context, getModelTitleList());
        spinnerModel.setAdapter(spinnerModelAdapter);
        spinnerModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerLineByModel(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initSpinnerLineByModel(final int modelPosition) {
        SpinnerTitleAdapter spinnerLineAdapter = new SpinnerTitleAdapter(context,
                getLineTitleList(modelData.get(modelPosition).getLines()));
        spinnerLine.setAdapter(spinnerLineAdapter);
        spinnerLine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerProcessByLine(modelPosition, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerLineAdapter.notifyDataSetChanged();
    }

    private void initSpinnerProcessByLine(int modelPosition, int linePosition) {
        List<Process> processList = modelData.get(modelPosition).getLines().get(linePosition).getProcesses();
        SpinnerTitleAdapter spinnerProcessAdapter = new SpinnerTitleAdapter(context, getProcessTitleList(processList));
        spinnerProcess.setAdapter(spinnerProcessAdapter);
        spinnerProcessAdapter.notifyDataSetChanged();
    }

    private List<String> getModelTitleList() {
        List<String> listModel = new ArrayList<>();
        for (int i = 0; i < modelData.size(); i++) {
            listModel.add(modelData.get(i).getTitle());
        }
        return listModel;
    }

    private List<String> getLineTitleList(List<Line> lineList) {
        List<String> listLine = new ArrayList<>();
        for (int i = 0; i < lineList.size(); i++) {
            listLine.add(lineList.get(i).getTitle());
        }
        return listLine;
    }

    private List<String> getProcessTitleList(List<Process> processList) {
        List<String> listLine = new ArrayList<>();
        for (int i = 0; i < processList.size(); i++) {
            listLine.add(processList.get(i).getTitle());
        }
        return listLine;
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                int modelPosition = getModelPosition();
                int linePosition = getLinePosition();
                int processPosition = getProcessPosition();

                Model selectedModel = modelData.get(modelPosition);
                Line selectedLine = selectedModel.getLines().get(linePosition);
                Process selectedProcess = selectedLine.getProcesses().get(processPosition);

                listener.onWork(selectedModel, selectedLine, selectedProcess);
                autoDismiss(true);
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                int modelPosition = getModelPosition();
                int linePosition = getLinePosition();
                int processPosition = getProcessPosition();

                Model selectedModel = modelData.get(modelPosition);
                Line selectedLine = selectedModel.getLines().get(linePosition);
                Process selectedProcess = selectedLine.getProcesses().get(processPosition);

                listener.onView(selectedModel, selectedLine, selectedProcess);
                autoDismiss(true);
            }
        });
    }

    private int getModelPosition() {
        return (spinnerModel != null ? spinnerModel.getSelectedItemPosition() : 0);
    }

    private int getLinePosition() {
        return (spinnerLine != null ? spinnerLine.getSelectedItemPosition() : 0);
    }

    private int getProcessPosition() {
        return (spinnerProcess != null ? spinnerProcess.getSelectedItemPosition() : 0);
    }

    public interface OnSelectedListener {
        void onWork(Model model, Line line, Process process);

        void onView(Model model, Line line, Process process);
    }

}