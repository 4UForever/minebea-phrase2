package com.devsenses.minebea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.partmodel.LotData;

import java.util.List;

/**
 * Created by pong.p on 3/31/2016.
 */
public class SpinnerLotNoAdapter extends BaseAdapter {

    private final List<LotData> list;
    private final LayoutInflater inflater;

    public SpinnerLotNoAdapter(Context context,List<LotData> list){
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public LotData getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
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
        holder.textView.setText(list.get(position).getNumber());

        return convertView;
    }

    private static class ViewHolder{
        TextView textView;
        ViewHolder(View v){
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}
