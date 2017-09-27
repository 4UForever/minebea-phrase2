package com.devsenses.minebea.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.fragment.ScanQRFragment;
import com.devsenses.minebea.listener.OnApiGetNGListener;
import com.devsenses.minebea.listener.OnApiGetPartWIPListener;
import com.devsenses.minebea.listener.OnQRCodeHelperListener;
import com.devsenses.minebea.manager.BundleManager;
import com.devsenses.minebea.manager.LineLeaderManager;
import com.devsenses.minebea.manager.PartAndWIPViewManager;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.model.partmodel.LotDataModel;
import com.devsenses.minebea.model.partmodel.LotNo;
import com.devsenses.minebea.model.partmodel.Part;
import com.devsenses.minebea.model.partmodel.PartData;
import com.devsenses.minebea.model.partmodel.PartModel;
import com.devsenses.minebea.storage.PreferenceHelper;
import com.devsenses.minebea.task.TaskNG;
import com.devsenses.minebea.task.TaskPartAndWIP;
import com.devsenses.minebea.utils.Utils;

import java.util.List;

/**
 * Created by pong.p on 12/21/2015.
 */
public class PartAndWIPActivity extends BaseModelActivity {
    private static final String KEY_PART_MODEL = "key_partModel";

    private FrameLayout container;
    private LinearLayout layoutDone;

    private PartModel partModel;

