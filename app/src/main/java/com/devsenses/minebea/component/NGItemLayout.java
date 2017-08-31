package com.devsenses.minebea.component;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogNGList;
import com.devsenses.minebea.model.ngmodel.NG;

import java.util.List;

/**
 * Created by pong.p on 2/3/2016.
 */
public class NGItemLayout extends LinearLayout {
    private Context context;

    private EditText ngDetailSelector;
    private EditText ngQuantityEditText;
    private ImageView deleteImage;

    private List<NG> ngList;
    private OnNGSelectedListener listener;

    private NG selectedNG;
    private MaterialDialog dialog;

    public NGItemLayout(Context context, List<NG> ngList, OnNGSelectedListener listener) {
        super(context);
        this.context = context;
        this.ngList = ngList;
        this.listener = listener;
        initUI();
        initEvent();
    }

    private void initUI() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_ng_detail, this);
        ngDetailSelector = (EditText) findViewById(R.id.edit_ng_detail);
        ngQuantityEditText = (EditText) findViewById(R.id.edit_ng_qty);
        deleteImage = (ImageView) findViewById(R.id.image_del);
    }

    private void initEvent() {
        ngDetailSelector.setOnKeyListener(null);
        ngDetailSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListViewDialog();
            }
        });
        deleteImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDelete();
            }
        });
        ngQuantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                listener.onUpdateQuantity();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void openListViewDialog() {
        dialog = new DialogNGList(context, ngList, new DialogNGList.OnSelectItemCallback() {
            @Override
            public void onSelected(NG ng) {
                ngDetailSelector.setText(ng.getTitle());
                selectedNG = ng;
                listener.onSelected(selectedNG);
                closeDialog();
            }
        }).show();
    }

    private void closeDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) dialog.dismiss();
        }
    }

    public void updateNGList(List<NG> list) {
        ngList = list;
    }

    public NG getSelectedNG() {
        return selectedNG;
    }

    public int getSelectedNGQuantity() {
        return ngQuantityEditText.getText().toString().isEmpty() ? 0 :
                Integer.parseInt(ngQuantityEditText.getText().toString().replaceAll("[\\D]", ""));
    }

    public interface OnNGSelectedListener {
        void onSelected(NG ng);
        void onUpdateQuantity();
        void onDelete();
    }
}
