package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.ClassBook;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface OnClassBookListListener {

    void onClassBookList(List<ClassBook> books);

    void onFailed();
}
