package com.devsenses.minebea.listener;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by USER on 7/9/2560.
 */

public class OnButtonDeleteNumberClickedListener implements Button.OnClickListener {
    private EditText editText;

    public OnButtonDeleteNumberClickedListener(@NonNull EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View v) {
        if (!editText.getText().toString().isEmpty()) {
            try {
                int number = Integer.parseInt(editText.getText().toString());
                if (number > 0) {
                    editText.setText(String.valueOf(number - 1));
                }
            } catch (Exception e) {
                Log.d(getClass().getSimpleName(), e.getMessage());
            }
        } else {
            editText.setText("0");
        }
    }
}
