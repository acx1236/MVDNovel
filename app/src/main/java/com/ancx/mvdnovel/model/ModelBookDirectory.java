package com.ancx.mvdnovel.model;

import android.text.TextUtils;

import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.DirectoryList;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.ancx.mvdnovel.util.MemoryUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.util.ReaderUtil;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class ModelBookDirectory {

    private OnBookDirectoryListener onBookDirectoryListener;

    public void setOnBookDirectoryListener(OnBookDirectoryListener onBookDirectoryListener) {
        this.onBookDirectoryListener = onBookDirectoryListener;
    }

    /**
     * 获取小说源
     *
     * @param _id 图书Id
     */
    public void getSource(String _id) {
        // 从数据库中查询看看有没有记录已经保存的小说源
        String sourceId = DatabaseManager.getSourceId(_id);
        if (TextUtils.isEmpty(sourceId)) {
            // 数据库中没有保存，在服务器中获取
            getNetSource(_id);
        } else {
            // 数据库中保存了，直接获取小说目录信息
            getDirectory(_id, sourceId);
        }
    }

    /**
     * 从网络中获取小说源
     *
     * @param _id 图书Id
     */
    private void getNetSource(final String _id) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/toc?view=summary&book=" + _id,
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        List<Source> sources = GsonUtil.getArray(response, Source.class);
                        if (onBookDirectoryListener != null)
                            // 获取到源，根据源集合选择一个源进行获取目录
                            onBookDirectoryListener.setSource(_id, sources);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if (onBookDirectoryListener != null)
                            onBookDirectoryListener.onFailed();
                    }
                });
    }

    /**
     * 获取目录信息，先从缓存文件中找，找不到再从网络中找
     *
     * @param _id      小说id
     * @param sourceId 小说源id
     */
    public void getDirectory(String _id, String sourceId) {
        // 获取SD卡中缓存目录信息的路径（app cache目录下 \novel\小说id\小说源id.text ）
        String chapterPath = MemoryUtil.getSaveNovelPath(_id, sourceId);
        if (chapterPath == null) {
            // 获取路径失败，可能没有SD卡，从网络中获取目录信息
            getNetDirectory(_id, sourceId);
            return;
        }
        // 有SD卡,看看有没有目录缓存
        File chapterFile = new File(chapterPath);
        if (chapterFile.exists()) {
            // 缓存过目录信息
            try {
                // 根据路径获取缓存数据
                String text = ReaderUtil.getString(new FileInputStream(chapterFile));
                // 根据数据解析成类
                DirectoryList directoryList = GsonUtil.getObject(text, DirectoryList.class);
                List<Chapter> chapters = directoryList.getChapters();
                if (onBookDirectoryListener != null) {
                    if (chapters == null)
                        // 缓存的目录信息中没有章节的集合
                        onBookDirectoryListener.noData();
                    else
                        // 含有有效的数据
                        onBookDirectoryListener.setDirectory(chapters);
                }
                return;
            } catch (FileNotFoundException e) {
                MsgUtil.LogException(e);
                MsgUtil.LogTag("ModelBookDirectory -> getSdCardDirectory -> FileNotFoundException");
            }
        }
        // 解析失败或者文件没有发现
        getNetDirectory(_id, sourceId);
    }

    /**
     * 从网络中获取小说目录信息
     *
     * @param _id      小说Id
     * @param sourceId 小说源Id
     */
    private void getNetDirectory(final String _id, final String sourceId) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/toc/" + sourceId + "?view=chapters",
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        DirectoryList directoryList = GsonUtil.getObject(response, DirectoryList.class);
                        if (directoryList != null) {
                            List<Chapter> chapters = directoryList.getChapters();
                            if (onBookDirectoryListener != null) {
                                if (chapters == null)
                                    // 没有目录数据，显示没有数据页面，点击可以重新获取
                                    onBookDirectoryListener.noData();
                                else {
                                    // 有目录数据,缓存到SD卡中
                                    cacheDir(_id, sourceId, response);
                                    // 设置目录信息集合
                                    onBookDirectoryListener.setDirectory(chapters);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if (onBookDirectoryListener != null)
                            onBookDirectoryListener.onFailed();
                    }
                });
    }

    /**
     * 获取到正确的目录数据后，把数据缓存到SD卡中
     *
     * @param _id      小说Id
     * @param sourceId 小说源Id
     * @param dirText  获取的目录数据
     */
    private void cacheDir(String _id, String sourceId, String dirText) {
        String dirPath = MemoryUtil.getSaveNovelPath(_id, sourceId);
        if (dirPath == null)
            // 没有SD卡
            return;
        // 有SD卡
        try {
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                dirFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(dirFile);
            fos.write(dirText.getBytes("utf-8"));
            fos.close();
        } catch (IOException e) {
            // 可能没有上一级目录，就是这个小说没有被添加到书库，所以不用继续缓存,无需任何操作
            MsgUtil.LogException(e);
            MsgUtil.LogTag("ModelBookDirectory -> cacheDir -> IOException");
        }
    }

}