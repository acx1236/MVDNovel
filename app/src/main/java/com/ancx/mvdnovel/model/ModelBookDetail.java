package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnBookDetailListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

/**
 * Created by Ancx on 2016/4/15.
 */
public class ModelBookDetail {

    public void getDetail(final String _id) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/book/" + _id,
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        BookDetail bookDetail = GsonUtil.getObject(response, BookDetail.class);
                        if (onBookDetailListener != null) {
                            onBookDetailListener.setDetail(bookDetail);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    private OnBookDetailListener onBookDetailListener;

    public void setOnBookDetailListener(OnBookDetailListener onBookDetailListener) {
        this.onBookDetailListener = onBookDetailListener;
    }

}
