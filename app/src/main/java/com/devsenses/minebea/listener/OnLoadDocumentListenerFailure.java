package com.devsenses.minebea.listener;


import com.devsenses.minebea.model.documentmodel.DocumentModel;

/**
 * Created by Administrator on 2/23/2015.
 */
public interface OnLoadDocumentListenerFailure extends OnBaseApiFailure {
    void onLoadSuccess(DocumentModel documentModel);
}
