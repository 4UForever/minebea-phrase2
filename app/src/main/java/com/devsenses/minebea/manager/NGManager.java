package com.devsenses.minebea.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.component.NGContain;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnApiGetNGListener;
import com.devsenses.minebea.model.ngmodel.NG;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.task.TaskNG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 2/9/2016.
 */
public class NGManager implements NGContain.OnSelectedNGPerItem {
    private List<NG> ngList;
    private List<NG> baseBGList;
    private List<NGContain> ngContainList;

    private Context context;
    private OnUpdateNGNumberListener listener;

    private LinearLayout layoutNGList;
    private TextView textAddNG;

    public NGManager(Context context, LinearLayout layoutNGList, TextView textAddNG, OnUpdateNGNumberListener listener) {
        this.context = context;
        this.layoutNGList = layoutNGList;
        this.textAddNG = textAddNG;
        this.listener = listener;

        ngContainList = new ArrayList<>();
        initEvent();
    }

    private void initEvent() {
        textAddNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndAddNGRowIfCan();
            }
        });
    }

    public void loadNGList(final String employeeNo) {
        TaskNG.getNGList(context, employeeNo, new OnApiGetNGListener() {
            @Override
            public void onSuccess(NGListData ngListData) {
                setupNGListLayout(ngListData);
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(context, reason + "\nPlease try again.",
                        new DialogWithText.OnClickListener() {
                            @Override
                            public void onClick() {
                                loadNGList(employeeNo);
                            }
                        });
            }
        });
    }

    public void setupNGListLayout(NGListData ngListData) {
//        NG ng = new NG();
//        ng.setId(1);
//        ng.setTitle("yeah");
//        NG ng2 = new NG();
//        ng2.setId(2);
//        ng2.setTitle("yeah");
//        ngListData.getNGList().add(ng);
//        ngListData.getNGList().add(ng2);

        ngContainList = new ArrayList<>();

        if (ngListData.getNGList().size() > 0) {
            baseBGList = ngListData.getNGList();
            resetNGList();
            checkAndAddNGRowIfCan();
        } else {
            showTextNoNGAvailable();
            textAddNG.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void showTextNoNGAvailable() {
        TextView text = new TextView(context);
        text.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        text.setGravity(Gravity.CENTER);
        text.setText("This process is not have any NG list, Please contact administrator.");
        layoutNGList.addView(text);
    }

    private void checkAndAddNGRowIfCan() {
        addNGDetailRow();
        if (ngContainList.size() >= baseBGList.size()) {
            textAddNG.setVisibility(View.GONE);
        }
    }

    private void addNGDetailRow() {
        NGContain ngContain = new NGContain(context, ngList, this);
        ngContainList.add(ngContain);
        layoutNGList.addView(ngContain.layout);
    }

    private List<NG> getSelectedNGFromNGContain() {
        //NGContain
        List<NG> selectedNGList = new ArrayList<>();
        for (NGContain ngContainTemp : ngContainList) {
            if (ngContainTemp.getSelectedNG() != null) {
                selectedNGList.add(ngContainTemp.getSelectedNG());
            }
        }
        return selectedNGList;
    }


    private void resetNGList() {
        ngList = new ArrayList<>();
        for (NG ngTemp : baseBGList) {
            ngList.add(ngTemp);
        }
    }

    private void removeSelectedNGFromNGList(List<NG> selectedNGList) {
        for (NG selectedNG : selectedNGList) {
            if (selectedNG != null) {
                if (ngList.contains(selectedNG)) {
                    ngList.remove(selectedNG);
                }
            }
        }
    }

    private void updateNGContain() {
        for (NGContain ngContainTemp : ngContainList) {
            ngContainTemp.updateList(ngList);
        }
    }

    public int getSumQuantityFromNGList() {
        int sum = 0;
        for (NGContain ngContainTemp : ngContainList) {
            if (ngContainTemp != null) {
                sum += ngContainTemp.getNGQuantity();
            }
        }
        return sum;
    }

    public boolean isNGListComplete() {
        boolean isComplete = true;
        if (ngContainList.size() > 0) {
            for (NGContain con : ngContainList) {
                if (con.getSelectedNG() == null) {
                    isComplete = false;
                }
            }
            if (!isComplete) {
                DialogWithText.showMessage(context,
                        "Please fill all NG detail on list.\nOr delete unused row.");
            }
        }
        return isComplete;
    }

    @Override
    public void onUpdateList() {
        resetNGList();
        removeSelectedNGFromNGList(getSelectedNGFromNGContain());

        updateNGContain();
    }

    @Override
    public void onUpdateQuantity() {
        listener.onUpdateQuantity();
    }

    @Override
    public void onDeleteNGRow(NGContain contain) {
        if (ngContainList.contains(contain)) {
            ngContainList.remove(contain);
            layoutNGList.removeView(contain.layout);
        }
        onUpdateList();
        onUpdateQuantity();
        textAddNG.setVisibility(View.VISIBLE);
    }

    public String getNGListJsonFormat() {
        if (ngContainList != null && ngContainList.size() > 0) {
            JSONArray ary = new JSONArray();
            for (NGContain con : ngContainList) {
                JSONObject jsonObject = new JSONObject();
                try {
                    if (con.getSelectedNG() != null) {
                        jsonObject.put("ng_id", con.getSelectedNG().getId());
                        jsonObject.put("quantity", con.getNGQuantity());
                    }
                } catch (JSONException e) {
                    Log.d("Check", "Json Error: " + e.getMessage());
                }
                ary.put(jsonObject);
            }
            return ary.toString();
        } else {
            return "[]";
        }
    }

    public interface OnUpdateNGNumberListener {
        void onUpdateQuantity();
    }
}
