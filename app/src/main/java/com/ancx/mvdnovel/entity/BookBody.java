package com.ancx.mvdnovel.entity;

/**
 * Created by Ancx on 2016/4/20.
 */
public class BookBody {

    private boolean ok;

    private ChapterBody chapter;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public ChapterBody getChapter() {
        return chapter;
    }

    public void setChapter(ChapterBody chapter) {
        this.chapter = chapter;
    }

}
