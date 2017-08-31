package com.devsenses.minebea.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.devsenses.minebea.R;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by Horus on 2/11/2015.
 */
public class LoadingDialog extends ProgressDialog {
    protected CircleProgressBar progressBar;
    protected Context context;
    private boolean isProgressTextShow = false;

    public LoadingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_loading);

        setIndeterminate(true);
        setCancelable(false);

        initProgressBar();

        initProgressTextIfNeed();
    }

    protected void initProgressBar() {
        progressBar = (CircleProgressBar) findViewById(R.id.progress);
        progressBar.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
    }

    private void initProgressTextIfNeed() {
        if(isProgressTextShow){
            progressBar.setMax(100);
            progressBar.setShowProgressText(true);
        }
    }

    public LoadingDialog showProgressText() {
        isProgressTextShow = true;
        return this;
    }

    public void updateProgressDownloading(long progress, long total) {
        if(progressBar != null) {
            progressBar.setProgress((int) (progress * 100 / total));
        }
    }

}
