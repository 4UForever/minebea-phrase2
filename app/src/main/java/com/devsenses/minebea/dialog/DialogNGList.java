package com.devsenses.minebea.dialog;

import android.content.Context;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.devsenses.minebea.R;
import com.devsenses.minebea.custom.NGSearchAdapter;
import com.devsenses.minebea.model.ngmodel.NG;
import com.veinhorn.searchadapter.SearchAdapter;

import java.util.List;

/**
 * Created by pong.p on 2/3/2016.
 */
public class DialogNGList extends MaterialDialog.Builder {
    private final Context context;

    private EditText searchBox;
    private GridView gridView;
    private SearchAdapter<NG> adapter;

    public DialogNGList(Context context, List<NG> list, final OnSelectItemCallback listener) {
        super(context);
        this.context = context;

        initCustomView();

        initAdapter(list, listener);
        initSearchBoxEvent();
    }

    private void initCustomView() {
        View view = View.inflate(context, R.layout.view_searchable_list_view, null);
        searchBox = (EditText) view.findViewById(R.id.searchEditText);
        gridView = (GridView) view.findViewById(R.id.gridView);
        customView(view, true);
    }

    private void initAdapter(List<NG> list, final OnSelectItemCallback callback) {
        adapter = new NGSearchAdapter(list, context)
                .registerFilter(NG.class, "title")
                .setIgnoreCase(true);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onSelected(((NGSearchAdapter) adapter).getItem(position));
            }
        });

        setListViewHeightByItem(gridView, adapter);
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
        searchBox.setOnKeyListener(new View.OnKeyListener() {
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

    private boolean setListViewHeightByItem(GridView listView, SearchAdapter<NG> adapter) {

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

            if (totalItemsHeight > getScreenHeight()) {
                totalItemsHeight = (getScreenHeight() * 8) / 10;
            }

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + 20;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public interface OnSelectItemCallback {
        void onSelected(NG ng);
    }
}
