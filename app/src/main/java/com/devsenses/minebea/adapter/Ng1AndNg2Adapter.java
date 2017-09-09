package com.devsenses.minebea.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/9/2560.
 */

public class Ng1AndNg2Adapter extends BaseAdapter {

    private final List<NGSummary> list;
    private final LayoutInflater inflater;

    public Ng1AndNg2Adapter(@NonNull Context context, @Nullable List<NGDetail> ngDetailList) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = new ArrayList<>();

        if (ngDetailList != null) {
            Log.d("MineBea", ngDetailList.toString());
            for (int i = 0; i < ngDetailList.size(); i++) {
                NGSummary summary = new NGSummary();
                summary.setNg(ngDetailList.get(i).getNg());
                summary.setNg1(ngDetailList.get(i).getQuantity());
                this.list.add(summary);
            }
        }
    }

    public List<NGSummary> getNgSummaryList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public NGSummary getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ng1_and_ng2, parent, false);
            holder = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textNgDetail.setText(list.get(position).getNg().getTitle());
        holder.textNg1.setText(list.get(position).getNg1());
        return convertView;
    }

    private class ViewHolder {
        TextView textNgDetail;
        TextView textNg1;
        EditText editNg2;
        private final int currentItemPosition;

        ViewHolder(View view, int position) {
            this.currentItemPosition = position;
            textNgDetail = (TextView) view.findViewById(R.id.text_item_ng_detail);
            textNg1 = (TextView) view.findViewById(R.id.text_item_ng1);
            editNg2 = (EditText) view.findViewById(R.id.edit_item_ng2);
            editNg2.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    list.get(currentItemPosition).setNg2(editNg2.getText().toString());
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
