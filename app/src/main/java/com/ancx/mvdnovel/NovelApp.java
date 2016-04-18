package com.ancx.mvdnovel;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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

    public static List<String> bookIds = new ArrayList<>();
}
