package com.ancx.mvdnovel.util;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ancx.mvdnovel.BuildConfig;
import com.ancx.mvdnovel.NovelApp;

public class MsgUtil {

    private static TextView tv_text;

    /**
     * 短吐司
     *
     * @param text
     */
    public static void ToastShort(String text) {
        Toast.makeText(NovelApp.getInstance(), text + "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 长吐司
     *
     * @param text
     */
    public static void ToastLong(String text) {
        Toast.makeText(NovelApp.getInstance(), text + "", Toast.LENGTH_LONG).show();
    }

    /**
     * 错误，请重试
     */
    public static void ToastError() {
        Toast.makeText(NovelApp.getInstance(), "错误，请重试", Toast.LENGTH_SHORT).show();
    }

    /**
     * Log打印TAG
     *
     * @param text
     */
    public static void LogTag(String text) {
        if (BuildConfig.LOG_DEBUG)
            Log.e("TAG", text + "");
    }

    /**
     * Log打印Exception
     *
     * @param e
     */
    public static void LogException(Exception e) {
        if (BuildConfig.LOG_DEBUG)
            Log.e("Exception", e.getMessage() + "");
    }

    public static void LogThrowable(Throwable t) {
        if (BuildConfig.LOG_DEBUG)
            if (t != null) {
                Log.e("Throwable", t.getMessage() + "");
            } else {
                Log.e("Throwable", "Throwable 为 null （空指针）");
            }
    }

}
