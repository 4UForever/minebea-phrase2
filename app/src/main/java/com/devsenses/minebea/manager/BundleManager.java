package com.devsenses.minebea.manager;

import android.os.Bundle;

import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;
import com.devsenses.minebea.model.loginmodel.SelectedModel;
import com.devsenses.minebea.model.loginmodel.Shift;
import com.devsenses.minebea.model.partmodel.PartData;
import com.devsenses.minebea.model.partmodel.RecoverPartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pong.p on 12/17/2015.
 */
public class BundleManager {

    public static final String KEY_PROCESS_LIST = "processList";
    public static final String KEY_PROCESS_ID = "processId";
    public static final String KEY_PROCESS_NAME = "processName";
    public static final String KEY_PROCESS_NUMBER = "processNumber";
    public static final String KEY_LINE_ID = "idLine";
    public static final String KEY_LINE_NAME = "LineName";
    public static final String KEY_MODEL_ID = "idModel";
    public static final String KEY_MODEL_NAME = "modelName";
    public static final String KEY_QR_CODE = "qrCode";
    public static final String KEY_IS_VIEW = "isView";
    public static final String KEY_IS_WORK = "isWork";
    public static final String KEY_PART_DATA = "partData";
    public static final String KEY_LOT_NO = "lotNo";
    public static final String KEY_ON_BREAK = "onBreak";
    public static final String KEY_RECOVER_PART = "recoverPart";
    public static final String KEY_SHIFT_ID = "idShift";
    public static final String KEY_SHIFT_NAME = "shiftName";

    public static Bundle putLoginModelDataToBundle(Bundle bundle, Shift shift, Model model, Line line, Process process) {

        setShiftData(bundle, shift);
        setModelData(bundle, model);
        setLineData(bundle, line);
        setProcessList(bundle, line.getProcesses());

        setProcessID(bundle, process.getId());
        setProcessTitle(bundle, process.getTitle());
        setProcessNumber(bundle, process.getNumber());
        setOnBreak(bundle, (long) 0);
        return bundle;
    }

    public static Bundle putSelectedModelDataToBundle(Bundle bundle, SelectedModel selectedModel) {
        setShiftId(bundle, selectedModel.getID());
        setShiftTime(bundle, selectedModel.getTime());

        setModelId(bundle, selectedModel.getID());
        setModelTitle(bundle, selectedModel.getTitle());

        setLineId(bundle, selectedModel.getLineID());
        setLineTitle(bundle, selectedModel.getLineTitle());

        setProcessID(bundle, selectedModel.getProcessID());
        setProcessTitle(bundle, selectedModel.getProcessTitle());
        setProcessNumber(bundle, selectedModel.getProcessNumber());
        setLotNo(bundle, selectedModel.getLotNumber());

        setOnBreak(bundle, selectedModel.getOnBreak());
        return bundle;
    }

    public static Bundle putUserDataToBundle(Bundle bundle, String employeeNo, boolean isWork, boolean isView) {
        setEmployeeNo(bundle, employeeNo);
        setIsWork(bundle, isWork);
        setIsView(bundle, isView);
        return bundle;
    }

    public static Bundle putLotNo(Bundle bundle, String lotNo) {
        setLotNo(bundle, lotNo);
        return bundle;
    }

    public static Bundle putRecoverPartAndWIPData(Bundle bundle, RecoverPartModel recoverPartModel) {
        bundle.putParcelable(KEY_RECOVER_PART, recoverPartModel);
        return bundle;
    }

    public static RecoverPartModel getRecoverPartAndWIPData(Bundle bundle) {
        return (RecoverPartModel) bundle.getParcelable(KEY_RECOVER_PART);
    }


    /* Shift */
    public static void setShiftData(Bundle bundle, Shift shift) {
        setShiftId(bundle, shift.getId());
        setShiftTime(bundle, shift.getTime());
    }

    public static void setShiftId(Bundle bundle, long id) {
        bundle.putLong(BundleManager.KEY_SHIFT_ID, id);
    }

    public static String getShiftTime(Bundle bundle) {
        return bundle.getString(KEY_SHIFT_NAME, "No model title");
    }

    public static void setShiftTime(Bundle bundle, String time) {
        bundle.putString(BundleManager.KEY_SHIFT_NAME, time);
    }

    public static long getShiftID(Bundle bundle) {
        return bundle.getLong(KEY_SHIFT_ID, 0);
    }

    /* Model */
    public static void setModelData(Bundle bundle, Model model) {
        setModelId(bundle, model.getId());
        setModelTitle(bundle, model.getTitle());
    }

