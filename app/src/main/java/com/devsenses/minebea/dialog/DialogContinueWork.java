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

/**
 * Created by pong.p on 2/4/2016.
 */
public class DialogContinueWork extends MaterialDialog.Builder {

    private OnDialogContinueWorkListener listener;

    public DialogContinueWork(@NonNull Context context, String textProcess, OnDialogContinueWorkListener listener) {
        super(context);
        this.listener = listener;

        initCustomView();
        initDialogOption();

        setPositiveAction();
        setNegativeAction();
        setProcessNumber(textProcess);
    }

    private void initDialogOption() {
        positiveText("CONTINUE");
        negativeText("LOGOUT").negativeColor(ContextCompat.getColor(context, R.color.red));

        cancelable(false);
        autoDismiss(true);
        buttonsGravity(GravityEnum.CENTER);
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_continue_work, null);
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
                listener.onContinue();
                autoDismiss(true);
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                listener.onLogout();
                autoDismiss(true);
            }
        });
    }

    public interface OnDialogContinueWorkListener{
        void onContinue();
        void onLogout();
    }
}
