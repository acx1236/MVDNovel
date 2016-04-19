package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.Ranking;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface OnRankingListener {

    /**
     * @param males   男性
     * @param females 女性
     */
    void setRanking(List<Ranking> males, List<Ranking> females);

    void onFailed();
}
