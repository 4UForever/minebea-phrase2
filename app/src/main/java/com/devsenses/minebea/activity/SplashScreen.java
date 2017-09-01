package com.devsenses.minebea.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.devsenses.minebea.R;
import com.devsenses.minebea.permission.Permission;
import com.devsenses.minebea.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by big.n on 3/9/2017.
 */

public class SplashScreen extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initData();
    }

    private void initData() {
        if (Permission.isPermissionGranted(this)) {
            onGoMainPage();
        }
    }

    private void onGoMainPage() {
        Intent intent = new Intent(this, ScanQrActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Integer> perms = new HashMap<>();
        perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
        perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

        for (int i = 0; i < perms.size(); i++) {
            perms.put(permissions[i], grantResults[i]);
        }

        if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            onGoMainPage();
        }
        else {
            Toast.makeText(this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
        }
    }
}
