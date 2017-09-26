package com.devsenses.minebea.manager;

import android.os.Bundle;

import com.devsenses.minebea.model.breakmodel.BreakStep;
import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;
import com.devsenses.minebea.model.loginmodel.SelectedModel;
import com.devsenses.minebea.model.loginmodel.Shift;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.devsenses.minebea.model.partmodel.PartData;
import com.devsenses.minebea.model.partmodel.RecoverPartModel;

import org.parceler.Parcels;

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
    public static final String KEY_WORKING_DATE = "workingDate";
    public static final String KEY_BASE_NG_LIST_DATA = "ngListData";
    public static final String KEY_NG_1_LIST = "ng1";
    public static final String KEY_BREAK_STEP_LIST = "breaks";
    public static final String KEY_SETUP = "setup";
    public static final String KEY_DT = "dt";
    public static final String KEY_START_DATE = "start_date";
    public static final String KEY_END_DATE = "end_date";

    public static Bundle putLoginModelDataToBundle(Bundle bundle, String workingDate, Shift shift, Model model, Line line, Process process) {

        setWorkingDateData(bundle, workingDate);
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

        setWorkingDate(bundle, selectedModel.getWorkingDate());

        setShiftId(bundle, selectedModel.getID());
        setShiftTime(bundle, selectedModel.getShiftTitle());

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

    public static Bundle putBaseNgList(Bundle bundle, NGListData listData) {
        setBaseNgList(bundle, listData);

        return bundle;
    }

    private static void setBaseNgList(Bundle bundle, NGListData listData) {
        bundle.putParcelable(KEY_BASE_NG_LIST_DATA, Parcels.wrap(listData));
    }

    public static NGListData getBaseNgList(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(KEY_BASE_NG_LIST_DATA));
    }

    public static Bundle putSummaryWorkingData(Bundle bundle, List<NGDetail> ng1List,
                                               List<BreakStep> breakStepList, String setup,
                                               String dt, String startDate, String endDate) {
        setNg1List(bundle, ng1List);
        setBreakStepList(bundle, breakStepList);
        setSetup(bundle, setup);
        setDt(bundle, dt);
        setStartDate(bundle, startDate);
        setEndDate(bundle, endDate);

        return bundle;
    }

    private static void setNg1List(Bundle bundle, List<NGDetail> ng1List) {
        bundle.putParcelable(KEY_NG_1_LIST, Parcels.wrap(ng1List));
    }

    public static List<NGDetail> getNg1List(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(KEY_NG_1_LIST));
    }

    private static void setBreakStepList(Bundle bundle, List<BreakStep> breakStepList) {
        bundle.putParcelable(KEY_BREAK_STEP_LIST, Parcels.wrap(breakStepList));
    }

    public static List<BreakStep> getBreakStepList(Bundle bundle) {
        return Parcels.unwrap(bundle.getParcelable(KEY_BREAK_STEP_LIST));
    }

    private static void setSetup(Bundle bundle, String setup) {
        bundle.putString(KEY_SETUP, setup);
    }

    public static String getSetup(Bundle bundle) {
        return bundle.getString(KEY_SETUP, "0");
    }

    private static void setDt(Bundle bundle, String dt) {
        bundle.putString(KEY_DT, dt);
    }

    public static String getDt(Bundle bundle) {
        return bundle.getString(KEY_DT, "0");
    }

    private static void setStartDate(Bundle bundle, String startDate) {
        bundle.putString(KEY_START_DATE, startDate);
    }

    public static String getStartDate(Bundle bundle) {
        return bundle.getString(KEY_START_DATE, "");
    }

    private static void setEndDate(Bundle bundle, String endDate) {
        bundle.putString(KEY_END_DATE, endDate);
    }

    public static String getEndDate(Bundle bundle) {
        return bundle.getString(KEY_END_DATE, "");
    }

    public static RecoverPartModel getRecoverPartAndWIPData(Bundle bundle) {
        return (RecoverPartModel) bundle.getParcelable(KEY_RECOVER_PART);
    }

    /* Date */
    public static void setWorkingDateData(Bundle bundle, String date) {
        setWorkingDate(bundle, date);
    }

    public static String getWorkingDate(Bundle bundle) {
        return bundle.getString(KEY_WORKING_DATE, "No model title");
    }

    public static void setWorkingDate(Bundle bundle, String mWorkingDate) {
        bundle.putString(BundleManager.KEY_WORKING_DATE, mWorkingDate);
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

    /*On BreakStep*/
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
