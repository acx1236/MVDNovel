package com.ancx.mvdnovel.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ancx on 16/4/24.
 */
public class IOUtil {

    /**
     * 缓存数据到SD卡中
     *
     * @param path 缓存的路径
     * @param text 缓存的内容
     * @throws IOException 没有SD卡，文件创建失败，文件写入失败
     */
    public static void cache(String path, String text) throws IOException {
        if (path == null)
            // 没有SD卡，路径是null
            return;
        File file = new File(path);
        if (!file.exists())
            // 如果文件不存在，则创建
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(text.getBytes("utf-8"));
        fos.close();
    }
}
