package com.longxiang.woniuke.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mac on 16/9/24.
 */
public class NetUtils {

        public static boolean detect(Context context) {

            ConnectivityManager manager = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);

            if (manager == null) {
                return false;
            }

            NetworkInfo networkinfo = manager.getActiveNetworkInfo();

            if (networkinfo == null || !networkinfo.isAvailable()) {
                return false;
            }

            return true;
        }
    }

