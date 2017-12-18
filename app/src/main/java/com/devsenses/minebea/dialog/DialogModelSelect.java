package com.devsenses.minebea.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.SpinnerTitleAdapter;
import com.devsenses.minebea.model.loginmodel.ContinueData;
import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;
import com.devsenses.minebea.model.loginmodel.Shift;
import com.devsenses.minebea.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Horus on 1/28/2015.
 */
public class DialogModelSelect extends MaterialDialog.Builder {
    private Spinner spinnerModel, spinnerLine, spinnerProcess, spinnerShift;
    private EditText editDate;
    private LinearLayout layoutDateAndShift;

    @NonNull
    private final FragmentManager fragmentManager;
    private List<Shift> shiftData;
    private List<Model> modelData;

    private final List<ContinueData> continueDataList;
    private OnSelectedListener listener;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private View newView;
    private View continueView;
    private Button btnNew;
    private Button btnContinue;

    private boolean isStartNewWork = true;
    private RadioGroup radioContinuePeocessGroup;

    public DialogModelSelect(@NonNull Context context, @NonNull FragmentManager fragmentManager,
                             String empNo, boolean isWork, boolean isView, List<Shift> shiftData, List<Model> modelData,
                             List<ContinueData> continueDataList, OnSelectedListener listener) {
        super(context);
        this.fragmentManager = fragmentManager;
        this.shiftData = shiftData;
        this.modelData = modelData;
        this.continueDataList = continueDataList;
        this.listener = listener;

        initCustomView();
        initPagerView();
        initTab();

        initUIDialog();
        initDialogOption(isWork, isView);
        setDateTimeField();
        initEditDate();
        initSpinnerShift();
        initSpinnerModel();

        initContinueList();

        if (isWork) {
            setPositiveAction();
        }
        if (isView) {
            setNegativeAction();
        }

        setEmployeeNo(empNo);
    }

