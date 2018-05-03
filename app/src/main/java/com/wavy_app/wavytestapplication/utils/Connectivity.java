package com.wavy_app.wavytestapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mugheesanwar on 25/09/2016.
 */

public class Connectivity {

    /**
     * Get the network info
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
    /**
     * Check if there is any connectivity to a Wifi network
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = Connectivity.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

}