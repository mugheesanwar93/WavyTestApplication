
package com.wavy_app.wavytestapplication.networks;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.wavy_app.wavytestapplication.pojo.UserProfileResponse;
import com.wavy_app.wavytestapplication.utils.Constants;

public class NetworkManager {

    public static NetworkManager instance = null;

    public RequestQueue requestQueue;

    private NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
    public static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    public void get(String tag, final RequestResponseListener<Object> listener) {
        final GsonRequest<UserProfileResponse> request = new GsonRequest<>(Request.Method.GET,
                Constants.getBaseUrl() + tag,
                UserProfileResponse.class,
                null,
                new Response.Listener<UserProfileResponse>() {
                    @Override
                    public void onResponse(UserProfileResponse response) {
                        Gson gson = new Gson();
                        String obj = gson.toJson(response);
                        Log.e("NETWORK", "DATA: " + obj);
                        listener.onResult(200, obj);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            listener.onResult(Constants.WebApi.Response.TIMEOUT, null);
                        } else {
                            listener.onResult(Constants.WebApi.Response.NO_INTERNET, null);
                        }
                    }
                }
        );

        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setTag(Constants.getBaseUrl() + tag);
        requestQueue.add(request);
    }

    public void delete(String tag, final RequestResponseListener<Object> listener) {
        final GsonRequest<UserProfileResponse> request = new GsonRequest<>(Request.Method.DELETE,
                Constants.getBaseUrl() + tag,
                UserProfileResponse.class,
                null,
                new Response.Listener<UserProfileResponse>() {
                    @Override
                    public void onResponse(UserProfileResponse response) {
                        Gson gson = new Gson();
                        String obj = gson.toJson(response);
                        Log.e("NETWORK", "DATA: " + obj);
                        listener.onResult(200, obj);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.getClass().equals(TimeoutError.class)) {
                            listener.onResult(Constants.WebApi.Response.TIMEOUT, null);
                        } else {
                            listener.onResult(Constants.WebApi.Response.NO_INTERNET, null);
                        }

                    }
                }
        );

        RetryPolicy policy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        request.setTag(Constants.getBaseUrl() + tag);
        requestQueue.add(request);
    }
}
