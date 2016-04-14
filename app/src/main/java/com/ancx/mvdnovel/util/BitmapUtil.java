package com.ancx.mvdnovel.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片工具类
 * Created by Ancx
 */
public class BitmapUtil {

    /**
     * 保存Bitmap为图片
     *
     * @param bitmap
     * @param picPath
     */
    public static void saveBitmapToFile(Bitmap bitmap, String picPath) {
        BufferedOutputStream os = null;
        try {
            File file = new File(picPath);
            int end = picPath.lastIndexOf(File.separator);
            String _filePath = picPath.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
        } catch (Exception e) {
            MsgUtil.LogException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    MsgUtil.LogException(e);
                }
            }
            System.gc();
        }
    }

    public static Bitmap readBitMap(String localPath, int width, int height) {
        if (width == 0 || height == 0)
            return BitmapFactory.decodeFile(localPath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(localPath, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(localPath, options);
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
