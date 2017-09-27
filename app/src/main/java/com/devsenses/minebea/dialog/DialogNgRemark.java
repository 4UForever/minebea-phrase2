package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.utils.Utils;

/**
 * Created by USER on 10/9/2560.
 */

public class DialogNgRemark extends MaterialDialog.Builder {
    public interface OnConfirmNgDialogListener {
        void onConfirm(String description);
    }

    private final String remark;
    private final OnConfirmNgDialogListener listener;
    private EditText editBox;

    public DialogNgRemark(@NonNull Context context, String remark, @NonNull OnConfirmNgDialogListener listener) {
        super(context);
        this.remark = remark;
        this.listener = listener;

        initCustomView();
        initDialogOption();
        initEditBox();

        setPositiveAction();
        setNegativeAction();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_ng_remark, null);
        customView(view, true);
    }

    private void initDialogOption() {
        positiveText("FINISH").positiveColor(ContextCompat.getColor(context, R.color.red));
        negativeText("CANCEL");
        cancelable(false);
        autoDismiss(false);
    }

    private void initEditBox() {
        editBox = (EditText) customView.findViewById(R.id.edit_dialog_ng_remark_description);
        if (remark != null) {
            editBox.setText(remark);
        }
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (getDescription().isEmpty()) {
                    Utils.alert(getContext(), "Warning",
                            "Please input the reason on remark.");
                } else {
                    listener.onConfirm(editBox.getText().toString());
                    autoDismiss(true);
                }
            }
        });
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                autoDismiss(true);
            }
        });
    }

    private String getDescription() {
        return editBox.getText().toString();
    }
}
