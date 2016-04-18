package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.ClassBookList;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnClassBookListListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ModelClassTabFragment {

    public void getBookList(String gender, String type, String major, String start) {
        StringBuilder httpUrl = new StringBuilder("http://api.zhuishushenqi.com/book/by-categories?gender=");
        httpUrl.append(gender);
        httpUrl.append("&type=");
        httpUrl.append(type);
        httpUrl.append("&major=");
        try {
            httpUrl.append(URLEncoder.encode(major, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            httpUrl.append(major);
        }
        httpUrl.append("&minor=&start=");
        httpUrl.append(start);
        httpUrl.append("&limit=50");
        HttpUtil.addRequest(httpUrl.toString(), new HttpRequestListener() {
            @Override
            public void onSuccess(String response) {
                ClassBookList classBookList = GsonUtil.getObject(response, ClassBookList.class);
                if (classBookList.isOk() && onClassBookListListener != null)
                    onClassBookListListener.onClassBookList(classBookList.getBooks());
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

    private OnClassBookListListener onClassBookListListener;

    public void setOnClassBookListListener(OnClassBookListListener onClassBookListListener) {
        this.onClassBookListListener = onClassBookListListener;
    }
}
