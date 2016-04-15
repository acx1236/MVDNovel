package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.Books;

import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public interface OnSearchBooksListener {

    void setBooks(List<Books> mData);
}
