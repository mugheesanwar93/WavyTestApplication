package com.wavy_app.wavytestapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class AppUtil {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
