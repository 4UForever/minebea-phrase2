package com.devsenses.minebea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devsenses.minebea.R;
import com.devsenses.minebea.dialog.DialogWithText;
import com.devsenses.minebea.listener.OnQRCodeHelperListener;
import com.devsenses.minebea.manager.QRCodeManager;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * Created by pong.p on 12/21/2015.
 */
public class ScanQRFragment extends Fragment {
    private static final String KEY_CAMERA_STAGE = "key_camera_stage";

    public static final String SCAN_EMPLOYEE_NO = "scan_employee_no";
    public static final String SCAN_PART_WIP_LOT = "scan_part_and_wip";

    private String cameraStage;

    private ImageView btnEmployeeNo, btnPartAndWIP;
    private TextView titleScanQRTextView;

    private QRCodeManager qrCodeManager;
    private QRCodeReaderView qrDecoderView;

    private OnCompleteListener onCompleteListener;

    public static ScanQRFragment newInstance(String cameraStage) {
        ScanQRFragment fragment = new ScanQRFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_CAMERA_STAGE, cameraStage);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scan_qr, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cameraStage = getArguments().getString(KEY_CAMERA_STAGE);

        initData();
    }

    private void initData() {
        initUI(getView());
        initViewByStage();
        initQRCodeManager();
        if (onCompleteListener != null) {
            onCompleteListener.onComplete(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        qrCodeManager.startCameraPreviewIfCan();
    }

    @Override
    public void onPause() {
        super.onPause();
        qrCodeManager.stopCameraPreviewIfCan();
    }

    private void initUI(View view) {
        qrDecoderView = (QRCodeReaderView) view.findViewById(R.id.qrDecoderView);
//        btnScan = (ImageView) view.findViewById(R.id.imageScan);
        btnEmployeeNo = (ImageView) view.findViewById(R.id.imageEmp_no);
        btnPartAndWIP = (ImageView) view.findViewById(R.id.imageLotList);
        titleScanQRTextView = (TextView) view.findViewById(R.id.titleScanQRTextView);
    }

    private void initViewByStage() {
        if (cameraStage.equals(SCAN_PART_WIP_LOT)) {
            btnEmployeeNo.setVisibility(View.GONE);
            btnPartAndWIP.setVisibility(View.VISIBLE);
            titleScanQRTextView.setText(R.string.text_scan_qr_fill_data);
        }
    }

    private void initQRCodeManager() {
        qrCodeManager = new QRCodeManager(getActivity(), qrDecoderView);
    }

    public void setViewCreatedListener(OnCompleteListener completeListener) {
        this.onCompleteListener = completeListener;
    }

    public void setOnTopRightCornerViewClickListener(View view, View.OnClickListener listener) {
        view.setOnClickListener(new OnDefaultClickEventListener(listener));
    }

    public View getTopRightCornerView() {
        if (cameraStage.equals(SCAN_EMPLOYEE_NO)) {
            return btnEmployeeNo;
        } else {
            return btnPartAndWIP;
        }
    }

    private class OnDefaultClickEventListener implements View.OnClickListener {
        private View.OnClickListener listener;

        OnDefaultClickEventListener(View.OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            qrCodeManager.stopCameraPreviewIfCan();
            listener.onClick(v);
        }
    }

    public void setOnQRReadListener(final OnQRCodeHelperListener listener) {
        qrCodeManager.setQRCodeListener(new OnQRCodeHelperListener() {
            @Override
            public void onQRCodeReadListener(String qrCode) {
                if (!qrCode.isEmpty() && qrCode.length() > 0) {
                    qrCodeManager.stopCameraPreviewIfCan();
                    Log.d("ScanQRFragment", "onQRCodeReadListener : " + qrCode);
                    listener.onQRCodeReadListener(qrCode);
//                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    DialogWithText.showMessage(getActivity(), "No data in this QR Code");
                }
            }
        });
    }

    public void enableCornerButton() {
        btnEmployeeNo.setEnabled(true);
    }

    public void disableCornerButton() {
        btnEmployeeNo.setEnabled(false);
    }

    public void startCamera() {
        qrCodeManager.startCameraPreviewIfCan();
    }

    public void stopCamera() {
        qrCodeManager.stopCameraPreviewIfCan();
    }

    public interface OnCompleteListener {
        void onComplete(ScanQRFragment fragment);
    }

}
