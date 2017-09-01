package com.devsenses.minebea.dialog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;

/**
 * Created by Horus on 1/28/2015.
 */
public class DialogWithText extends MaterialDialog.Builder {

    private OnClickListener listener;

    public DialogWithText(@NonNull Context context, String message) {
        super(context);

        initCustomView();
        initText(message);
        initDialogOption();

        positiveText("OK").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        initEventClick();
    }

    public DialogWithText(@NonNull Context context, String message, OnClickListener listener) {
        super(context);
        this.listener = listener;

        initCustomView();
        initText(message);
        initDialogOption();

        positiveText("OK").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        initEventClick();
    }

    private void initCustomView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_loginfaied, null);
        customView(view, true);
    }

    private void initText(String message) {
        TextView text = (TextView) customView.findViewById(R.id.textDialogFailed);
        text.setText(message);
    }

    private void initDialogOption() {
        autoDismiss(true);
        cancelable(false);
    }

    void initEventClick() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
    }

    public static void showMessage(@NonNull Context context, String message) {
        DialogWithText dialog = new DialogWithText(context, message);
        dialog.show();
    }

    public static void showMessage(@NonNull Context context, String message, OnClickListener listener) {
        DialogWithText dialog = new DialogWithText(context, message, listener);
        dialog.show();
    }

    public static void showMessage(@NonNull Context context, String message, boolean hasCancel, OnClickListener listener) {
        DialogWithText dialog = new DialogWithText(context, message, listener);
        if (hasCancel) {
            dialog.negativeText("CANCEL");
            dialog.cancelable(true);
        }
        dialog.show();
    }

    public static void showAlertWithBreak(@NonNull Context context, String message, final OnClickListener listener) {
        final DialogWithText dialog = new DialogWithText(context, message);
        dialog.negativeText("BREAK").negativeColor(ContextCompat.getColor(context, R.color.red));
        dialog.buttonsGravity(GravityEnum.CENTER);
        dialog.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (listener != null) {
                    listener.onClick();
                }
                dialog.autoDismiss(true);
            }
        });
        dialog.show();
    }

    public interface OnClickListener {
        void onClick();
    }

}
