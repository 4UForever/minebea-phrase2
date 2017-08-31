package com.devsenses.minebea.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.documentmodel.Document;

import java.util.List;

/**
 * Created by Administrator on 5/11/2015.
 */
public class ListDocumentAdapter extends BaseAdapter {
    private Context context;
    private List<Document> dataDocument;

    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;

    public ListDocumentAdapter(Context context, List<Document> dataDocument) {
        this.context = context;
        this.dataDocument = dataDocument;
    }

    @Override
    public int getCount() {
        return dataDocument.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 ==0)
            return TYPE_ONE;
        else
            return TYPE_TWO;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null){
            holder = new Holder();
            LayoutInflater inflater = (LayoutInflater)   context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_document, null);
            holder.textDocument = (TextView)convertView.findViewById(R.id.textDocument);
            holder.layout_item = (LinearLayout)convertView.findViewById(R.id.layout_item);
            convertView.setTag(holder);
        }
        else{
            holder = (Holder)convertView.getTag();
        }
        holder.textDocument.setText(dataDocument.get(position).getTitle());
        if (getItemViewType(position) == TYPE_ONE)
            holder.layout_item.setBackgroundColor(context.getResources().getColor( R.color.white));
        else
            holder.layout_item.setBackgroundColor(context.getResources().getColor(R.color.bg_highlight));


        return convertView;
    }
    private static class Holder{
        TextView textDocument;
        LinearLayout layout_item;
    }
}
