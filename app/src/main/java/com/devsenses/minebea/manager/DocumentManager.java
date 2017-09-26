package com.devsenses.minebea.manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devsenses.minebea.R;
import com.devsenses.minebea.constant.Constant;
import com.devsenses.minebea.fragment.GridMenuDocumentFragment;
import com.devsenses.minebea.fragment.ListDocumentFragment;
import com.devsenses.minebea.listener.OnLoadDocumentListenerFailure;
import com.devsenses.minebea.model.documentmodel.DocumentData;
import com.devsenses.minebea.model.documentmodel.DocumentModel;
import com.devsenses.minebea.task.TaskDocument;
import com.devsenses.minebea.utils.DownloadFile;

import java.io.File;

/**
 * Created by pong.p on 2/9/2016.
 */
public class DocumentManager {

    private FragmentActivity activity;
    private String employeeNo;
    private long modelId;
    private long lineId;
    private long processId;

    public DocumentManager() {
    }

    public DocumentManager(FragmentActivity activity, String employeeNo, long modelId, long lineId, long processId) {
        this.activity = activity;
        this.employeeNo = employeeNo;
        this.modelId = modelId;
        this.lineId = lineId;
        this.processId = processId;
    }

    public void startLoadDocument() {
        //4,6,129 for test PDF
        TaskDocument.getDocumentList(activity, employeeNo, modelId, lineId,
                processId, new OnLoadDocumentListenerFailure() {
                    @Override
                    public void onLoadSuccess(DocumentModel model) {
                        showGridDocument(model);
                    }

                    @Override
                    public void onFailure(String reason) {
                        setTextViewIfNoDocument();
                    }
                });
    }

    protected void showGridDocument(final DocumentModel model) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentDocument, GridMenuDocumentFragment.newInstance(model));
        fragmentTransaction.commit();
    }

    protected void setTextViewIfNoDocument() {
        FrameLayout frameLayout = (FrameLayout) activity.findViewById(R.id.fragmentDocument);
        TextView textView = new TextView(activity.getBaseContext());
        textView.setText("Can't load document category");
        textView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        frameLayout.addView(textView);
    }

    public void showListDocument(DocumentData data) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentDocument, ListDocumentFragment.newInstance(data, employeeNo));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void loadDocument(String url, final String fileName) {
        if (url == null || url.isEmpty()) {
            Toast.makeText(activity, "URL can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (fileName == null || fileName.isEmpty()) {
            Toast.makeText(activity, "File name can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        DownloadFile.startDownload(activity, url + "?qr_code=" + employeeNo, fileName, new DownloadFile.OnDownloadFileCallback() {
            @Override
            public void onDownloadFinished(String fileName) {
                openDocument(fileName);
            }

            @Override
            public void onFailed() {
                Toast.makeText(activity, "Download Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openDocument(final String fileName) {
//        Log.d(TAG, "openDocument() : " + fileName);
        File file = new File(Constant.pathExternalStorage + Constant.pathMinebea + "/" + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }

    public void deleteFile() {
        File dir = new File(Constant.pathExternalStorage + Constant.pathMinebea);
        try {
            if (dir.exists() && dir.isDirectory()) {
                File[] fileList = dir.listFiles();
                for (File childFile : fileList) {
                    childFile.delete();
                }
            }
        } catch (NullPointerException e) {
            Log.d("DocumentManager", "deleteFile(): " + e.getMessage());
        }
    }
}
