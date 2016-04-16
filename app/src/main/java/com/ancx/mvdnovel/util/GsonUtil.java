package com.ancx.mvdnovel.util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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

    public static <T> List<T> getArray(String response, Class<T> className) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(getObject(jsonArray.getString(i), className));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
