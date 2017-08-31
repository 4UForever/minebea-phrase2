package com.devsenses.minebea.custom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.devsenses.minebea.R;
import com.devsenses.minebea.model.lineleadermodel.LineLeader;
import com.veinhorn.searchadapter.SearchAdapter;

import java.util.List;

/**
 * Created by pong.p on 12/25/2015.
 */
public class LineLeaderListSearchableLayout extends LinearLayout {

    private SearchAdapter<LineLeader> adapter;
    public EditText searchBox;
    public GridView gridView;
    private Context context;

    public LineLeaderListSearchableLayout(Context context, List<LineLeader> list, final OnSelectItemCallback callback) {
        super(context);
        this.context = context;

        initUIView();
        initAdapter(list,callback);
        initSearchBoxEvent();
    }

    private void initUIView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_searchable_list_view, this, true);

        searchBox = (EditText) view.findViewById(R.id.searchEditText);
        gridView = (GridView) view.findViewById(R.id.gridView);
    }

    private void initAdapter(List<LineLeader> list, final OnSelectItemCallback callback) {
        adapter = new LineLeaderSearchAdapter(list, context)
                .registerFilter(LineLeader.class, "fullName")
                .setIgnoreCase(true);

        gridView.setAdapter(adapter);
        setListViewHeightByItem(gridView, adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onSelected(((LineLeaderSearchAdapter)adapter).getItem(position));
            }
        });
    }

    private void initSearchBoxEvent() {
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItem(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        searchBox.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 67) {//erase word key
                    filterItem(searchBox.getText().toString());
                }
                return false;
            }
        });
    }

    private void filterItem(String s) {
        adapter.getFilter().filter(s, new Filter.FilterListener() {
            @Override
            public void onFilterComplete(int count) {
                setListViewHeightByItem(gridView, adapter);
            }
        });
    }

    public boolean setListViewHeightByItem(GridView listView,SearchAdapter<LineLeader> adapter) {

        if (adapter != null) {

            int numberOfItems = adapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int i = 0; i < numberOfItems; i++) {
                View item = adapter.getView(i, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public interface OnSelectItemCallback {
        void onSelected(LineLeader leader);
    }
}
