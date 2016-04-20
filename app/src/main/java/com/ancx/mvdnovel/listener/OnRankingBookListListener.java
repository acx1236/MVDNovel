package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.RankingBook;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface OnRankingBookListListener {

    void onRankingBookList(List<RankingBook> books);

    void onFailed();
}
