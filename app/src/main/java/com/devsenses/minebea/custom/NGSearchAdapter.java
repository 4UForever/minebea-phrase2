package com.devsenses.minebea.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.ngmodel.NG;
import com.veinhorn.searchadapter.SearchAdapter;

import java.util.List;

/**
 * Created by pong.p on 2/3/2016.
 */
public class NGSearchAdapter extends SearchAdapter<NG> {

    public NGSearchAdapter(List<NG> container, Context context) {
        super(container, context);
    }

    @Override
    public NG getItem(int position) {
        return filteredContainer.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_spiner, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(filteredContainer.get(position).getTitle());

        return convertView;
    }

    public void updateListNG(List<NG> container){
        this.container = container;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView textView;
        ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}
