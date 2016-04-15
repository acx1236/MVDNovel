package com.ancx.mvdnovel.view;

import java.util.List;

/**
 * Created by Ancx on 2016/4/14.
 */
public interface SearchView {

    /**
     * 清空EditText
     */
    void clearEditText();

    /**
     * 获取EditText的文本
     *
     * @return
     */
    String getText();

    /**
     * 设置搜索历史列表
     */
    void setHistory(List<String> historys);

    /**
     * 设置关键字列表
     */
    void setKeyWord(List<String> keyWords);

    /**
     * 显示关键字列表的View
     */
    void showKeyWordView();

    /**
     * 隐藏关键字列表的View
     */
    void hideKeyWordsView();

    /**
     * 显示 × View
     */
    void showChaView();

    /**
     * 隐藏 × View
     */
    void hideChaView();
}
