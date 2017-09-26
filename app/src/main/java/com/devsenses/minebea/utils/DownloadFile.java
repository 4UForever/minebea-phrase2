package com.devsenses.minebea.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.devsenses.minebea.R;
import com.devsenses.minebea.constant.Constant;
import com.devsenses.minebea.dialog.LoadingDialog;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;

/**
 * Created by Administrator on 2/23/2015.
 */
public class DownloadFile {

    public static void startDownload(final Context context, final String url, final String fileName, final OnDownloadFileCallback callback) {

        //final CircleProgressBar progressBar = new CircleProgressBar(act);
//        Log.d("DownloadManager", "old url : " + url+" file name : "+fileName);
//        url = "http://staging-minebea.devsenses.net/admin/document/195/download";
//        fileName = "customTest.pdf";

//        new DownloadFileTask(callback).execute(url,fileName);

        String path = Constant.pathExternalStorage + Constant.pathMinebea;
        final File file = new File(path, fileName);
        File fileDirectory = new File(path);
        boolean isCanCreatedDirectory = true;
        if (!fileDirectory.exists()) {
            isCanCreatedDirectory = fileDirectory.mkdirs();
        }

        if (!file.exists()) {
            if (isCanCreatedDirectory) {
                final LoadingDialog progressDialog = new LoadingDialog(context).showProgressText();
                progressDialog.show();

                Ion.with(context)
                        .load(url)
                        .progressDialog(progressDialog)
                        .progress(new ProgressCallback() {
                            @Override
                            public void onProgress(long downloaded, long total) {
                                progressDialog.updateProgressDownloading(downloaded, total);

                                Log.d("DownloadManager", "onProgress " + downloaded + "/" + total);
                            }
                        })
                        .write(file)
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File result) {
                                if (result != null) {
                                    if (result.exists()) {
                                        callback.onDownloadFinished(fileName);
                                    } else {
                                        Log.d("DownloadManager", "onFailed " + e.getMessage());
                                        callback.onFailed();
                                    }
                                } else {
                                    callback.onFailed();
                                }

                                progressDialog.dismiss();
                            }

                        });
            } else {
                Toast.makeText(context, R.string.create_directory_error, Toast.LENGTH_SHORT).show();
            }

        } else {
            callback.onDownloadFinished(fileName);
        }
    }

    public interface OnDownloadFileCallback {
        void onDownloadFinished(String fileName);

        void onFailed();
    }
}
