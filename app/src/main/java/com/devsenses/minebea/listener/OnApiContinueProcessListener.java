package com.devsenses.minebea.listener;

import com.devsenses.minebea.model.loginmodel.ContinueData;

import java.util.List;

/**
 * Created by USER on 16/12/2560.
 */

public interface OnApiContinueProcessListener extends OnBaseApiFailure {
    void onSuccess(List<ContinueData> continueData);
}
