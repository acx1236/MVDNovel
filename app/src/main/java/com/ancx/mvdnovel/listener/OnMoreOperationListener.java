package com.ancx.mvdnovel.listener;

/**
 * Created by Ancx on 2016/4/19.
 */
public interface OnMoreOperationListener {

    void onRemoveBook(String _id, int position);

    void onCacheBook(String _id, int position);

    void onBookDirectory(String sourceId, int position);

}
