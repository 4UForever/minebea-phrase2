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
        holder.radioButtonNg1.setChecked(true);
        holder.radioButtonNg2.setChecked(list.get(position).getNg2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

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

            radioButtonNg1.setEnabled(false);
            radioButtonNg1.setClickable(false);

            radioButtonNg2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(getAdapterPosition()).setNg2(!list.get(getAdapterPosition()).getNg2());
                    radioButtonNg2.setChecked(list.get(getAdapterPosition()).getNg2());
                    if (listener != null) {
                        listener.onNg2Change();
                    }
                }
            });
        }
    }
}
