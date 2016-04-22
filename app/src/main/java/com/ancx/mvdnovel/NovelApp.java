package com.ancx.mvdnovel;

import android.app.Application;
import android.content.Context;

import com.ancx.mvdnovel.util.DbHelper;

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

    public static boolean readBookChanged = false;

    private static DbHelper dbHelper;

    public static DbHelper getDbHelper() {
        if (dbHelper == null)
            dbHelper = new DbHelper(instance, 1);
        return dbHelper;
    }
}
