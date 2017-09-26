package com.devsenses.minebea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.documentmodel.DocumentData;

import java.util.List;

/**
 * Created by Administrator on 3/6/2015.
 */
public class GridDocumentAdapter extends BaseAdapter {
    private List<DocumentData> listData;
    private LayoutInflater inflater;

    public GridDocumentAdapter(Context context, List<DocumentData> listData) {
        this.listData = listData;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public DocumentData getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.layout_grid_document, parent, false);
            holder.textDocument = (TextView) convertView.findViewById(R.id.textDocument);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.textDocument.setText(listData.get(position).getTitle());

        return convertView;
    }

    private static class Holder {
        TextView textDocument;
    }
}
