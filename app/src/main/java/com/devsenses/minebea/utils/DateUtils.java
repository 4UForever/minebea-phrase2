package com.devsenses.minebea.utils;

import android.text.format.DateFormat;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by USER on 17/9/2560.
 */

public final class DateUtils {
    private DateUtils() {
    }

    public static String reFormatDate(Date date) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", date).toString();
    }

    public static String reFormatDate(Calendar calendar) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", calendar).toString();
    }
}
