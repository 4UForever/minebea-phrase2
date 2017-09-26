package com.devsenses.minebea.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.devsenses.minebea.adapter.Ng1AndNg2Adapter;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGSummary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by USER on 9/9/2560.
 */

public class NGDetailListManager {
    public interface OnNg2SumChangeListener {
        void onNg2SumUpdate(String sumNg2);
    }

    private final Context context;
    private final RecyclerView listView;
    private final List<NGDetail> ngDetailList;

    private Ng1AndNg2Adapter adapter;
    private OnNg2SumChangeListener listener;

    public NGDetailListManager(@NonNull Context context, RecyclerView listView, List<NGDetail> ngDetailList) {
        this.context = context;
        this.listView = listView;
        this.ngDetailList = ngDetailList;

        initListView();
    }

    public void setOnNg2ChangeListener(OnNg2SumChangeListener listener) {
        this.listener = listener;
    }

    private void initListView() {
        adapter = new Ng1AndNg2Adapter(context, ngDetailList);
        adapter.setOnNg2ChangeListener(new Ng1AndNg2Adapter.OnNg2ChangeListener() {
            @Override
            public void onNg2Change() {
                if (listener != null) listener.onNg2SumUpdate(getSumNg2Value());
            }
        });
        listView.setAdapter(adapter);
    }

    private List<NGSummary> getNgSummaryList() {
        return adapter.getNgSummaryList();
    }

    public String getSumNg2Value() {
        String ng2;
        int ng2Value = 0;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            ng2 = getNgSummaryList().get(i).getNg2();
            if (!ng2.isEmpty()) {
                ng2Value += Integer.parseInt(ng2);
            }
        }
        return String.valueOf(ng2Value);
    }

    public boolean isNg2Empty() {
        boolean isEmpty = false;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            if (getNgSummaryList().get(i).getNg2().isEmpty()) {
                isEmpty = true;
            }
        }

        return isEmpty;
    }

    public boolean isNg1AndNg2Matched() {
        boolean isMatched = true;
        int ng1, ng2;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            ng1 = Integer.parseInt(getNgSummaryList().get(i).getNg1());
            ng2 = Integer.parseInt(getNgSummaryList().get(i).getNg2());
            if (ng2 > ng1) {
                isMatched = false;
            }
        }
        return isMatched;
    }

    public String getNgSummaryJsonFormatted() {
        int ng1, ng2;
        try {
            JSONArray ary = new JSONArray();
            for (int i = 0; i < getNgSummaryList().size(); i++) {
                JSONObject ngObj = new JSONObject();
                ngObj.put("ng_id", getNgSummaryList().get(i).getNg().getId());
                ng1 = Integer.parseInt(getNgSummaryList().get(i).getNg1());
                ng2 = Integer.parseInt(getNgSummaryList().get(i).getNg2());
                if (ng2 >= ng1) {
                    ngObj.put("ng1", ng2);
                } else {
                    ngObj.put("ng1", ng1);
                }
                ngObj.put("ng2", ng2);
                ary.put(ngObj);
            }
            return ary.toString();
        } catch (JSONException e) {
            Log.d("MineBea", "Json Error: " + e.getMessage());
            return "[]";
        }
    }
}
