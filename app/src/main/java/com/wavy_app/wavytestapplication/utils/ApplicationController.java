package com.wavy_app.wavytestapplication.utils;

import android.app.Application;
import android.content.Context;

import com.wavy_app.wavytestapplication.networks.NetworkManager;

/**
 * Created by mugheesanwar on 20/09/15.
 */
public class ApplicationController extends Application {
    /**
     * A singleton instance of the application class for easy access in other
     * places
     */
//    private static ApplicationController sInstance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.getInstance(this);
//        sInstance = this;
    }

}
