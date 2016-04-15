package com.ancx.mvdnovel.util;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ancx on 2016/4/14.
 */
public class HttpUtil {

    private static RequestQueue mQueue;

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
        });
        request.setTag(httpUrl);
        getQueue().add(request);
    }

    public static void cancelRequest(String httpUrl) {
        getQueue().cancelAll(httpUrl);
    }

}
