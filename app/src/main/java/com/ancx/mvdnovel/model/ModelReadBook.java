package com.ancx.mvdnovel.model;

import android.content.SharedPreferences;

import com.ancx.mvdnovel.entity.BookBody;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnReadBookListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.ancx.mvdnovel.util.MemoryUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.android.volley.VolleyError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Ancx on 2016/4/20.
 */
public class ModelReadBook {

    public void getBookBody(String link) {
        try {
            HttpUtil.addRequest("http://chapter2.zhuishushenqi.com/chapter/" + URLEncoder.encode(link, "utf-8"),
                    new HttpRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            BookBody bookBody = GsonUtil.getObject(response, BookBody.class);
                            if (bookBody.isOk() && onReadBookListener != null)
                                onReadBookListener.setBookBody(bookBody.getChapter());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            if (onReadBookListener != null)
                                onReadBookListener.onBodyFailed();
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("ModelReadBook -> getBookBody -> URL编码异常");
        }
    }

    private OnReadBookListener onReadBookListener;

    public void setOnReadBookListener(OnReadBookListener onReadBookListener) {
        this.onReadBookListener = onReadBookListener;
    }

    public void cacheText(String _id, String title, String text, SharedPreferences sharedPreferences, String link) {
        try {
            File bookFile = new File(MemoryUtil.getSaveNovelPath(_id, title));
            if (!bookFile.exists()) {
                bookFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(bookFile);
            fos.write(text.getBytes("utf-8"));
            fos.close();
            sharedPreferences.edit().putBoolean(link, true).commit();
        } catch (IOException e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("ModelReadBook -> cacheText -> IOException");
        }
    }

    /**
     * 创建图书的文件夹
     *
     * @return
     */
    public boolean createDir(String _id) {
        String saveNovelPath = MemoryUtil.getSaveNovelPath(_id, null);
        if (saveNovelPath == null) {
            return false;
        }
        File gidFile = new File(saveNovelPath);
        if (!gidFile.exists()) {
            gidFile.mkdirs();
        }
        return true;
    }

    public void cacheChacpter(final String _id, final Chapter chapter, final SharedPreferences sharedPreferences) {
        if (sharedPreferences.getBoolean(chapter.getLink(), false))
            return;
        try {
            HttpUtil.addRequest("http://chapter2.zhuishushenqi.com/chapter/" + URLEncoder.encode(chapter.getLink(), "utf-8"),
                    new HttpRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            BookBody bookBody = GsonUtil.getObject(response, BookBody.class);
                            if (bookBody.isOk())
                                cacheText(_id, chapter.getTitle(), bookBody.getChapter().getBody(), sharedPreferences, chapter.getLink());
                        }

                        @Override
                        public void onError(VolleyError error) {
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("ModelReadBook -> cacheChacpter -> URL编码异常");
        }
    }

}
