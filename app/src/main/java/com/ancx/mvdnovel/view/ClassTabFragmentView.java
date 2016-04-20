package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.ClassBook;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface ClassTabFragmentView {

    String getStart();

    String getGender();

    String getMajor();

    String getType();

    List<ClassBook> getData();

    void setBookList(int total);

    void setAddCount(int addCount);

    void errorNet();
}
