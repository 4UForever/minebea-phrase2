package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.breakmodel.BreakReasonData;

/**
 * Created by pong.p on 2/2/2016.
 */
public interface OnApiGetReasonListener extends OnBaseApiFailure {
    void onSuccess(BreakReasonData breakReasonData);
}
