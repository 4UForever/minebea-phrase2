package com.devsenses.minebea.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;

/**
 * Created by USER on 12/9/2560.
 */

public final class NetworkUtils {
    private NetworkUtils() {

    }

    public static boolean isNetworkConnect(@NonNull Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else if (Build.VERSION.SDK_INT >= 21) {
            Network[] info = connectivity.getAllNetworks();
            if (info != null) {
                for (Network anInfo : info) {
                    if (anInfo != null && connectivity.getNetworkInfo(anInfo).isConnected()) {
                        return true;
                    }
                }
            }
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
            final NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }
        }

        return false;
    }
}
