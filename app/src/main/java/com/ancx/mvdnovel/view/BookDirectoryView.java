package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.adapter.BookDirectoryAdapter;
import com.ancx.mvdnovel.entity.Chapter;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface BookDirectoryView {

    /**
     * 获取小说名称
     */
    String getId();

    void errorNet();

    void noData();

    boolean isEnd();

    void loadComplete();

    void setSelection(int selection);

    void setAdapter(BookDirectoryAdapter bookDirectoryAdapter);
}
