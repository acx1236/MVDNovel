package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.BookDetail;

import java.util.List;

/**
 * 操作MainActivity的接口
 * Created by Ancx on 2016/4/14.
 */
public interface MainView {

    /**
     * 显示正在阅读的小说
     */
    void showBook(List<BookDetail> books);

    void updateComplete();
}
