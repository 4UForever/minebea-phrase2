package com.devsenses.minebea.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.devsenses.minebea.model.breakmodel.BreakReasonData;
import com.google.gson.Gson;

/**
 * Created by USER on 18/9/2560.
 */

public class CacheHelper {
    private final static String KEY = "MINEBEA_CACHE_KEY";

    private final static String KEY_BREAK_REASON_DATA = "KEY_BREAK_REASON_DATA";

    private final SharedPreferences preferences;

    public CacheHelper(@NonNull Context context) {
        preferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public void saveBreakReasonData(BreakReasonData breakReasonData) {
        preferences.edit().putString(KEY_BREAK_REASON_DATA, new Gson().toJson(breakReasonData)).apply();
    }

    public BreakReasonData getBreakReasonData() {
        try {
            return new Gson().fromJson(preferences.getString(KEY_BREAK_REASON_DATA, ""),
                    BreakReasonData.class);
        } catch (Exception e) {
            Log.e("MineBea", e.getMessage());
            return null;
        }
    }
}
