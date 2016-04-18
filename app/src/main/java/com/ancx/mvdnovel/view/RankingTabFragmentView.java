package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.RankingBook;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface RankingTabFragmentView {

    /**
     * 获取榜单Id
     *
     * @return
     */
    String getRankId();

    /**
     * 设置榜单图书列表
     *
     * @param books
     */
    void setContent(List<RankingBook> books);
}
