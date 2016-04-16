package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.Ranking;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface RankingView {

    /**
     * 设置排行榜列表的数据
     *
     * @param mData  所有数据
     * @param male   男性数据个数
     * @param female 女性数据个数
     */
    void setRanking(List<Ranking> mData, int male, int female);
}
