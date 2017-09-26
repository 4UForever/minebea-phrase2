package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.loginmodel.ProcessLog;

/**
 * Created by pong.p on 1/13/2016.
 */
public interface OnApiFailureRecoverProcessListener extends OnBaseApiFailure {
    void onSuccess(ProcessLog processLog);
}
