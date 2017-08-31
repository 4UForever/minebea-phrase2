package com.devsenses.minebea.permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.devsenses.minebea.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by big.n on 3/7/2017.
 */

public class Permission {

    public static boolean isPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            int writeExternalStorePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            List<String> permissionGroups = new ArrayList<>();

            if (cameraPermission == PackageManager.PERMISSION_GRANTED && writeExternalStorePermission == PackageManager.PERMISSION_GRANTED) {
                Log.v("TEST", "Permission is granted");
                return true;
            } else {

                if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                    permissionGroups.add(Manifest.permission.CAMERA);
                }

                if (writeExternalStorePermission != PackageManager.PERMISSION_GRANTED) {
                    permissionGroups.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

                Log.v("TEST", "Permission is revoked");
                activity.requestPermissions(permissionGroups.toArray(new String[permissionGroups.size()]), 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TEST", "Permission is granted");
            return true;
        }
    }
}
