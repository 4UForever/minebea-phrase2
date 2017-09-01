package com.devsenses.minebea.custom;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.lineleadermodel.LineLeader;
import com.veinhorn.searchadapter.SearchAdapter;

import java.util.List;

/**
 * Created by pong.p on 12/25/2015.
 */
public class LineLeaderSearchAdapter extends SearchAdapter<LineLeader> {
    public LineLeaderSearchAdapter(List<LineLeader> list, Context context) {
        super(list, context);
    }

    @Override
    public LineLeader getItem(int position) {
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
        holder.textView.setText(filteredContainer.get(position).getFullName());

        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}
