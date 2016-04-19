package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.UpdateBook;

import java.util.List;

/**
 * Created by Ancx on 2016/4/19.
 */
public interface OnUpdateBookListener {

    void onUpdata(List<UpdateBook> updateBooks);
}
