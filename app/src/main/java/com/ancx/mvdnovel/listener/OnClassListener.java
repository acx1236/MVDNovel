package com.ancx.mvdnovel.listener;

import com.ancx.mvdnovel.entity.ClassName;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public interface OnClassListener {

    /**
     * @param male   男性
     * @param female 女性
     */
    void onClassName(List<ClassName> male, List<ClassName> female);
}
