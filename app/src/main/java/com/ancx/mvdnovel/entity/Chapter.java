package com.ancx.mvdnovel.entity;

/**
 * Created by Ancx on 2016/4/16.
 */
public class Chapter {

    private String title;
    private String link;
    private int currency;
    private boolean unreadble;
    private boolean isVip;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public boolean isUnreadble() {
        return unreadble;
    }

    public void setUnreadble(boolean unreadble) {
        this.unreadble = unreadble;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

}
