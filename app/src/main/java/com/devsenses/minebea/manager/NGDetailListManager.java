package com.devsenses.minebea.manager;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    private final RecyclerView listView;
    private final List<NGDetail> ngDetailList;

    private Ng1AndNg2Adapter adapter;
    private OnNg2SumChangeListener listener;

    public NGDetailListManager(RecyclerView listView, List<NGDetail> ngDetailList) {
        this.listView = listView;
        this.ngDetailList = ngDetailList;

        initListView();
    }

    public void setOnNg2ChangeListener(OnNg2SumChangeListener listener) {
        this.listener = listener;
    }

    private void initListView() {
        adapter = new Ng1AndNg2Adapter(ngDetailList);
        listView.setAdapter(adapter);
    }

    private List<NGSummary> getNgSummaryList() {
        return adapter.getNgSummaryList();
    }

    public boolean isNg1AndNg2Matched() {
        boolean isMatched = true;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            if (getNgSummaryList().get(i).getNg1() != getNgSummaryList().get(i).getNg2()) {
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
                //TODO wait real API for name of "serial id"
                ngObj.put("serial_no", getNgSummaryList().get(i).getSerialNo());
                ngObj.put("ng1", getNgSummaryList().get(i).getNg1());
                ngObj.put("ng2", getNgSummaryList().get(i).getNg2());
                ary.put(ngObj);
            }
            return ary.toString();
        } catch (JSONException e) {
            Log.d("MineBea", "Json Error: " + e.getMessage());
            return "[]";
        }
    }
}
