package com.ancx.mvdnovel.view;

/**
 * Created by Ancx on 2016/4/21.
 */
public interface ReadBookView {

    String getId();

    void setText(String content);

    void setHint(String title, int currentChapter, String chaptersCount);

    void errorNet();

    void noData();

    void showLoading();

    void loadComplete();

    void setReadPage(int readPage);

    void readComplete();
}
