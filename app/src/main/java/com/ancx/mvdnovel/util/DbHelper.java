package com.ancx.mvdnovel.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库和表的创建
 * Created by Ancx on 16/2/24.
 */
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, int version) {
        super(context, "novel", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 搜索历史表
        db.execSQL("create table history (name string, time string)");
        // 我的书架表
        db.execSQL("create table bookshelf (_id string, sourceId string, title string, cover string, author string, updated string, lastChapter string, readCount int, chaptersCount int, readPage int, openedTime string)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
