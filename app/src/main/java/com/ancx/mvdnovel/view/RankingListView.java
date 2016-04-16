package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.Ranking;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface RankingListView {

    /**
     * 获取Ranging对象
     *
     * @return
     */
    Ranking getRanking();

    /**
     * 显示ViewPager
     */
    void setViewPager();

}
