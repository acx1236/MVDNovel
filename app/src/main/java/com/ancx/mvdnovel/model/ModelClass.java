package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.ClassList;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnClassListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ModelClass {

    public void getClassList() {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/cats/lv2/statistics",
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        ClassList classList = GsonUtil.getObject(response, ClassList.class);
                        if (classList.isOk() && onClassListener != null)
                            onClassListener.onClassName(classList.getMale(), classList.getFemale());
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    private OnClassListener onClassListener;

    public void setOnClassListener(OnClassListener onClassListener) {
        this.onClassListener = onClassListener;
    }
}
