package com.devsenses.minebea.manager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.devsenses.minebea.adapter.AdditionalNgDetailAdapter;
import com.devsenses.minebea.adapter.Ng1AndNg2Adapter;
import com.devsenses.minebea.model.ngmodel.NG;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGSummary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/9/2560.
 */

public class NGDetailListManager {
    public interface OnNg2SumChangeListener {
        void onNg2SumUpdate(String sumNg2);
    }

    private final RecyclerView listView;
    private final List<NGDetail> ngDetailList;
    private final RecyclerView additionalListView;
    private final List<NG> baseNgList;

    private Ng1AndNg2Adapter adapter;
    private AdditionalNgDetailAdapter additionalAdapter;
    private OnNg2SumChangeListener listener;

    public NGDetailListManager(RecyclerView listView, List<NGDetail> ngDetailList,
                               RecyclerView additionalListView, List<NG> baseNgList) {
        this.listView = listView;
        this.ngDetailList = ngDetailList;
        this.additionalListView = additionalListView;
        this.baseNgList = baseNgList;

        initListView();
        initAdditionalListView();
        updateNg2Sum();
    }

    public void setOnNg2ChangeListener(OnNg2SumChangeListener listener) {
        this.listener = listener;
    }

    private void initListView() {
        adapter = new Ng1AndNg2Adapter(ngDetailList);
        listView.setAdapter(adapter);
        adapter.setOnNg2ChangeListener(new Ng1AndNg2Adapter.OnNg2ChangeListener() {
            @Override
            public void onNg2Change() {
                updateNg2Sum();
            }
        });
    }

    private void initAdditionalListView() {
        additionalAdapter = new AdditionalNgDetailAdapter(baseNgList);
        additionalListView.setAdapter(additionalAdapter);
        additionalAdapter.setOnNg2ChangeListener(new Ng1AndNg2Adapter.OnNg2ChangeListener() {
            @Override
            public void onNg2Change() {
                updateNg2Sum();
            }
        });
    }

    private void updateNg2Sum() {
        if (listener != null) {
            this.listener.onNg2SumUpdate(String.valueOf(getSumNG()));
        }
    }


    //////   Utility   /////////

    public List<NGSummary> getNgSummaryList() {
        return adapter.getNgSummaryList();
    }

    public List<NGSummary> getAdditionalNgSummaryList() {
        return additionalAdapter.getAdditionalNgSummaryList();
    }

    public List<NGSummary> getFinalNgSummaryList() {
        List<NGSummary> newList = new ArrayList<>(
                getNgSummaryList().size() + getAdditionalNgSummaryList().size());
        newList.addAll(getNgSummaryList());
        newList.addAll(getAdditionalNgSummaryList());
        return newList;
    }


    public void addNewAdditionalNgSummary() {
        additionalAdapter.addNewNgSummary();
        updateNg2Sum();
    }

    public int getSumNG() {
        int sumNg = 0;
        for (NGSummary ng : getFinalNgSummaryList()) {
            if (ng.getNg2()) sumNg++;
        }
        return sumNg;
    }

    public boolean isNg1AndNg2Matched(@NonNull List<NGSummary> list) {
        boolean isMatched = true;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNg1() != list.get(i).getNg2()) {
                isMatched = false;
            }
        }
        return isMatched;
    }

    public String getNgSummaryJsonFormatted(@NonNull List<NGSummary> list) {
        try {
            JSONArray ary = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                JSONObject ngObj = new JSONObject();
                ngObj.put("ng_id", list.get(i).getNg().getId());
                ngObj.put("ng_serial", list.get(i).getSerialNo());
                ngObj.put("ng1", list.get(i).getNg1());
                ngObj.put("ng2", list.get(i).getNg2());
                ary.put(ngObj);
            }
            return ary.toString();
        } catch (JSONException e) {
            Log.d("MineBea", "Json Error: " + e.getMessage());
            return "[]";
        }
    }
}
