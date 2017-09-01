package com.devsenses.minebea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devsenses.minebea.R;

import java.util.List;

/**
 * Created by Horus on 1/28/2015.
 */
public class SpinnerTitleAdapter extends BaseAdapter {
    private List<String> listModel;
    private LayoutInflater inflater;

    public SpinnerTitleAdapter(Context context, List<String> listModel) {
        this.listModel = listModel;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position ,convertView , parent );
    }

    @Override
    public int getCount() {
        return listModel.size();
    }

    @Override
    public String getItem(int position) {
        return listModel.get(position);
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

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_spiner, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(listModel.get(position));

        return convertView;
    }

    private static class ViewHolder{
        TextView textView;
        ViewHolder(View v){
            textView = (TextView) v.findViewById(R.id.text);
        }
    }

}
