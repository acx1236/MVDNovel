package com.ancx.mvdnovel.util;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Ancx on 16/2/25.
 */
public class ImageLoader {

    private static Handler handler = new Handler();
    private final static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static String getKey(String imagePath) {
        return MD5Util.GetMD5Code(imagePath);
    }

    // 显示图片
    public static void display(final String imagePath, final ImageView mImageView, int defaultResource, final int errorResource, final int maxWidth, final int maxHeight) {
        if (MemoryUtil.getMemory().get(getKey(imagePath)) != null) {
            mImageView.setImageBitmap(MemoryUtil.getMemory().get(getKey(imagePath)));
        } else {
            // 从内存中找图片
            // 显示默认图片
            mImageView.setTag(imagePath);
            mImageView.setImageResource(defaultResource);
            // sd卡中查找
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = BitmapUtil.readBitMap(MemoryUtil.getSaveBitmapPath(getKey(imagePath)), maxWidth, maxHeight);
                    if (bitmap != null) {
                        if (imagePath.equals(mImageView.getTag())) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mImageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                        MemoryUtil.getMemory().put(getKey(imagePath), bitmap);
                    } else {
                        // 从网络上下载
                        if (imagePath.equals(mImageView.getTag())) {
                            downloadBitMap(imagePath, mImageView, errorResource, maxWidth, maxHeight);
                        }
                    }
                }
            });
        }
    }

    private static void downloadBitMap(final String imagePath, final ImageView mImageView, final int errorResource, int maxWidth, int maxHeight) {
        ImageRequest imageRequest = new ImageRequest(imagePath, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(final Bitmap bitmap) {
                if (imagePath.equals(mImageView.getTag())) {
                    mImageView.setImageBitmap(bitmap);
                }
                MemoryUtil.getMemory().put(getKey(imagePath), bitmap);
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        BitmapUtil.saveBitmapToFile(bitmap, MemoryUtil.getSaveBitmapPath(getKey(imagePath)));
                    }
                });
            }
        }, maxWidth, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mImageView.setImageResource(errorResource);
            }
        });
        HttpUtil.getQueue().add(imageRequest);
    }

}