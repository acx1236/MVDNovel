package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.UpdateBook;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnUpdateBookListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ModelMain {

    public void updateBooks() {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < NovelApp.bookIds.size(); i++) {
            if (i == NovelApp.bookIds.size() - 1) {
                ids.append(NovelApp.bookIds.get(i));
            } else {
                ids.append(NovelApp.bookIds.get(i));
                ids.append(",");
            }
        }
        HttpUtil.addRequest("http://api.zhuishushenqi.com/book?view=updated&id=" + ids,
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        List<UpdateBook> updateBooks = GsonUtil.getArray(response, UpdateBook.class);
                        if (updateBooks != null && onUpdateBookListener != null) {
                            onUpdateBookListener.onUpdata(updateBooks);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    private OnUpdateBookListener onUpdateBookListener;

    public void setOnUpdateBookListener(OnUpdateBookListener onUpdateBookListener) {
        this.onUpdateBookListener = onUpdateBookListener;
    }
}
