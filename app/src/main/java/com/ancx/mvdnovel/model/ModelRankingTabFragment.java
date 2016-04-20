package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.RankingBookList;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnRankingBookListListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ModelRankingTabFragment {

    public void getContent(String _id) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/ranking/" + _id,
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        RankingBookList rankingBookList = GsonUtil.getObject(response, RankingBookList.class);
                        if (rankingBookList.isOk() && onRankingBookListListener != null)
                            onRankingBookListListener.onRankingBookList(rankingBookList.getRanking().getBooks());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        if (onRankingBookListListener != null)
                            onRankingBookListListener.onFailed();
                    }
                });
    }

    private OnRankingBookListListener onRankingBookListListener;

    public void setOnRankingBookListListener(OnRankingBookListListener onRankingBookListListener) {
        this.onRankingBookListListener = onRankingBookListListener;
    }
}
