package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.partmodel.PartModel;

/**
 * Created by pong.p on 1/11/2016.
 */
public interface OnApiGetPartWIPListener extends OnBaseApiFailure {
    void onSuccess(PartModel partModel);
}
