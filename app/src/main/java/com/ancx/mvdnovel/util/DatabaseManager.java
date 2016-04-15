package com.ancx.mvdnovel.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ancx.mvdnovel.NovelApp;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的操作类
 * Created by Ancx on 16/2/24.
 */
public class DatabaseManager {

    private DbHelper dbHelper;

    public DatabaseManager() {
        dbHelper = new DbHelper(NovelApp.getInstance(), 1);
    }

    /**
     * 获取搜索历史数据
     *
     * @return
     */
    public List<String> getHistory() {
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
        Cursor cursor;
        List<String> list = new ArrayList<>();
        cursor = dbRead.rawQuery("select * from history order by time desc", null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        dbRead.close();
        return list;
    }

    /**
     * 向搜索历史表中添加数据
     *
     * @param name
     */
    public void addHistory(String name) {
        delHistory(name);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("insert into history values(?, ?)", new Object[]{name, System.currentTimeMillis()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    /**
     * 在搜索历史表中删除数据
     *
     * @param name 删除的关键字，为null时全部删除
     */
    public void delHistory(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            if (name == null) {
                db.execSQL("delete from history", new Object[]{});
            } else {
                db.execSQL("delete from history where name = ?", new Object[]{name});
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        db.close();
    }

}
