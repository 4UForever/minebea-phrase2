package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.NgDetailAdapter;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.utils.UiUtils;

/**
 * Created by USER on 7/9/2560.
 */

public class DialogNgListDetail extends MaterialDialog.Builder {

    public interface OnDialogNgListListener {
        void onSavedList(NGListData listData);
    }

    private final NGListData baseNgList;
    @Nullable
    private final NGListData existNgList;
    private final OnDialogNgListListener listener;

    private ListView listView;
    private NgDetailAdapter ngDetailAdapter;

    public DialogNgListDetail(Context context, NGListData baseNgList, @Nullable NGListData existNgList,
                              OnDialogNgListListener listener) {
        super(context);
        this.baseNgList = baseNgList;
        this.existNgList = existNgList;
        this.listener = listener;

        initCustomView();
        initDialogOption();
//        initSpinnerModel();
//
//        setProcessNumber(textProcess);
        setPositiveAction();
        setNegativeAction();
        setNeutralAction();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_ng_list, null);
//        spinReason = (Spinner) view.findViewById(R.id.spinner_reason);
        listView = (ListView) view.findViewById(R.id.list_dialog_ng_list);
        if (isBaseNgListEmpty()) {
            ngDetailAdapter = new NgDetailAdapter(getContext(), baseNgList.getNGList(),
                    existNgList != null ? existNgList.getNGList() : null);
            listView.setAdapter(ngDetailAdapter);
            UiUtils.setListViewHeightBasedOnItems(listView);
        }
        customView(view, true);
    }

    private void initDialogOption() {
        positiveText("SAVE").positiveColor(ContextCompat.getColor(context, R.color.red));
        negativeText("CANCEL");
        neutralText("Add new NG1 Detail");
        cancelable(false);
        autoDismiss(false);
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (isBaseNgListEmpty()) {
                    NGListData selectedNgList = new NGListData();
                    selectedNgList.setNGList(ngDetailAdapter.getSelectedNgList());
                    Log.d("MineBea", selectedNgList.toString());
                    listener.onSavedList(selectedNgList);
                } else {
                    listener.onSavedList(null);
                }
                autoDismiss(true);
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

    private void setNeutralAction() {
        onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                ngDetailAdapter.addNewNgDetail();
                UiUtils.setListViewHeightBasedOnItems(listView);
//                autoDismiss(true);
            }
        });
    }

    private boolean isBaseNgListEmpty() {
        return baseNgList != null && baseNgList.getNGList() != null && !baseNgList.getNGList().isEmpty();
    }
}
