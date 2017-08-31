package com.devsenses.minebea.dialog;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.listener.OnDialogEmp_NoListener;


/**
 * Created by Administrator on 26/1/2558.
 */
public class DialogEmp_No extends MaterialDialog.Builder {

    private boolean _isShow = false;

    private OnDialogEmp_NoListener callback;
    private EditText editTextEmpNo;

    public DialogEmp_No(@NonNull Context context,@NonNull OnDialogEmp_NoListener callback) {
        super(context);
        this.callback = callback;

        initCustomView();
        initDialogOption();

        setPositiveAction();
        setNegativeAction();
    }

    private void initCustomView(){
        View customView = View.inflate(context,R.layout.dialog_emp_no,null);
        editTextEmpNo = (EditText) customView.findViewById(R.id.editTextEmpNo);
//        editTextEmpNo.setText("pong.p");
        customView(customView, true);
    }

    private void initDialogOption(){
        positiveText("OK").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        negativeText("CANCEL");
        cancelable(false);
        autoDismiss(false);
    }

    private String getEmpNo(){
        return (editTextEmpNo!=null?editTextEmpNo.getText().toString():"");
    }

    private void setPositiveAction(){
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                String emp_no = getEmpNo();
                if (emp_no.length() > 0) {
                    autoDismiss(true);
                    callback.onClickDialog_OK(emp_no);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.text_input_emp_no), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNegativeAction(){
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                callback.onClickDialog_Cancel();
                autoDismiss(true);
            }
        });
    }

    public boolean isShow(){
        return _isShow;
    }

    @Override
    public MaterialDialog show() {
        _isShow = true;
        return super.show();
    }

    @Override
    public MaterialDialog.Builder autoDismiss(boolean dismiss) {
        _isShow = false;
        return super.autoDismiss(dismiss);
    }
}
