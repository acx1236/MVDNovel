package com.ancx.mvdnovel.view;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface SearchView {

    /**
     * 返回按钮
     */
    void back();

    /**
     * 清空EditText
     */
    void clearEditText();

    /**
     * 搜索
     */
    void search();

    /**
     * 获取EditText的文本
     *
     * @return
     */
    String getText();

    /**
     * 清空搜索历史
     */
    void clearHistory();

    /**
     * 显示搜索历史列表
     */
    void showHisTory();

    /**
     * 显示关键字列表
     */
    void showKeyWord();

    /**
     * 显示关键字列表的View
     */
    void showKeyWordView();
}
