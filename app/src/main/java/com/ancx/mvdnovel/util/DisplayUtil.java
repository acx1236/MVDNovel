package com.ancx.mvdnovel.util;

import com.ancx.mvdnovel.NovelApp;

/**
 * Created by Ancx on 2016/4/19.
 */
public class DisplayUtil {

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static float px2dip(float pxValue) {
        final float scale = NovelApp.getInstance().getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @return
     */
    public static float dip2px(float dipValue) {
        final float scale = NovelApp.getInstance().getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static float px2sp(float pxValue) {
        final float fontScale = NovelApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static float sp2px(float spValue) {
        final float fontScale = NovelApp.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }

}
