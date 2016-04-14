package com.ancx.mvdnovel.listener;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface HttpRequestListener {

    void onSuccess(String response);

    void onError(VolleyError error);
}
