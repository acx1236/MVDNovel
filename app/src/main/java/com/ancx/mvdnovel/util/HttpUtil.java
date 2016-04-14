package com.ancx.mvdnovel.util;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Administrator on 2016/4/14.
 */
public class HttpUtil {

    public static void addRequest(String httpUrl, final HttpRequestListener httpRequestListener) {
        StringRequest request = new StringRequest(httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (httpRequestListener != null) {
                    httpRequestListener.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (httpRequestListener != null) {
                    httpRequestListener.onError(error);
                }
            }
        });
        request.setTag(request);
        NovelApp.mQueue.add(request);
    }

    public static void cancelRequest(String request) {
        NovelApp.mQueue.cancelAll(request);
    }

}
