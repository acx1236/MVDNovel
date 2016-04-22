package com.ancx.mvdnovel.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.BookDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的操作类
 * Created by Ancx on 16/2/24.
 */
public class DatabaseManager {

    /**
     * 获取搜索历史数据
     *
     * @return
     */
    public static List<String> getHistory() {
        SQLiteDatabase dbRead = NovelApp.getDbHelper().getReadableDatabase();
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
    public static void addHistory(String name) {
        delHistory(name);
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
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
    public static void delHistory(String name) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
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

    /**
     * 添加图书
     *
     * @param book
     * @return 1添加成功；0添加失败
     */
    public static int addBook(BookDetail book) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            //  _id				    图书id
            // title				图书名称
            // cover				图片地址
            // author			    图书作者
            //  updated			    更新时间
            // lastChapter		    最后一章
            //  readCount			已读章节数
            // chaptersCount		总章节数
            // readPage             阅读页数
            // openedTime		    最后一次打开的时间(排序标准)
            db.execSQL("insert into bookshelf values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{book.get_id(), "", book.getTitle(), book.getCover(), book.getAuthor(), book.getUpdated(),
                            book.getLastChapter(), 1, book.getChaptersCount(), 1, System.currentTimeMillis()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        NovelApp.bookIds.add(book.get_id());
        NovelApp.readBookChanged = true;
        return 1;
    }

    /**
     * 根据图书id删除记录
     *
     * @param _id
     * @return 1删除成功；0删除失败
     */
    public static int deleteBook(String _id) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("delete from bookshelf where _id = ?", new Object[]{_id});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            MsgUtil.LogException(e);
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        NovelApp.bookIds.remove(_id);
        NovelApp.readBookChanged = true;
        return 1;
    }

    /**
     * 更新图书
     *
     * @param book
     * @return 1更新成功；0更新失败
     */
    public static int updateBook(BookDetail book) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update bookshelf set sourceId=?, updated=?, lastChapter=?, readCount=?, chaptersCount=?, readPage=?, openedTime=? where _id=?",
                    new Object[]{book.getSourceId(), book.getUpdated(), book.getLastChapter(), book.getReadCount(), book.getChaptersCount(), book.getReadPage(), System.currentTimeMillis(), book.get_id()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        return 1;
    }


    /**
     * 获取所有添加图书
     *
     * @return
     */
    public static List<BookDetail> getBooks() {
        NovelApp.bookIds.clear();
        SQLiteDatabase dbRead = NovelApp.getDbHelper().getReadableDatabase();
        Cursor cursor;
        BookDetail book;
        List<BookDetail> list = new ArrayList<>();
        cursor = dbRead.rawQuery("select * from bookshelf order by openedTime desc", null);
        while (cursor.moveToNext()) {
            book = new BookDetail();
            String _id = cursor.getString(cursor.getColumnIndex("_id"));
            book.set_id(_id);
            NovelApp.bookIds.add(_id);
            book.setSourceId(cursor.getString(cursor.getColumnIndex("sourceId")));
            book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            book.setCover("/agent/" + cursor.getString(cursor.getColumnIndex("cover")));
            book.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            book.setUpdated(cursor.getString(cursor.getColumnIndex("updated")));
            book.setLastChapter(cursor.getString(cursor.getColumnIndex("lastChapter")));
            book.setReadCount(cursor.getInt(cursor.getColumnIndex("readCount")));
            book.setChaptersCount(cursor.getInt(cursor.getColumnIndex("chaptersCount")));
            book.setReadPage(cursor.getInt(cursor.getColumnIndex("readPage")));
            list.add(book);
        }
        cursor.close();
        dbRead.close();
        NovelApp.readBookChanged = false;
        return list;
    }

    public static String getSourceId(String _id) {
        SQLiteDatabase db = NovelApp.getDbHelper().getReadableDatabase();
        Cursor cursor = db.rawQuery("select sourceId from bookshelf where _id = ?", new String[]{_id});
        cursor.moveToNext();
        String sourceId = null;
        try {
            sourceId = cursor.getString(cursor.getColumnIndex("sourceId"));
        } catch (Exception e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("DatabaseManager -> getSourceId -> 异常");
        }
        return sourceId;
    }

    public static int updateSourceId(String _id, String sourceId) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update bookshelf set sourceId=? where _id=?", new Object[]{sourceId, _id});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        return 1;
    }
}