    public static void setModelId(Bundle bundle, long id) {
        bundle.putLong(BundleManager.KEY_MODEL_ID, id);
    }

    public static String getModelTitle(Bundle bundle) {
        return bundle.getString(KEY_MODEL_NAME, "No model title");
    }

    public static void setModelTitle(Bundle bundle, String title) {
        bundle.putString(BundleManager.KEY_MODEL_NAME, title);
    }

    public static long getModelID(Bundle bundle) {
        return bundle.getLong(KEY_MODEL_ID, 0);
    }


    /* Line */
    public static void setLineData(Bundle bundle, Line line) {
        setLineId(bundle, line.getId());
        setLineTitle(bundle, line.getTitle());
    }

    private static void setLineId(Bundle bundle, long id) {
        bundle.putLong(BundleManager.KEY_LINE_ID, id);
    }

    private static void setLineTitle(Bundle bundle, String title) {
        bundle.putString(BundleManager.KEY_LINE_NAME, title);
    }

    public static String getLineTitle(Bundle bundle) {
        return bundle.getString(KEY_LINE_NAME, "No line title");
    }

    public static long getLineID(Bundle bundle) {
        return bundle.getLong(KEY_LINE_ID, 0);
    }


    /* Process */
    public static void setProcessList(Bundle bundle, List<Process> processList) {
        bundle.putParcelableArrayList(BundleManager.KEY_PROCESS_LIST, (ArrayList<Process>) processList);
    }

    public static List<Process> getProcessList(Bundle bundle) {
        return bundle.getParcelableArrayList(BundleManager.KEY_PROCESS_LIST);
    }

    public static void setProcessID(Bundle bundle, long processId) {
        bundle.putLong(BundleManager.KEY_PROCESS_ID, processId);
    }

    public static long getProcessID(Bundle bundle) {
        return bundle.getLong(BundleManager.KEY_PROCESS_ID);
    }

    public static void setProcessTitle(Bundle bundle, String title) {
        bundle.putString(BundleManager.KEY_PROCESS_NAME, title);
    }

    public static String getProcessTitle(Bundle bundle) {
        return bundle.getString(BundleManager.KEY_PROCESS_NAME);
    }

    public static void setProcessNumber(Bundle bundle, String number) {
        bundle.putString(BundleManager.KEY_PROCESS_NUMBER, number);
    }

    public static String getProcessNumber(Bundle bundle) {
        return bundle.getString(BundleManager.KEY_PROCESS_NUMBER);
    }


    /* Employee No */
    public static void setEmployeeNo(Bundle bundle, String employeeNo) {
        bundle.putString(BundleManager.KEY_QR_CODE, employeeNo);
    }

    public static String getEmployeeNo(Bundle bundle) {
        return bundle.getString(KEY_QR_CODE, "Unknown.");
    }

    /* Permission */
    public static void setIsWork(Bundle bundle, Boolean isWork) {
        bundle.putBoolean(BundleManager.KEY_IS_WORK, isWork);
    }

    public static Boolean getIsWork(Bundle bundle) {
        return bundle.getBoolean(BundleManager.KEY_IS_WORK, false);
    }

    public static void setIsView(Bundle bundle, Boolean isView) {
        bundle.putBoolean(BundleManager.KEY_IS_VIEW, isView);
    }

    public static Boolean getIsView(Bundle bundle) {
        return bundle.getBoolean(BundleManager.KEY_IS_VIEW, false);
    }

    /* Part Data */
    public static Bundle putPartData(Bundle bundle, PartData partData) {
        bundle.putParcelable(BundleManager.KEY_PART_DATA, partData);
        return bundle;
    }

    public static PartData getPartData(Bundle bundle) {
        return bundle.getParcelable(BundleManager.KEY_PART_DATA);
    }

    /*Lot No*/
    public static void setLotNo(Bundle bundle, String lotNo) {
        bundle.putString(KEY_LOT_NO, lotNo);
    }

    public static String getLotNo(Bundle bundle) {
        return bundle.getString(KEY_LOT_NO);
    }

    /*On Break*/
    public static void setOnBreak(Bundle bundle, Long onBreak) {
        if (onBreak == null) onBreak = (long) 0;
        bundle.putLong(KEY_ON_BREAK, onBreak);
    }

    public static Long getOnBreak(Bundle bundle) {
        try {
            return bundle.getLong(KEY_ON_BREAK);
        } catch (NullPointerException e) {
            return (long) 0;
        }

    }

}
