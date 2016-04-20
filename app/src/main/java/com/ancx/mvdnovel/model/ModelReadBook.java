package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.BookBody;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnReadBookListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.android.volley.VolleyError;

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
}
