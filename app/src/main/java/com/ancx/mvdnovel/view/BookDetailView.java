package com.ancx.mvdnovel.view;

import com.ancx.mvdnovel.entity.BookDetail;

/**
 * Created by Ancx on 2016/4/15.
 */
public interface BookDetailView {

    /**
     * 获取图书id
     *
     * @return
     */
    String getId();

    /**
     * 显示图片
     *
     * @param imgPath 图片地址
     */
    void showCover(String imgPath);

    /**
     * 显示小说名称
     *
     * @param title
     */
    void showTitle(String title);

    /**
     * 显示状态和类型
     *
     * @param cat_Serial
     */
    void showCat_Serial(String cat_Serial);

    /**
     * 显示作者
     *
     * @param author
     */
    void showAuthor(String author);

    /**
     * 显示更新时间
     *
     * @param update
     */
    void showUpdate(String update);

    /**
     * 显示最后一章
     *
     * @param lastChapter
     */
    void showLastChapter(String lastChapter);

    /**
     * 显示简介
     *
     * @param longIntro
     */
    void showLongIntro(String longIntro);

    /**
     * 设置添加还是移除
     *
     * @param text
     */
    void setAddText(String text);

    void errorNet();

    void setBook(BookDetail book);
}
