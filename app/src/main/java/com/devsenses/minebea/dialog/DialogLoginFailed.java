package com.devsenses.minebea.dialog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;

/**
 * Created by Horus on 1/28/2015.
 */
public class DialogLoginFailed extends MaterialDialog.Builder {

    public enum TYPE_LOGIN_FAILED {
        LOGIN_FAIL , NO_DATA
    }

    public DialogLoginFailed(@NonNull Context context, TYPE_LOGIN_FAILED TYPE) {
        super(context);

        initCustomView();

        if (TYPE.equals(TYPE_LOGIN_FAILED.NO_DATA)) {
            addTextViewIfTypeNoData();
        }

        initDialogOption();

        setPositiveAction();
    }

    private void initCustomView(){
        View customView = View.inflate(context,R.layout.dialog_loginfaied,null);
        customView(customView, true);
    }

    private void addTextViewIfTypeNoData() {
        TextView text = (TextView) customView.findViewById(R.id.textDialogFailed);
        text.setText(context.getResources().getString(R.string.login_no_data));
    }

    private void initDialogOption(){
        positiveText("OK").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        autoDismiss(true);
    }

    private void setPositiveAction(){
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {

            }
        });
    }
}
