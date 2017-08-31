package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.listener.OnDialogStopProcessListener;

/**
 * Created by Horus on 2/2/2015.
 */
public class DialogStopRunning extends MaterialDialog.Builder {

    OnDialogStopProcessListener listener;

    public DialogStopRunning(@NonNull Context context, String textProcess, OnDialogStopProcessListener listener) {
        super(context);
        this.listener = listener;

        initCustomView();
        initDialogOption();

        setPositiveAction();
        setNegativeAction();
        setProcessNumber(textProcess);
    }

    private void initDialogOption() {
        positiveText("FINISH").positiveColor(ContextCompat.getColor(context,R.color.red));
        negativeText("BREAK");
        cancelable(true);
        autoDismiss(true);
        buttonsGravity(GravityEnum.CENTER);
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_stop_running, null);
        customView(view, true);
    }

    private void setProcessNumber(String textProcess) {
        TextView lbProcessNumber = (TextView) customView.findViewById(R.id.textViewProcessNumber);
        lbProcessNumber.setText(textProcess);
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listener.onFinish();
                autoDismiss(true);
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listener.onBreak();
                autoDismiss(true);
            }
        });
    }
}