package com.wavy_app.wavytestapplication.utils;

public class Constants {

    public static String url = "http://private-anon-70444b951d-test16231.apiary-mock.com/";

    public static String getBaseUrl() {
        return url + "user/";
    }

    public static class WebApi {
        public static class Response {
            public static final int SUCCESS = 200;
            public static final int NO_INTERNET = 504;
            public static final int TIMEOUT = 408;
        }
    }

}
