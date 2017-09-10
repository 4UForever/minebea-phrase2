package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.NgDetailAdapter;
import com.devsenses.minebea.manager.NGManager;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.utils.UiUtils;

import java.util.List;

/**
 * Created by USER on 7/9/2560.
 */

public class DialogNgListDetail extends MaterialDialog.Builder {
    public interface OnDialogNgListListener {
        void onSavedList(List<NGDetail> ngDetailList);
    }

    private final NGListData baseNgList;
    private final List<NGDetail> existNgList;
    private final OnDialogNgListListener listener;

    private ListView listView;
    private NgDetailAdapter ngDetailAdapter;
    private NGManager ngManager;

    public DialogNgListDetail(Context context, NGListData baseNgList, @Nullable List<NGDetail> existNgList,
                              OnDialogNgListListener listener) {
        super(context);
        this.baseNgList = baseNgList;
        this.existNgList = existNgList;
        this.listener = listener;

        initCustomView();
        initDialogOption();

        setPositiveAction();
        setNegativeAction();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_ng_list, null);
        listView = (ListView) view.findViewById(R.id.list_dialog_ng_list);
        if (!isBaseNgListEmpty()) {
            setupListView();
            showAddNewNgDetailButton();
        } else {
            TextView emptyText = (TextView) view.findViewById(R.id.text_dialog_ng_list_empty_message);
            emptyText.setVisibility(View.VISIBLE);
        }
        customView(view, true);
    }

    private void initDialogOption() {
        positiveText("SAVE").positiveColor(ContextCompat.getColor(context, R.color.red));
        negativeText("CANCEL");
        cancelable(false);
        autoDismiss(false);
    }

    private void setupListView() {
        ngDetailAdapter = new NgDetailAdapter(getContext(), baseNgList.getNGList(),
                existNgList != null ? existNgList : null);
        ngDetailAdapter.setOnItemChangedListener(new NgDetailAdapter.OnItemChangedListener() {
            @Override
            public void notifyItemChange() {
                UiUtils.setListViewHeightBasedOnItems(listView);
            }
        });
        listView.setAdapter(ngDetailAdapter);
        UiUtils.setListViewHeightBasedOnItems(listView);
    }

    private void showAddNewNgDetailButton() {
        neutralText("Add new NG1 Detail");
        setNeutralAction();
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (!isBaseNgListEmpty()) {
                    Log.d("MineBea", "listener return " + ngDetailAdapter.getSelectedNgList().toString());
                    listener.onSavedList(ngDetailAdapter.getSelectedNgList());
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
                if (ngDetailAdapter != null) ngDetailAdapter.addNewNgDetail();
//                autoDismiss(true);
            }
        });
    }

    private boolean isBaseNgListEmpty() {
        return baseNgList == null || baseNgList.getNGList() == null || baseNgList.getNGList().isEmpty();
    }
}
