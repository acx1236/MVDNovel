package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.RankingList;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnRankingListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

/**
 * Created by Ancx on 2016/4/16.
 */
public class ModelRanking {

    public void getRanking() {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/ranking/gender", new HttpRequestListener() {
            @Override
            public void onSuccess(String response) {
                RankingList rankingList = GsonUtil.getObject(response, RankingList.class);
                if (rankingList.isOk() && onRankingListener != null)
                    onRankingListener.setRanking(rankingList.getMale(), rankingList.getFemale());
            }

            @Override
            public void onError(VolleyError error) {
                if (onRankingListener != null)
                    onRankingListener.onFailed();
            }
        });
    }

    private OnRankingListener onRankingListener;

    public void setOnRankingListener(OnRankingListener onRankingListener) {
        this.onRankingListener = onRankingListener;
    }
}
