package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.BookDetail;

/**
 * Created by Ancx on 2016/4/21.
 */
public interface ReadBookView {

    BookDetail getBook();

    void setText(String content);
}
