package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.Books;

import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public interface SearchBooksView {

    /**
     * 显示小说搜索结果
     */
    void setResult(List<Books> mData);

    /**
     * 获取搜索的小说名称
     */
    String getBookName();
}
