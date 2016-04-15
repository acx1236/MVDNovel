package com.ancx.mvdnovel.util;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ancx on 2016/4/14.
 */
public class HttpUtil {

    private static RequestQueue mQueue;

    private static String ETag;

    public static RequestQueue getQueue() {
        if (mQueue == null)
            mQueue = Volley.newRequestQueue(NovelApp.getInstance());
        return mQueue;
    }

    public static void addRequest(String httpUrl, final HttpRequestListener httpRequestListener) {
        StringRequest request = new StringRequest(httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MsgUtil.LogTag(response);
                if (httpRequestListener != null) {
                    httpRequestListener.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MsgUtil.LogTag(error.getMessage() + "\n" + error.getLocalizedMessage());
                if (httpRequestListener != null) {
                    httpRequestListener.onError(error);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("If-None-Match", ETag);
                return super.getHeaders();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                ETag = responseHeaders.get("ETag");
                MsgUtil.LogTag("ETag = " + ETag);
                return super.parseNetworkResponse(response);
            }
        };
        request.setTag(httpUrl);
        getQueue().add(request);
    }

    public static void cancelRequest(String httpUrl) {
        getQueue().cancelAll(httpUrl);
    }

}
