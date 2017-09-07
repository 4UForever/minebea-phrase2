package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.devsenses.minebea.R;
import com.devsenses.minebea.fragment.GridMenuDocumentFragment;
import com.devsenses.minebea.fragment.ListDocumentFragment;
import com.devsenses.minebea.manager.DocumentManager;
import com.devsenses.minebea.model.documentmodel.DocumentData;

/**
 * Created by pong.p on 12/28/2015.
 */
public class ReportActivity extends BaseModelActivity  implements ListDocumentFragment.OnListDocumentListener,
        GridMenuDocumentFragment.OnGridMenuDocumentListener {
    private static final String TAG = "ReportActivity";

    private DocumentManager documentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);

        setEventLogout();
        hideWorkStatus();
        hideLotNo();
        hideAddNG1();

        if (savedInstanceState == null) {
            initDocumentManager();
        }
    }

    protected void initDocumentManager() {
        documentManager = new DocumentManager(ReportActivity.this, employeeNo, modelId, lineId, processId);
        documentManager.startLoadDocument();
    }

    private void hideWorkStatus() {
        LinearLayout layoutStatus = (LinearLayout) findViewById(R.id.work_status_layout);
        layoutStatus.setVisibility(View.GONE);
    }

    private void hideLotNo() {
        LinearLayout layoutLotNo = (LinearLayout) findViewById(R.id.layoutLotNo);
        layoutLotNo.setVisibility(View.GONE);
    }

    private void hideAddNG1(){
        LinearLayout layoutAddNG1 = (LinearLayout) findViewById(R.id.layoutAddNG1);
        layoutAddNG1.setVisibility(View.GONE);
    }

    protected void setEventLogout() {
        LinearLayout layoutLogout = (LinearLayout) findViewById(R.id.layoutLogout);
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, ScanQrActivity.class);
                ReportActivity.this.finish();
                ReportActivity.this.startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onGridMenuSelected(DocumentData documentDocumentData) {
        documentManager.showListDocument(documentDocumentData);
    }

    @Override
    public void onDocumentSelected(String url, String fileName) {
        documentManager.loadDocument(url, fileName);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (documentManager != null) {
            documentManager.deleteFile();
        }
    }
}
