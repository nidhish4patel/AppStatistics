package com.nidhi.as.utils;

import android.util.Log;

public class TraceUtils {

    public static void logException(Exception e) {
        e.printStackTrace();
    }

    public static void logE(String key, String value) {
        Log.e(key, value);
    }

}
