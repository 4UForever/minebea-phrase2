package com.devsenses.minebea.manager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;

import com.devsenses.minebea.adapter.Ng1AndNg2Adapter;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGSummary;

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

    public String getNgSummaryJsonFormatted() {
        return "";
    }

    private List<NGSummary> getNgSummaryList() {
        return adapter.getNgSummaryList();
    }

    private int getSumNG1() {
        int sum = 0;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            sum += Integer.parseInt(getNgSummaryList().get(i).getNg1());
        }
        return sum;
    }

    private int getSumNG2() {
        int sum = 0;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            if (getNgSummaryList().get(i).getNg2().isEmpty()) {
                sum = getSumNG1();
            } else {
                sum += Integer.parseInt(getNgSummaryList().get(i).getNg2());
            }
        }
        return sum;
    }

    public boolean isNg1AndNg2Matched() {
        boolean isMatched = true;
        int ng1, ng2;
        for (int i = 0; i < getNgSummaryList().size(); i++) {
            ng1 = Integer.parseInt(getNgSummaryList().get(i).getNg1());
            if (getNgSummaryList().get(i).getNg2().isEmpty()) {
                ng2 = ng1;
            } else {
                ng2 = Integer.parseInt(getNgSummaryList().get(i).getNg2());
            }
            Log.d("MineBea", "ng1=" + ng1 + " ng2=" + ng2);
            if (ng2 != 0 && ng1 != ng2) {
                isMatched = false;
            }
        }
        Log.d("MineBea", "------------------------------------");
        return isMatched;
    }
}
