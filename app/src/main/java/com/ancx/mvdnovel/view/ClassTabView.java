package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface ClassTabView {

    /**
     * 获取性别
     *
     * @return
     */
    String getGender();

    /**
     * 获取大类
     *
     * @return
     */
    String getMajor();

    /**
     * 设置ViewPager的页面
     *
     * @param fragments
     */
    void setViewPager(List<BaseFragment> fragments);
}
