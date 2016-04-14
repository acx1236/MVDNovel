package com.ancx.mvdnovel;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ancx on 2016/4/14.
 */
public class NovelApp extends Application {

    private static Context instance;

    public static Context getInstance() {
        return instance;
    }

    public static RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        mQueue = Volley.newRequestQueue(instance);
    }

}
