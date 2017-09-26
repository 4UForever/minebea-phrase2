package com.devsenses.minebea.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.SpinnerLotNoAdapter;
import com.devsenses.minebea.custom.LineLeaderListSearchableLayout;
import com.devsenses.minebea.model.lineleadermodel.LineLeader;
import com.devsenses.minebea.model.partmodel.LotDataModel;

import java.util.List;

/**
 * Created by pong.p on 12/16/2015.
 */
public class DialogLineLeaderSelect extends MaterialDialog.Builder {
    private final Context context;
    private LotDataModel lotDataModel;
    private final OnDialogLineLeaderListener listener;
    private EditText firstSerialEdit;
    private EditText lotNoEdit;
    private Spinner lotNoSpinner;

    private List<LineLeader> lineLeaderList;
    private ViewGroup layout;
    private EditText lineLeaderEditText;
    private LineLeaderListSearchableLayout searchCustomLayout;

    private LineLeader selectedLineLeader;

    public DialogLineLeaderSelect(@NonNull Context context, List<LineLeader> lineLeaderList,
                                  LotDataModel lotDataModel, OnDialogLineLeaderListener listener) {
        super(context);
        this.context = context;
        this.lotDataModel = lotDataModel;
        this.listener = listener;
        this.lineLeaderList = lineLeaderList;

        initCustomView();
        initUIDialog();
        initDialogOption();
        initEvent();

        setPositiveAction();
        setNegativeAction();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.dialog_line_leader_select, null);
        customView(view, true);
    }

    private void initUIDialog() {
        lineLeaderEditText = (EditText) customView.findViewById(R.id.lineLeaderEditText);
        firstSerialEdit = (EditText) customView.findViewById(R.id.firstSerialEditText);
        lotNoEdit = (EditText) customView.findViewById(R.id.lotNoEditText);
        lotNoSpinner = (Spinner) customView.findViewById(R.id.lotNoSpinner);
        layout = (ViewGroup) customView.findViewById(R.id.frameLayout);

        if (!lotDataModel.isTypingLot()) {
            lotNoEdit.setVisibility(View.GONE);
            lotNoSpinner.setVisibility(View.VISIBLE);
            lotNoSpinner.setAdapter(new SpinnerLotNoAdapter(context, lotDataModel.getLotDataList()));
        }

        firstSerialEdit.setText(lotDataModel.getFirstSerialNo());
    }

    private void initDialogOption() {
        this.positiveText("Process").positiveColor(ContextCompat.getColor(context, R.color.blueText));
        this.negativeText("Cancel");
        this.cancelable(false);
        autoDismiss(false);
    }


    private void initEvent() {
        lineLeaderEditText.setOnKeyListener(null);
        lineLeaderEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!needToCloseListViewDialog()) openListViewDialog();
            }
        });
    }

    private void openListViewDialog() {
        searchCustomLayout = new LineLeaderListSearchableLayout(context, lineLeaderList,
                new LineLeaderListSearchableLayout.OnSelectItemCallback() {
                    @Override
                    public void onSelected(LineLeader leader) {
                        lineLeaderEditText.setText(leader.getFullName());
                        selectedLineLeader = leader;
                        needToCloseListViewDialog();
                    }
                });

        layout.addView(searchCustomLayout);
    }

    private Boolean needToCloseListViewDialog() {
        if (searchCustomLayout != null) {
            layout.removeView(searchCustomLayout);
            searchCustomLayout = null;
            return true;
        }
        return false;
    }

    private void setPositiveAction() {
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                if (selectedLineLeader == null) {
                    DialogWithText.showMessage(context, "Please select line leader.");
                    return;
                }
                if (firstSerialEdit.getText().toString().isEmpty()) {
                    DialogWithText.showMessage(context, "Please input first serial no.");
                    return;
                }
                if (lotDataModel.isTypingLot() && lotNoEdit.getText().toString().isEmpty()) {
                    DialogWithText.showMessage(context, "Please input lot no.");
                    return;
                }
                listener.onOk(selectedLineLeader.getId(), firstSerialEdit.getText().toString(), getLotNo(), getLotID());
                autoDismiss(true);
            }
        });
    }

    private String getLotNo() {
        return lotNoEdit.getText().toString();
    }

    private Long getLotID() {
        if (lotNoSpinner.getAdapter() != null) {
            return lotNoSpinner.getAdapter().getItemId(lotNoSpinner.getSelectedItemPosition());
        } else {
            return Long.parseLong("0");
        }
    }

    public String getLotNoByID() {
        if (lotNoSpinner.getAdapter() != null) {
            return ((SpinnerLotNoAdapter) lotNoSpinner.getAdapter()).getItem(lotNoSpinner.getSelectedItemPosition()).getNumber();
        } else {
            return getLotNo();
        }
    }

    private void setNegativeAction() {
        onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                autoDismiss(true);
            }
        });
    }

    public interface OnDialogLineLeaderListener {
        void onOk(long lineLeaderID, String firstSerial, String lotNo, Long lotId);
    }
}
