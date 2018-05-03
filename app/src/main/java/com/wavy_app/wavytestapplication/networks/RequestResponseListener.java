package com.wavy_app.wavytestapplication.networks;


public interface RequestResponseListener<T> {
    void onResult(Integer response, T object);
}


