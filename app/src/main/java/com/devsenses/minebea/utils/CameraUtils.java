package com.devsenses.minebea.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Administrator on 3/26/2015.
 */
public class CameraUtils {

    public static boolean checkHasCamera(Context context){
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //yes
            Log.i("camera", "This device has camera!");
            return true;
        }else{
            //no
            Log.i("camera", "This device has no camera!");
            return false;
        }
    }
}
