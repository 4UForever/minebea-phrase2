package com.devsenses.minebea.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Horus on 2/13/2015.
 */
public class Utils {
    public static String getDateCurrent(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy" , Locale.ENGLISH);
        return sdf.format(new Date());
    }
    public static void clearKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void alert(Context context,String title,String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok,null);

        alert.show();
    }

    public static void alert(Context context,String title,String message,DialogInterface.OnClickListener listener){
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener);

        alert.show();
    }

    public static void popConfirmDialog(Context context,String title,String message,DialogInterface.OnClickListener listener){
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.no,null);

        alert.show();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
}
