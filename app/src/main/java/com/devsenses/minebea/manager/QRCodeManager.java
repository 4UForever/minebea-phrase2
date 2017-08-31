package com.devsenses.minebea.manager;

import android.content.Context;
import android.graphics.PointF;

import com.devsenses.minebea.listener.OnQRCodeHelperListener;
import com.devsenses.minebea.utils.CameraUtils;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

/**
 * Created by pong.p on 12/15/2015.
 */
public class QRCodeManager implements QRCodeReaderView.OnQRCodeReadListener {

    private static int BACK_CAMERA = 0;
    private static int FRONT_CAMERA = 1;
    private static int CAMERA_TYPE = FRONT_CAMERA;

    private QRCodeReaderView qrDecoderView;
    private OnQRCodeHelperListener listener;
    private Context context;

    private boolean qrStatus = true;

    public QRCodeManager(Context context, QRCodeReaderView qrDecoderView) {
        this.context = context;
        this.qrDecoderView = qrDecoderView;

        initCameraType();
        initEvent();
    }

    private void initCameraType() {
        qrDecoderView.setCameraType(CAMERA_TYPE);
    }

    private void initEvent() {
        qrDecoderView.setOnQRCodeReadListener(this);
    }

    public void setQRCodeListener(final OnQRCodeHelperListener listener){
        this.listener = listener;
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        if(listener!=null) {
            listener.onQRCodeReadListener(text);
        }
    }

    @Override
    public void cameraNotFound() {
        // Called when your device have no camera
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
        // Called when there's no QR codes in the camera preview image
    }

    public void startCameraPreviewIfCan() {
        if (CameraUtils.checkHasCamera(context)) {
            if (!qrStatus) {
                qrDecoderView.getCameraManager().startPreview();
                qrStatus = true;
            }
        }
    }

    public void stopCameraPreviewIfCan() {
        if (CameraUtils.checkHasCamera(context)) {
            if (qrStatus) {
                qrDecoderView.getCameraManager().stopPreview();
                qrStatus = false;
            }
        }
    }
}
