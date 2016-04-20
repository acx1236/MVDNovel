package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.BookDetail;

/**
 * Created by Ancx on 2016/4/19.
 */
public interface OnMoreOperationListener {

    void onRemoveBook(String _id, int position);

    void onCacheBook(BookDetail book);

    void onBookDirectory(String _id, String title, int position);

}
