package com.devsenses.minebea.component;

import android.content.Context;

import com.devsenses.minebea.model.ngmodel.NG;

import java.util.List;

/**
 * Created by pong.p on 2/3/2016.
 */
public class NGContain implements NGItemLayout.OnNGSelectedListener {
    public NGItemLayout layout;
    private OnSelectedNGPerItem listener;

    public NGContain(Context context, List<NG> list, OnSelectedNGPerItem listener) {
        this.listener = listener;
        layout = new NGItemLayout(context, list, this);
    }

    @Override
    public void onSelected(NG ng) {
        listener.onUpdateList();
    }

    @Override
    public void onUpdateQuantity() {
        listener.onUpdateQuantity();
    }

    @Override
    public void onDelete() {
        listener.onDeleteNGRow(this);
    }

    public NG getSelectedNG() {
        return layout.getSelectedNG();
    }

    public int getNGQuantity() {
        return layout.getSelectedNGQuantity();
    }

    public void updateList(List<NG> list) {
        layout.updateNGList(list);
    }

    public interface OnSelectedNGPerItem {
        void onUpdateList();
        void onUpdateQuantity();
        void onDeleteNGRow(NGContain contain);
    }
}
