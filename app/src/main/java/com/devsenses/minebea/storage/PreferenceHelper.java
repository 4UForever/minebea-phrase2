package com.devsenses.minebea.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.google.gson.Gson;

/**
 * Created by USER on 12/9/2560.
 */

public class PreferenceHelper {

    private final static String KEY = "MINEBEA_PREF_KEY";

    private final static String KEY_BREAK_REASON_DATA = "KEY_BREAK_REASON_DATA";
    private final SharedPreferences preferences;

    public PreferenceHelper(@NonNull Context context, @NonNull String employeeNo) {
        if (employeeNo.isEmpty()) {
            throw new UnsupportedOperationException("employeeNo must not be empty");
        }
        preferences = context.getSharedPreferences(KEY + employeeNo, Context.MODE_PRIVATE);
    }

    public void saveNg1DetailList() {

    }

    public void saveStartDate() {

    }

    public void saveBreakDetailList() {

    }

    public void saveBreakReasonData(BreakReasonData breakReasonData) {
        preferences.edit().putString(KEY_BREAK_REASON_DATA, new Gson().toJson(breakReasonData)).apply();
    }

    public BreakReasonData getBreakReasonData() {
        try {
            BreakReasonData data = new Gson().fromJson(preferences.getString(KEY_BREAK_REASON_DATA, ""),
                    BreakReasonData.class);
            data.getBreakReason().get(0).setReason("those reason is from cache");
            return data;
        } catch (Exception e) {
            Log.e("MineBea", e.getMessage());
            return null;
        }
    }

    public void clearPreference() {
        preferences.edit().clear().apply();
    }
}