    private PartAndWIPViewManager partViewManager;
    private LineLeaderManager lineLeaderManager;
    private PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_part_wip);

        preferenceHelper = new PreferenceHelper(PartAndWIPActivity.this, employeeNo);

        initUI();
        initDoneEvent();
        Utils.clearKeyboard(PartAndWIPActivity.this);

        if (savedInstanceState == null) {
            requestPart();
        }
    }

    private void initUI() {
        container = (FrameLayout) findViewById(R.id.container);
        container.setVisibility(View.GONE);
        layoutDone = (LinearLayout) findViewById(R.id.btn_done_layout);

        LinearLayout layoutLotNo = (LinearLayout) findViewById(R.id.layoutLotNo);
        layoutLotNo.setVisibility(View.GONE);
    }

    private void initDoneEvent() {
        layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.clearKeyboard(PartAndWIPActivity.this);
                collectDataFromViewManager();
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        partModel = savedInstanceState.getParcelable(KEY_PART_MODEL);
        if (partModel != null) {
            addPartAndWIPList(partModel.getPartData());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (partModel != null) {
            outState.putParcelable(KEY_PART_MODEL, partModel);
        }
        super.onSaveInstanceState(outState);
    }

    private void requestPart() {
        TaskPartAndWIP.getPart(PartAndWIPActivity.this, BundleManager.getEmployeeNo(bundle), BundleManager.getModelID(bundle),
                BundleManager.getProcessID(bundle), new OnApiGetPartWIPListener() {
                    @Override
                    public void onSuccess(PartModel model) {
                        partModel = model;
                        addPartAndWIPList(model.getPartData());
                    }

                    @Override
                    public void onFailure(String reason) {
                        DialogWithText.showMessage(PartAndWIPActivity.this, reason);
                    }
                });
    }

    private void addPartAndWIPList(PartData partData) {
//        Log.d("Task", "loaded part list : " + partData.getPartList());
//        Log.d("Task", "recover part list : " + BundleManager.getRecoverPartAndWIPData(bundle).getParts());

        partViewManager = new PartAndWIPViewManager(PartAndWIPActivity.this,
                this.findViewById(android.R.id.content), partData.getPartList());
        partViewManager.setOnEachScanButtonClick(new PartAndWIPViewManager.OnQRCodeRead() {
            @Override
            public void onRead(EditText editText) {
                Utils.clearKeyboard(PartAndWIPActivity.this);
                openScanQRFragment(editText);
            }
        });

        try {
            partViewManager.updateRecoverPartData(BundleManager.getRecoverPartAndWIPData(bundle).getParts());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void collectDataFromViewManager() {
        if (partViewManager == null) return;
        partViewManager.getDataInForm(new PartAndWIPViewManager.OnGetFormDataListener() {
            @Override
            public void onOK(List<Part> partList, List<LotNo> wipLotList) {
//                Log.d("PartAndWIPActivity", partList.toString()+"\n"+ wipLotList.toString());
                callCheckPartAndWIP(partList.toString(), wipLotList.toString());
            }
        });
    }

    private void callCheckPartAndWIP(String partJsonString, String wipJsonString) {
        TaskPartAndWIP.checkPartAvailable(PartAndWIPActivity.this, BundleManager.getEmployeeNo(bundle),
                partJsonString, wipJsonString, new TaskPartAndWIP.OnCheckPartListener() {
                    @Override
                    public void onSuccess(LotDataModel lotDataModel) {
                        startFillLineLeaderInfo(BundleManager.getEmployeeNo(bundle), lotDataModel);
                    }

                    @Override
                    public void onFailure(String reason) {
                        DialogWithText.showMessage(PartAndWIPActivity.this, reason);
                    }
                });
    }

    private void openScanQRFragment(EditText editText) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ScanQRFragment scanQRFragment = ScanQRFragment.newInstance(ScanQRFragment.SCAN_PART_WIP_LOT);
        scanQRFragment.setViewCreatedListener(new OnScanQRFragmentViewCreated(editText));
        ft.add(R.id.container, scanQRFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private class OnScanQRFragmentViewCreated implements ScanQRFragment.OnCompleteListener {
        private EditText editText;

        public OnScanQRFragmentViewCreated(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void onComplete(ScanQRFragment fragment) {
            container.setVisibility(View.VISIBLE);
            fragment.setOnQRReadListener(new OnQRCodeHelperListener() {
                @Override
                public void onQRCodeReadListener(String code) {
                    getSupportFragmentManager().popBackStack();
                    container.setVisibility(View.GONE);
                    editText.setText(code);
                    editText.requestFocus();
                }
            });
            fragment.setOnTopRightCornerViewClickListener(fragment.getTopRightCornerView(),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            container.setVisibility(View.GONE);
                            getSupportFragmentManager().popBackStack();
                        }
                    });
        }
    }

    private void startFillLineLeaderInfo(String empNo, LotDataModel lotDataModel) {
        if (lineLeaderManager == null) {
            lineLeaderManager = new LineLeaderManager(PartAndWIPActivity.this, lotDataModel, preferenceHelper);
        }
        lineLeaderManager.startFillLineLeaderInfo(empNo, new LineLeaderManager.OnSendLineLeaderCallback() {
            @Override
            public void onFinished(String lotNo) {
                Utils.clearKeyboard(PartAndWIPActivity.this);
                BundleManager.setLotNo(bundle, lotNo);
                loadNgDetailList();
            }
        });
    }

    private void loadNgDetailList() {
        TaskNG.getNGList(PartAndWIPActivity.this, employeeNo, new OnApiGetNGListener() {
            @Override
            public void onSuccess(NGListData ngListData) {
                bundle = BundleManager.putBaseNgList(bundle, ngListData);
                preferenceHelper.saveBaseNgListData(ngListData);
                startMainActivity(bundle);
            }

            @Override
            public void onFailure(String reason) {
                DialogWithText.showMessage(PartAndWIPActivity.this, reason + "\nPlease try again.",
                        new DialogWithText.OnClickListener() {
                            @Override
                            public void onClick() {
                                loadNgDetailList();
                            }
                        });
            }
        });
    }

    private void startMainActivity(final Bundle bundle) {
        Intent mainIntent = new Intent(PartAndWIPActivity.this, MainActivity.class);
        mainIntent.putExtras(bundle);
        PartAndWIPActivity.this.startActivity(mainIntent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }
}
