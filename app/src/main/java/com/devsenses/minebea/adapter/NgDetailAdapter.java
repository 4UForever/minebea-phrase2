package com.devsenses.minebea.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NG;
import com.devsenses.minebea.model.ngmodel.NGDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 8/9/2560.
 */

public class NgDetailAdapter extends BaseAdapter {
    public interface OnItemChangedListener {
        void notifyItemChange();
    }

    private final LayoutInflater inflater;
    private Context context;
    private final List<NG> baseNgList;
    private final List<NGDetail> selectedNgList;
    private final List<String> baseNgTitleList;
    private OnItemChangedListener listener;

    public NgDetailAdapter(Context context, List<NG> baseNgList, @Nullable List<NGDetail> existNgList) {
        this.context = context;
        this.baseNgList = baseNgList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (existNgList != null) {
            selectedNgList = existNgList;
        } else {
            selectedNgList = new ArrayList<>();
        }

        baseNgTitleList = convertNgToNgTitle(baseNgList);
    }

    private NGDetail createNewBaseNg() {
        NG ng = new NG();
        ng.setId(baseNgList.get(0).getId());
        ng.setTitle(baseNgList.get(0).getTitle());
        ng.setProcessId(baseNgList.get(0).getProcessId());
        ng.setCreatedAt(baseNgList.get(0).getCreatedAt());
        ng.setUpdatedAt(baseNgList.get(0).getUpdatedAt());

        NGDetail ngDetail = new NGDetail();
        ngDetail.setNg(ng);
        ngDetail.setQuantity("");

        return ngDetail;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ng_detail, parent, false);
            holder = new ViewHolder(convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        for (int i = 0; i < baseNgTitleList.size(); i++) {
            if (baseNgTitleList.get(i).equals(selectedNgList.get(position).getNg().getTitle())) {
                holder.spinnerNgListItem.setSelection(i);
            }
        }
        holder.ngItemQty.setText(selectedNgList.get(position).getQuantity());
        return convertView;
    }

    public void setOnItemChangedListener(OnItemChangedListener listener) {
        this.listener = listener;
    }

    public List<NGDetail> getSelectedNgList() {
        return selectedNgList;
    }

    public void addNewNgDetail() {
        selectedNgList.add(createNewBaseNg());
        notifyDataSetChanged();
        if (listener != null) listener.notifyItemChange();
    }

    private void removeSelectedNgDetail(int position) {
        try {
            if (selectedNgList != null) {
                selectedNgList.remove(position);
                notifyDataSetChanged();
                if (listener != null) listener.notifyItemChange();
            }
        } catch (IndexOutOfBoundsException e) {
            Log.e("MineBea", "error remove at position " + position);
        }
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
        Button btnNgItemDelete;

        private final int itemPosition;

        ViewHolder(View v, int itemPosition) {
            this.itemPosition = itemPosition;

            spinnerNgListItem = (Spinner) v.findViewById(R.id.spinner_ng_list_item);
            ngItemQty = (EditText) v.findViewById(R.id.edit_ng_item_qty);
            btnNgItemDelete = (Button) v.findViewById(R.id.btn_ng_item_delete);

            spinnerNgListItem.setAdapter(new SpinnerTitleAdapter(context, baseNgTitleList));

            initEvent();
        }

        public void initEvent() {
            spinnerNgListItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        selectedNgList.get(itemPosition).getNg().setId(baseNgList.get(position).getId());
                        selectedNgList.get(itemPosition).getNg().setTitle(baseNgList.get(position).getTitle());
                        selectedNgList.get(itemPosition).getNg().setProcessId(baseNgList.get(position).getProcessId());
                        selectedNgList.get(itemPosition).getNg().setCreatedAt(baseNgList.get(position).getCreatedAt());
                        selectedNgList.get(itemPosition).getNg().setUpdatedAt(baseNgList.get(position).getUpdatedAt());
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("MineBea", "error position " + itemPosition);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ngItemQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        selectedNgList.get(itemPosition).setQuantity(ngItemQty.getText().toString());
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("MineBea", "error position " + itemPosition);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            btnNgItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeSelectedNgDetail(itemPosition);
                    getSelectedNgList();
                }
            });
        }
    }
}
