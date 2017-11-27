package com.devsenses.minebea.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 9/9/2560.
 */

public class Ng1AndNg2Adapter extends RecyclerView.Adapter<Ng1AndNg2Adapter.ViewHolder> {
    public interface OnNg2ChangeListener {
        void onNg2Change();
    }

    private final List<NGSummary> list;
    private OnNg2ChangeListener listener;

    public Ng1AndNg2Adapter(@Nullable List<NGDetail> ngDetailList) {
        this.list = new ArrayList<>();

        if (ngDetailList != null) {
            for (int i = 0; i < ngDetailList.size(); i++) {
                NGSummary summary = new NGSummary();
                summary.setSerialNo(ngDetailList.get(i).getSerialNo());
                summary.setNg(ngDetailList.get(i).getNg());
                summary.setNg1(true);
                summary.setNg2(true);
                this.list.add(summary);
            }
        }
    }

    public void setOnNg2ChangeListener(OnNg2ChangeListener listener) {
        this.listener = listener;
    }

    public List<NGSummary> getNgSummaryList() {
        return list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ng1_and_ng2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textNgDetail.setText(list.get(position).getNg().getTitle());
        holder.textNgSerialNo.setText(list.get(position).getSerialNo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public NGSummary getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.item_ng1_and_ng2, parent, false);
//            holder = new ViewHolder(convertView, position);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.textNgDetail.setText(list.get(position).getNg().getTitle());
//        holder.textNg1.setText(list.get(position).getNg1());
//        holder.editNg2.setText(list.get(position).getNg2());
//
//        Log.d("MineBea", "position=" + position + " " + list.get(position).toString());
//
//        return convertView;
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNgDetail;
        TextView textNgSerialNo;
        RadioButton radioButtonNg1;
        RadioButton radioButtonNg2;

        ViewHolder(View view) {
            super(view);
            textNgDetail = (TextView) view.findViewById(R.id.text_item_ng_detail);
            textNgSerialNo = (TextView) view.findViewById(R.id.text_item_ng_serial_no);
            radioButtonNg1 = (RadioButton) view.findViewById(R.id.radiobtn_item_ng1);
            radioButtonNg2 = (RadioButton) view.findViewById(R.id.radiobtn_item_ng2);

            radioButtonNg1.setChecked(true);
            radioButtonNg2.setChecked(true);

            radioButtonNg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButtonNg1.setChecked(!radioButtonNg1.isChecked());
                    list.get(getAdapterPosition()).setNg1(radioButtonNg1.isChecked());
                }
            });

            radioButtonNg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButtonNg2.setChecked(!radioButtonNg2.isChecked());
                    list.get(getAdapterPosition()).setNg2(radioButtonNg2.isChecked());
                }
            });
        }
    }
}
