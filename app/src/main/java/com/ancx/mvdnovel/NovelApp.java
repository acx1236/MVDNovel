package com.ancx.mvdnovel;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ancx on 2016/4/14.
 */
public class NovelApp extends Application {

    private static Context instance;

    public static Context getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }

}
