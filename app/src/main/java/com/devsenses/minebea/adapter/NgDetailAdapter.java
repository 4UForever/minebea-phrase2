package com.devsenses.minebea.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 8/9/2560.
 */

public class NgDetailAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context context;
    private final List<NG> baseNgList;
    private final List<NG> selectedNgList;
    private final List<String> baseNgTitleList;

    public NgDetailAdapter(Context context, List<NG> baseNgList, @Nullable List<NG> existNgList) {
        this.context = context;
        this.baseNgList = baseNgList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (existNgList != null) {
            selectedNgList = existNgList;
        } else {
            selectedNgList = new ArrayList<>();
            selectedNgList.add(createNewBaseNg());
        }

        baseNgTitleList = convertNgToNgTitle(baseNgList);
    }

    public void setAddedSelectedNgList(List<NG> addedNgList) {
        if (addedNgList != null) {
            selectedNgList.clear();
            selectedNgList.addAll(addedNgList);
            notifyDataSetChanged();
        }
    }

    public void addNewNgDetail() {
        selectedNgList.add(createNewBaseNg());
        notifyDataSetChanged();
    }

    public List<NG> getSelectedNgList() {
        return selectedNgList;
    }

    private NG createNewBaseNg() {
        NG ng = new NG();
        ng.setId(baseNgList.get(0).getId());
        ng.setTitle(baseNgList.get(0).getTitle());
        ng.setProcessId(baseNgList.get(0).getProcessId());
        ng.setCreatedAt(baseNgList.get(0).getCreatedAt());
        ng.setUpdatedAt(baseNgList.get(0).getUpdatedAt());
        ng.setUseQty("0");
        return ng;
    }

    @Override
    public int getCount() {
        return selectedNgList.size();
    }

    @Override
    public Object getItem(int position) {
        return selectedNgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ng_detail, parent, false);
            holder = new ViewHolder(convertView, selectedNgList.get(position));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        for (int i = 0; i < baseNgTitleList.size(); i++) {
            if (baseNgTitleList.get(i).equals(selectedNgList.get(position).getTitle())) {
                holder.spinnerNgListItem.setSelection(i);
            }
        }
//        holder.spinnerNgListItem.setSelection();
        holder.ngItemQty.setText(selectedNgList.get(position).getUseQty());
//        holder.textView.setText(list.get(position).getNumber());

        return convertView;
    }

    private List<String> convertNgToNgTitle(List<NG> list) {
        List<String> titles = new ArrayList<>();
        for (NG ng : list) {
            titles.add(ng.getTitle());
        }
        return titles;
    }

    private class ViewHolder {
        Spinner spinnerNgListItem;
        EditText ngItemQty;
        private final NG currentNg;

        ViewHolder(View v, NG currentNg) {
            this.currentNg = currentNg;

            spinnerNgListItem = (Spinner) v.findViewById(R.id.spinner_ng_list_item);
            ngItemQty = (EditText) v.findViewById(R.id.edit_ng_item_qty);

            spinnerNgListItem.setAdapter(new SpinnerTitleAdapter(context, baseNgTitleList));

            initEvent();
        }

        private void initEvent() {
            spinnerNgListItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    Log.d("MineBea", "item selected " + baseNgList.get(position).getTitle());
                    currentNg.setId(baseNgList.get(position).getId());
                    currentNg.setTitle(baseNgList.get(position).getTitle());
                    currentNg.setProcessId(baseNgList.get(position).getProcessId());
                    currentNg.setCreatedAt(baseNgList.get(position).getCreatedAt());
                    currentNg.setUpdatedAt(baseNgList.get(position).getUpdatedAt());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ngItemQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    currentNg.setUseQty(ngItemQty.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
}
