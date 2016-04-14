package com.ancx.mvdnovel.view;

/**
 * 操作MainActivity的接口
 * Created by Ancx on 2016/4/14.
 */
public interface MainView {

    /**
     * 搜索
     */
    void toSearch();

    /**
     * 退出应用
     */
    void exit();

    /**
     * 显示软件信息
     */
    void showInfo();

    /**
     * 打开榜单
     */
    void showList();

    /**
     * 打开分类
     */
    void showClass();

    /**
     * 显示正在阅读的小说
     */
    void showNovel();
}
