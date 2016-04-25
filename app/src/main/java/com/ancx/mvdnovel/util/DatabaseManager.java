package com.ancx.mvdnovel.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.BookDetail;

import java.io.File;
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
            db.execSQL("insert into bookshelf values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    new Object[]{
                            book.get_id(), "", book.getTitle(), book.getUntreatedCover(), book.getAuthor(),
                            book.getUntreatedUpdated(), book.getLastChapter(), book.getChaptersCount(), 1, 1, System.currentTimeMillis()});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            db.endTransaction();
        }
        db.close();
        NovelApp.bookIds.add(book.get_id());
        NovelApp.readBookChanged = true;
        // 添加图书成功后，直接在SD卡内根据id创建文件夹
        String saveNovelPath = MemoryUtil.getSaveNovelPath(book.get_id(), null);
        if (saveNovelPath != null) {
            // 如果SD卡存在，并返回了文件夹路径
            File gidFile = new File(saveNovelPath);
            if (!gidFile.exists()) {
                // 如果是第一次添加图书，那么创建文件夹
                gidFile.mkdirs();
            }
        }
        return 1;
    }

    /**
     * 根据图书id删除
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
            book.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            book.setCover(cursor.getString(cursor.getColumnIndex("cover")));
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

    /**
     * 根据图书Id获取资源id
     *
     * @param _id
     * @return
     */
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

    /**
     * 换源
     *
     * @param _id      图书id
     * @param sourceId 小说源id
     * @return 1 修改成功; 0 修改失败
     */
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

    /**
     * 阅读发生变化
     *
     * @param _id       图书id
     * @param readCount 阅读到的章节
     * @param readPage  阅读章节的页数
     * @return 1 修改成功; 0 修改失败
     */
    public static int updateRead(String _id, String readCount, String readPage) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update bookshelf set readCount=?, readPage=?, openedTime=? where _id=?",
                    new Object[]{readCount, readPage, System.currentTimeMillis(), _id});
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
     * 小说有更新
     *
     * @param _id           图书id
     * @param updated       更新时间
     * @param chaptersCount 总章节数
     * @param lastChapter   最后章节名称
     * @return 1 修改成功; 0 修改失败
     */
    public static int updateBook(String _id, String updated, int chaptersCount, String lastChapter) {
        SQLiteDatabase db = NovelApp.getDbHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("update bookshelf set updated=?, chaptersCount=?, lastChapter=?, openedTime=? where _id=?",
                    new Object[]{updated, chaptersCount, lastChapter, System.currentTimeMillis(), _id});
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