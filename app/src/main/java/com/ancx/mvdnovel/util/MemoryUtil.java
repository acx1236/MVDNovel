package com.ancx.mvdnovel.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.ancx.mvdnovel.NovelApp;

import java.io.File;

/**
 * Created by Ancx on 16/2/25.
 */
public class MemoryUtil {

    private final static LruCache<String, Bitmap> memory = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 1024 / 8)) {

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
//            oldValue.recycle();
            oldValue = null;
            MsgUtil.LogTag("hard cache is full , push to soft cache");
        }
    };

    public static LruCache<String, Bitmap> getMemory() {
        return memory;
    }

    /**
     * 清空缓存目录的文件
     *
     * @param directory
     */
    public static void cleanExternalStorageDirectoryCache(File directory) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (directory != null && directory.exists()) {
                if (directory.isFile()) {
                    directory.delete();
                } else if (directory.isDirectory()) {
                    File files[] = directory.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        cleanExternalStorageDirectoryCache(files[i]);
                    }
                }
                directory.delete();
                if (!NovelApp.getInstance().getExternalCacheDir().exists()) {
                    MsgUtil.ToastShort("清除完毕");
                }
            } else {
                MsgUtil.ToastShort("清除完毕");
            }
        }
    }

    /**
     * 获取SD卡的应用缓存路径
     *
     * @return
     */
    public static String getCacheDir() {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (NovelApp.getInstance().getExternalCacheDir() == null)
                return path;
            path = NovelApp.getInstance().getExternalCacheDir().getPath();
        }
        return path;
    }


    /**
     * 根据图片的picpath，获取在sd卡存储的位置
     *
     * @param picpath
     * @return
     */
    public static String getSaveBitmapPath(String picpath) {
        String path = null;
        if (getCacheDir() != null) {
            path = getCacheDir() + "/.pic/" + picpath + ".png";
        }
        return path;
    }

    public static String getSaveNovelPath(String novelGid, String sequence) {
        String path = null;
        if (getCacheDir() != null) {
            if (sequence == null) {
                path = getCacheDir() + "/novel/" + novelGid;
            } else {
                path = getCacheDir() + "/novel/" + novelGid + "/" + sequence + ".text";
            }
        }
        return path;
    }

}
