package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.ChapterBody;

/**
 * Created by Ancx on 2016/4/20.
 */
public interface OnReadBookListener {

    void setBookBody(ChapterBody chapterBody);

    void onBodyFailed();
}
