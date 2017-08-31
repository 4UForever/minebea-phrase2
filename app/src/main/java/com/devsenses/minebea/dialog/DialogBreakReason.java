package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.SpinnerTitleAdapter;
import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 2/2/2016.
 */
public class DialogBreakReason extends MaterialDialog.Builder {

    private Spinner spinReason;
    private BreakReasonData breakReasonData;
    private OnBreakReasonDialogListener listener;

    public DialogBreakReason(Context context, String textProcess,BreakReasonData breakReasonData,OnBreakReasonDialogListener listener) {
        super(context);
        this.breakReasonData = breakReasonData;
        this.listener = listener;

        initCustomView();
        initDialogOption();
        initSpinnerModel();

        setProcessNumber(textProcess);
        setPositiveAction();
        setNegativeAction();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_break, null);
        spinReason = (Spinner) view.findViewById(R.id.spinner_reason);
        customView(view, true);
    }

    private void initDialogOption() {
        positiveText("STOP").positiveColor(ContextCompat.getColor(context, R.color.red));
        negativeText("CANCEL");
        cancelable(false);
        autoDismiss(false);
    }

    private void setProcessNumber(String text) {
        TextView textProcess = (TextView) customView.findViewById(R.id.text_process_no);
        textProcess.setText(text);
    }

    private void initSpinnerModel() {
        SpinnerTitleAdapter spinnerModelAdapter = new SpinnerTitleAdapter(context, getReasonList());
        spinReason.setAdapter(spinnerModelAdapter);
    }

    private List<String> getReasonList() {
        List<String> listReason = new ArrayList<>();
        for (int i = 0; i < breakReasonData.getBreakReason().size(); i++) {
            listReason.add( breakReasonData.getBreakReason().get(i).getReason());
        }
        return listReason;
    }

    private int getReasonPosition() {
        return (spinReason != null ? spinReason.getSelectedItemPosition() : 0);
    }

    private BreakReason getSelectedReason() {
        return breakReasonData.getBreakReason().get(getReasonPosition());
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listener.onStop(getSelectedReason());
                autoDismiss(true);
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listener.onCancel();
                autoDismiss(true);
            }
        });
    }

    public interface OnBreakReasonDialogListener{
        void onStop(BreakReason breakReason);
        void onCancel();
    }
}
