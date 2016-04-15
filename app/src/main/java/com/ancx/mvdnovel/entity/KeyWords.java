package com.ancx.mvdnovel.entity;

import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public class KeyWords {

    private boolean ok;
    private List<String> keywords;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