    private void initPagerView() {
        newView = customView.findViewById(R.id.view_emp_select_new);
        continueView = customView.findViewById(R.id.view_emp_select_continue);
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_emp_listselect, null);
        customView(view, true);
    }

    private void initTab() {
        btnNew = (Button) customView.findViewById(R.id.btn_emp_select_new);
        btnContinue = (Button) customView.findViewById(R.id.btn_emp_select_continue);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartNewView();
                hideContinueView();
                isStartNewWork = true;
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showContinueView();
                hideStartNewView();
                isStartNewWork = false;
            }
        });
    }

    private void showStartNewView() {
        newView.setVisibility(View.VISIBLE);
        btnNew.setBackgroundResource(R.color.green);
    }

    private void showContinueView() {
        continueView.setVisibility(View.VISIBLE);
        btnContinue.setBackgroundResource(R.color.green);
    }

    private void hideStartNewView() {
        newView.setVisibility(View.GONE);
        btnNew.setBackgroundResource(R.color.dark_green);
    }

    private void hideContinueView() {
        continueView.setVisibility(View.GONE);
        btnContinue.setBackgroundResource(R.color.dark_green);
    }

    private void initUIDialog() {
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        editDate = (EditText) customView.findViewById(R.id.editDate);
        editDate.setInputType(InputType.TYPE_NULL);
        Calendar newDate = Calendar.getInstance();
        editDate.setText(dateFormatter.format(newDate.getTime()));

        spinnerShift = (Spinner) customView.findViewById(R.id.spinnerShift);
        spinnerModel = (Spinner) customView.findViewById(R.id.spinnerModel);
        spinnerLine = (Spinner) customView.findViewById(R.id.spinnerLineNo);
        spinnerProcess = (Spinner) customView.findViewById(R.id.spinnerProcessNo);

        layoutDateAndShift = (LinearLayout) customView.findViewById(R.id.layout_date_and_shift);
        radioContinuePeocessGroup = (RadioGroup) continueView.findViewById(R.id.radiogroup_unfinish_button);
    }

    private void initDialogOption(boolean isWork, boolean isView) {
        if (isWork) {
            this.positiveText("PROCESS").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        }
        if (isView) {
            this.negativeText("VIEW");
        }
        if (!isWork && isView) {
            layoutDateAndShift.setVisibility(View.GONE);
        }
        this.cancelable(false);
        this.autoDismiss(false);
    }

    private void setEmployeeNo(String qrCode) {
        TextView textEmpNo = (TextView) customView.findViewById(R.id.textEmpNo);
        textEmpNo.setText(qrCode);
    }

    private void initEditDate() {
        editDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePickerDialog.show();
                return false;
            }
        });
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void initSpinnerShift() {
        SpinnerTitleAdapter spinnerProcessAdapter = new SpinnerTitleAdapter(context, getShiftTimeList());
        spinnerShift.setAdapter(spinnerProcessAdapter);
        spinnerProcessAdapter.notifyDataSetChanged();
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

    private void initContinueList() {
        final int length = continueDataList.size();
        if (length > 0) {
            final RadioButton[] rb = new RadioButton[length];
            final int pad = getContext().getResources().getDimensionPixelSize(R.dimen.space_normal);
            for (int i = 0; i < length; i++) {
                ContinueData data = continueDataList.get(i);

                rb[i] = new RadioButton(getContext());
                rb[i].setId(i);
                rb[i].setText("Line " + data.getLineId() + " : " +
                        " Model " + data.getProductTitle() + " : " +
                        " Process " + data.getProcessNumber());
                rb[i].setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        getContext().getResources().getDimension(R.dimen.textSizeNormal));
                rb[i].setPadding(pad, pad, pad, pad);
                radioContinuePeocessGroup.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            }
        }
    }

    private List<String> getShiftTimeList() {
        List<String> listShift = new ArrayList<>();
        for (int i = 0; i < shiftData.size(); i++) {
            listShift.add(shiftData.get(i).getTime());
        }
        return listShift;
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
                Model model;
                Line line;
                Process process;
                String processLog = "";

                Shift shift = shiftData.get(getShiftPosition());
                String date = editDate.getText().toString();

                if (isStartNewWork) {
                    model = modelData.get(getModelPosition());
                    line = model.getLines().get(getLinePosition());
                    process = line.getProcesses().get(getProcessPosition());
                } else {
                    if (getContinueProcessPosition() < 0) {
                        Utils.alert(getContext(),"","Please select continue line");
                        return;
                    }

                    ContinueData data = continueDataList.get(getContinueProcessPosition());
                    process = new Process();
                    process.setId(Integer.parseInt(data.getProcessId()));
                    process.setTitle(data.getProcessTitle());
                    process.setNumber(data.getProcessNumber());
                    process.setCreatedAt("-");
                    process.setUpdatedAt("-");

                    List<Process> processList = new ArrayList<>();
                    processList.add(process);

                    line = new Line();
                    line.setId(Integer.parseInt(data.getLineId()));
                    line.setTitle(data.getLineTitle());
                    line.setProcesses(processList);
                    line.setCreatedAt("-");
                    line.setUpdatedAt("-");

                    List<Line> lineList = new ArrayList<>();
                    lineList.add(line);

                    model = new Model();
                    model.setId(Integer.parseInt(data.getProductId()));
                    model.setTitle(data.getProductTitle());
                    model.setLines(lineList);
                    model.setCreatedAt("-");
                    model.setUpdatedAt("-");

                    processLog = String.valueOf(data.getProcessLogFrom());
                }

                listener.onWork(date, shift, model, line, process, processLog);
                autoDismiss(true);
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                int shiftPosition = getShiftPosition();
                int modelPosition = getModelPosition();
                int linePosition = getLinePosition();
                int processPosition = getProcessPosition();

                String selectedDate = editDate.getText().toString();

                Shift selectedShift = shiftData.get(shiftPosition);
                Model selectedModel = modelData.get(modelPosition);
                Line selectedLine = selectedModel.getLines().get(linePosition);
                Process selectedProcess = selectedLine.getProcesses().get(processPosition);

                listener.onView(selectedDate, selectedShift, selectedModel, selectedLine, selectedProcess);
                autoDismiss(true);
            }
        });
    }

    private int getShiftPosition() {
        return (spinnerShift != null ? spinnerShift.getSelectedItemPosition() : 0);
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

    private int getContinueProcessPosition() {
        return (spinnerProcess != null ? radioContinuePeocessGroup.getCheckedRadioButtonId() : -1);
    }

    public interface OnSelectedListener {
        void onWork(String workingDate, Shift shift, Model model, Line line, Process process, String processLogFrom);

        void onView(String workingDate, Shift shift, Model model, Line line, Process process);
    }

}