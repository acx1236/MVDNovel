package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.Ranking;
import com.ancx.mvdnovel.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface RankingTabView {

    /**
     * 获取Ranging对象
     *
     * @return
     */
    Ranking getRanking();

    /**
     * 设置ViewPager的页面
     *
     * @param fragments
     */
    void setViewPager(List<BaseFragment> fragments);

}
