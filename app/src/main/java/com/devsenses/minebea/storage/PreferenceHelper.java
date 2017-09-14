package com.devsenses.minebea.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.devsenses.minebea.model.ngmodel.NGDetail;
import com.devsenses.minebea.model.ngmodel.NGListData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by USER on 12/9/2560.
 */

public class PreferenceHelper {

    private final static String KEY = "MINEBEA_PREF_KEY";

    private final static String KEY_BREAK_REASON_DATA = "KEY_BREAK_REASON_DATA";
    private final static String KEY_NG1_LIST = "KEY_NG1_LIST";
    private final static String KEY_START_DATE = "KEY_START_DATE";
    private final static String KEY_NG_LIST_DATA = "KEY_NG_LIST_DATA";

    private final SharedPreferences preferences;

    public PreferenceHelper(@NonNull Context context, @NonNull String employeeNo) {
        if (employeeNo.isEmpty()) {
            throw new UnsupportedOperationException("employeeNo must not be empty");
        }
        preferences = context.getSharedPreferences(KEY + employeeNo, Context.MODE_PRIVATE);
    }

    public void saveNg1DetailList(List<NGDetail> list) {
        preferences.edit().putString(KEY_NG1_LIST, new Gson().toJson(list)).apply();
    }

    public List<NGDetail> getNg1DetailList() {
        try {
            Type listType = new TypeToken<List<NGDetail>>() {
            }.getType();
            return new Gson().fromJson(preferences.getString(KEY_NG1_LIST, ""), listType);
        } catch (Exception e) {
            Log.e("MineBea", e.getMessage());
            return null;
        }
    }

    public void saveStartDate(String startDate) {
        preferences.edit().putString(KEY_START_DATE, startDate).apply();
    }

    public String getStartDate() {
        return preferences.getString(KEY_START_DATE, "");
    }

    public void saveBreakReasonData(BreakReasonData breakReasonData) {
        preferences.edit().putString(KEY_BREAK_REASON_DATA, new Gson().toJson(breakReasonData)).apply();
    }

    public BreakReasonData getBreakReasonData() {
        try {
            BreakReasonData data = new Gson().fromJson(preferences.getString(KEY_BREAK_REASON_DATA, ""),
                    BreakReasonData.class);
            String oldText = data.getBreakReason().get(0).getReason();
            data.getBreakReason().get(0).setReason(oldText + " from cache");
            return data;
        } catch (Exception e) {
            Log.e("MineBea", e.getMessage());
            return null;
        }
    }

    public void saveBaseNgListData(NGListData data) {
        preferences.edit().putString(KEY_NG_LIST_DATA, new Gson().toJson(data)).apply();
    }

    public NGListData getBaseNgListData() {
        try {
            return new Gson().fromJson(preferences.getString(KEY_NG_LIST_DATA, ""), NGListData.class);
        } catch (Exception e) {
            Log.e("MineBea", e.getMessage());
            return null;
        }
    }

    public void saveBreakDetailList() {

    }

    public void clearPreference() {
        preferences.edit().clear().apply();
    }
}
