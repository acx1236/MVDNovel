package com.ancx.mvdnovel.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.TextUtils;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.ChapterBody;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.listener.OnReadBookListener;
import com.ancx.mvdnovel.model.ModelBookDirectory;
import com.ancx.mvdnovel.model.ModelReadBook;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.MemoryUtil;
import com.ancx.mvdnovel.util.MsgUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class CacheBookService extends Service implements OnBookDirectoryListener, OnReadBookListener {

    private BookDetail book;
    private boolean isCacheing;
    private DatabaseManager databaseManager = new DatabaseManager();

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private File gidFile;
    private SharedPreferences sharedPreferences;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (isCacheing) {
            // 正在缓存
            MsgUtil.ToastShort("目前有小说正在缓存，请一会再来缓存这本小说");
            return super.onStartCommand(intent, flags, startId);
        }
        numOfCached = 0;
        book = (BookDetail) intent.getSerializableExtra("book");
        if (book == null) {
            MsgUtil.ToastShort("获取小说资源失败，重来一遍试试");
            return super.onStartCommand(intent, flags, startId);
        }
        // 创建小说文件夹
        String saveNovelPath = MemoryUtil.getSaveNovelPath(book.get_id(), null);
        if (saveNovelPath == null) {
            MsgUtil.ToastShort("没有检测到SD卡，无法使用缓存功能!");
            return super.onStartCommand(intent, flags, startId);
        }
        gidFile = new File(saveNovelPath);
        if (!gidFile.exists()) {
            gidFile.mkdirs();
        }
        sharedPreferences = NovelApp.getInstance().getSharedPreferences(book.get_id(), Activity.MODE_PRIVATE);
        MsgUtil.ToastShort("开始缓存");
        isCacheing = true;
        if (!NovelApp.bookIds.contains(book.get_id()))
            databaseManager.addBook(book);
        // 获取小说目录
        getBookDir();
        return super.onStartCommand(intent, flags, startId);
    }

    private ModelBookDirectory modelBookDirectory = new ModelBookDirectory();

    private void getBookDir() {
        modelBookDirectory.setOnBookDirectoryListener(this);
        String sourceId = databaseManager.getSourceId(book.get_id());
        if (TextUtils.isEmpty(sourceId))
            // 获取小说源
            modelBookDirectory.getSource(book.get_id());
        else
            modelBookDirectory.getDirectory(sourceId);
    }

    @Override
    public void setSource(List<Source> sources) {
        if (sources != null && sources.size() > 0) {
            modelBookDirectory.getDirectory(sources.get(0).get_id());
            databaseManager.updateSourceId(book.get_id(), sources.get(0).get_id());
        } else {
            isCacheing = false;
            MsgUtil.ToastShort("目录信息获取失败，请稍后再试!");
        }
    }

    private void cacheSuccess() {
        isCacheing = false;
        MsgUtil.ToastShort("缓存成功!");
    }

    List<Chapter> chapters;

    @Override
    public void setDirectory(List<Chapter> chapters) {
        if (chapters == null || chapters.size() == 0) {
            cacheSuccess();
            return;
        }
        this.chapters = chapters;
        modelReadBook.setOnReadBookListener(this);
        cache();
    }

    @Override
    public void onFailed() {
        isCacheing = false;
        MsgUtil.ToastShort("连接服务器出错，请检查网络配置或稍后重试!");
    }

    private void continueCache() {
        numOfCached++;
        cache();
    }

    private ModelReadBook modelReadBook = new ModelReadBook();
    private int numOfCached;

    private void cache() {
        if (numOfCached >= chapters.size()) {
            cacheSuccess();
            return;
        }
        if (sharedPreferences.getBoolean(chapters.get(numOfCached).getLink(), false)) {
            continueCache();
            return;
        }
        modelReadBook.getBookBody(chapters.get(numOfCached).getLink());
    }

    @Override
    public void setBookBody(ChapterBody chapterBody) {
        try {
            File bookFile = new File(MemoryUtil.getSaveNovelPath(book.get_id(), chapters.get(numOfCached).getTitle()));
            if (!bookFile.exists()) {
                bookFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(bookFile);
            fos.write(chapterBody.getBody().getBytes("utf-8"));
            fos.close();
            sharedPreferences.edit().putBoolean(chapters.get(numOfCached).getLink(), true).commit();
        } catch (IOException e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("CacheBookService -> setBookBody -> 创建本地文件异常");
        }
        continueCache();
    }

    @Override
    public void onBodyFailed() {
        continueCache();
        MsgUtil.LogTag("第" + (numOfCached + 1) + "章  缓存失败！");
    }
}
