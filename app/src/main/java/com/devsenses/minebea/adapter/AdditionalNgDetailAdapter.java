package com.devsenses.minebea.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NG;
import com.devsenses.minebea.model.ngmodel.NGSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 28/11/2560.
 */

public class AdditionalNgDetailAdapter extends RecyclerView.Adapter<AdditionalNgDetailAdapter.ViewHolder> {

    private final List<NG> baseNgList;
    private final List<NGSummary> list;

    private final List<String> baseNgTitleList;

    public AdditionalNgDetailAdapter(List<NG> baseNgList) {
        this.baseNgList = baseNgList;

        list = new ArrayList<>();
        baseNgTitleList = convertNgToNgTitle(baseNgList);
    }

    private List<String> convertNgToNgTitle(List<NG> list) {
        List<String> titles = new ArrayList<>();
        for (NG ng : list) {
            titles.add(ng.getTitle());
        }
        return titles;
    }

    public void addNewNgSummary() {
        list.add(createNewNgSummary());
        notifyDataSetChanged();
    }

    public List<NGSummary> getAdditionalNgSummaryList() {
        return list;
    }

    private NGSummary createNewNgSummary() {
        NG ng = new NG();
        ng.setId(baseNgList.get(0).getId());
        ng.setTitle(baseNgList.get(0).getTitle());
        ng.setProcessId(baseNgList.get(0).getProcessId());
        ng.setCreatedAt(baseNgList.get(0).getCreatedAt());
        ng.setUpdatedAt(baseNgList.get(0).getUpdatedAt());

        NGSummary summary = new NGSummary();
        summary.setNg(ng);
        summary.setSerialNo("");
        summary.setNg1(false);
        summary.setNg2(true);

        return summary;
    }

    private void removeSelectedNgDetail(int position) {
        try {
            if (list != null) {
                list.remove(position);
                notifyDataSetChanged();
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e("MineBea", "error remove at position " + position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_additional_ng1_and_ng2, parent, false);
        return new ViewHolder(parent.getContext(), view, baseNgTitleList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        for (int i = 0; i < baseNgTitleList.size(); i++) {
            if (baseNgTitleList.get(i).equals(list.get(position).getNg().getTitle())) {
                holder.spinnerNgListItem.setSelection(i);
            }
        }
        holder.ngItemSerialNo.setText(list.get(position).getSerialNo());
        holder.radioButtonNg1.setChecked(list.get(position).getNg1());
        holder.radioButtonNg2.setChecked(list.get(position).getNg2());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Spinner spinnerNgListItem;
        EditText ngItemSerialNo;
        RadioButton radioButtonNg1;
        RadioButton radioButtonNg2;
        Button btnNgItemDelete;

        ViewHolder(Context context, View v, List<String> baseNgTitleList) {
            super(v);
            spinnerNgListItem = (Spinner) v.findViewById(R.id.spinner_additional_ng_list_item);
            ngItemSerialNo = (EditText) v.findViewById(R.id.edit_additional_ng_serial_no);
            radioButtonNg1 = (RadioButton) v.findViewById(R.id.radiobtn_additional_ng1);
            radioButtonNg2 = (RadioButton) v.findViewById(R.id.radiobtn_additional_ng2);
            btnNgItemDelete = (Button) v.findViewById(R.id.btn_ng_additional_delete);

            spinnerNgListItem.setAdapter(new SpinnerTitleAdapter(context, baseNgTitleList));

            initSpinnerEvent();
            initEditSerialNoEvent();
            initDeleteEvent();
            initRadioEvent();
        }


        private void initSpinnerEvent() {
            spinnerNgListItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        list.get(getAdapterPosition()).getNg().setId(baseNgList.get(position).getId());
                        list.get(getAdapterPosition()).getNg().setTitle(baseNgList.get(position).getTitle());
                        list.get(getAdapterPosition()).getNg().setProcessId(baseNgList.get(position).getProcessId());
                        list.get(getAdapterPosition()).getNg().setCreatedAt(baseNgList.get(position).getCreatedAt());
                        list.get(getAdapterPosition()).getNg().setUpdatedAt(baseNgList.get(position).getUpdatedAt());
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("MineBea", "error position " + getAdapterPosition());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        private void initEditSerialNoEvent() {
            ngItemSerialNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        list.get(getAdapterPosition()).setSerialNo(ngItemSerialNo.getText().toString());
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("MineBea", "error position " + getAdapterPosition());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
            });
        }

        private void initRadioEvent() {
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

        private void initDeleteEvent() {
            btnNgItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeSelectedNgDetail(getAdapterPosition());
                }
            });
        }
    }
}
