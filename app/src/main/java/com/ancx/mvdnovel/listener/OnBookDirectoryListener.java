package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.Source;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public interface OnBookDirectoryListener {

    void noData();

    void setSource(String _id, List<Source> sources);

    void setDirectory(List<Chapter> chapters);

    void onFailed();

    void updateSource(List<Source> sources);

}
