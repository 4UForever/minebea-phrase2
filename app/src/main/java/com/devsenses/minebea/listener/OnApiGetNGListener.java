package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.ngmodel.NGListData;

/**
 * Created by pong.p on 2/4/2016.
 */
public interface OnApiGetNGListener extends OnBaseApiFailure {
    void onSuccess(NGListData ngListData);
}
