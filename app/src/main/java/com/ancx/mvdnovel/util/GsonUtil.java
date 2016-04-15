package com.ancx.mvdnovel.util;

import com.google.gson.Gson;

/**
 * Created by Ancx on 2016/4/15.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    public static <T> T getObject(String response, Class<T> className) {
        T fromJson = null;
        try {
            fromJson = gson.fromJson(response, className);
        } catch (Exception e) {
            MsgUtil.LogTag("NovelApp -> getObject -> Exception");
            MsgUtil.LogException(e);
        } finally {
            return fromJson;
        }
    }
}
