package com.ancx.mvdnovel.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.ChapterBody;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.listener.OnReadBookListener;
import com.ancx.mvdnovel.model.ModelBookDirectory;
import com.ancx.mvdnovel.model.ModelReadBook;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.MsgUtil;

import java.util.List;

public class CacheBookService extends Service implements OnBookDirectoryListener, OnReadBookListener {

    private SharedPreferences sharedPreferences;
    private ModelReadBook modelReadBook = new ModelReadBook();

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String _id;
    private boolean isCacheing;
    private int numOfCached;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        _id = intent.getStringExtra("_id");
        if (_id == null) {
            MsgUtil.ToastShort("获取小说资源失败，重来一遍试试");
            return super.onStartCommand(intent, flags, startId);
        }
        // 图书Id正确的传递过来,其他操作
        if (isCacheing) {
            // 正在缓存
            MsgUtil.ToastShort("目前有小说正在缓存，请一会再来缓存这本小说");
            return super.onStartCommand(intent, flags, startId);
        }
        numOfCached = 0;
        // 创建小说文件夹
        boolean modelReadBookDir = modelReadBook.createDir(_id);
        if (!modelReadBookDir) {
            MsgUtil.ToastShort("没有检测到SD卡，无法使用缓存功能!");
            return super.onStartCommand(intent, flags, startId);
        }
        MsgUtil.ToastShort("开始缓存");
        isCacheing = true;
        // 获取小说目录
        getBookDir();
        return super.onStartCommand(intent, flags, startId);
    }

    private void continueCache() {
        numOfCached++;
        cache();
    }

    private void cacheSuccess() {
        isCacheing = false;
        MsgUtil.ToastShort("缓存成功!");
    }

    private ModelBookDirectory modelBookDirectory = new ModelBookDirectory();

    private void getBookDir() {
        modelBookDirectory.setOnBookDirectoryListener(this);
        modelBookDirectory.getSource(_id);
    }

    @Override
    public void noData() {
        MsgUtil.LogTag("没有目录数据，并没有缓存任何数据");
        cacheSuccess();
    }

    @Override
    public void onFailed() {
        isCacheing = false;
        MsgUtil.ToastShort("连接服务器出错，请检查网络配置或稍后重试!");
    }

    @Override
    public void setSource(String _id, List<Source> sources) {
        // 从网络中获取的源id
        if (sources != null && sources.size() > 0) {
            // 正确的数据，并选择了源id
            // 根据小说的id+sourceId创建SP文件，用来记录哪些章节缓存过
            sharedPreferences = NovelApp.getInstance().getSharedPreferences(_id + sources.get(0).get_id(), Activity.MODE_PRIVATE);
            // 获取目录信息
            modelBookDirectory.getDirectory(_id, sources.get(0).get_id());
            // 替换数据库中的源id信息
            DatabaseManager.updateSourceId(_id, sources.get(0).get_id());
        } else {
            isCacheing = false;
            MsgUtil.ToastShort("目录信息获取失败，请稍后再试!");
        }
    }

    List<Chapter> chapters;

    @Override
    public void setDirectory(List<Chapter> chapters) {
        // 获取到目录信息，但有可能是源id使用的是缓存，所以可能没有创建SP对象，判断一下
        if (sharedPreferences == null) {
            String sourceId = DatabaseManager.getSourceId(_id);
            sharedPreferences = NovelApp.getInstance().getSharedPreferences(_id + sourceId, Activity.MODE_PRIVATE);
        }
        if (chapters == null || chapters.size() == 0) {
            cacheSuccess();
            return;
        }
        this.chapters = chapters;
        modelReadBook.setOnReadBookListener(this);
        cache();
    }

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
        modelReadBook.cacheText(_id, chapters.get(numOfCached).getTitle(), chapterBody.getBody(), sharedPreferences, chapters.get(numOfCached).getLink());
        continueCache();
    }

    @Override
    public void onBodyFailed() {
        MsgUtil.LogTag("第" + (numOfCached + 1) + "章  缓存失败！");
        continueCache();
    }
}