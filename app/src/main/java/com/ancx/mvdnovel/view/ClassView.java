package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.ClassName;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface ClassView {

    /**
     * 设置分类数据
     *
     * @param male
     * @param female
     */
    void setClass(List<ClassName> male, List<ClassName> female);

    void errorNet();
}
