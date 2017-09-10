package com.devsenses.minebea.manager;

import android.content.Context;
import android.support.annotation.NonNull;
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
    private final Context context;
    private final ListView listView;
    private final List<NGDetail> ngDetailList;

    private Ng1AndNg2Adapter adapter;

    public NGDetailListManager(@NonNull Context context, ListView listView, List<NGDetail> ngDetailList) {
        this.context = context;
        this.listView = listView;
        this.ngDetailList = ngDetailList;

        initListView();
    }

    private void initListView() {
        adapter = new Ng1AndNg2Adapter(context, ngDetailList);
        listView.setAdapter(adapter);
    }

    private List<NGSummary> getNgSummaryList() {
        return adapter.getNgSummaryList();
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
            Log.d("MineBea", "ng1=" + ng1 + " ng2=" + ng2);
            if (ng2 >= ng1) {
                isMatched = false;
            }
        }
        Log.d("MineBea", "------------------------------------");
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
