package com.ancx.mvdnovel.entity;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassBook {

    private String _id;
    private String author;
    private String cover;
    private String shortIntro;
    private String site;
    private String title;
    private String majorCate;
    private String latelyFollower;
    private String latelyFollowerBase;
    private String minRetentionRatio;
    private String retentionRatio;
    private String lastChapter;
    private List<String> tags;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover() {
        if (cover.length() > 7)
            return cover.substring(7);
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getShortIntro() {
        return shortIntro;
    }

    public void setShortIntro(String shortIntro) {
        this.shortIntro = shortIntro;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMajorCate() {
        return majorCate;
    }

    public void setMajorCate(String majorCate) {
        this.majorCate = majorCate;
    }

    public String getLatelyFollower() {
        return latelyFollower;
    }

    public void setLatelyFollower(String latelyFollower) {
        this.latelyFollower = latelyFollower;
    }

    public String getLatelyFollowerBase() {
        return latelyFollowerBase;
    }

    public void setLatelyFollowerBase(String latelyFollowerBase) {
        this.latelyFollowerBase = latelyFollowerBase;
    }

    public String getMinRetentionRatio() {
        return minRetentionRatio;
    }

    public void setMinRetentionRatio(String minRetentionRatio) {
        this.minRetentionRatio = minRetentionRatio;
    }

    public String getRetentionRatio() {
        return retentionRatio;
    }

    public void setRetentionRatio(String retentionRatio) {
        this.retentionRatio = retentionRatio;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
