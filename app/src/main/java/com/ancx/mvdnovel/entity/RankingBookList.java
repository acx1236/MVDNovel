package com.ancx.mvdnovel.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class RankingBookList {

    private RankingBean ranking;

    private boolean ok;

    public RankingBean getRanking() {
        return ranking;
    }

    public void setRanking(RankingBean ranking) {
        this.ranking = ranking;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public static class RankingBean {
        private String _id;
        private String updated;
        private String title;
        private String tag;
        private String cover;
        private int __v;
        private String monthRank;
        private String totalRank;
        private boolean isSub;
        private boolean collapse;
        @SerializedName("new")
        private boolean newX;
        private String gender;
        private int priority;
        private String created;
        private String id;
        private List<RankingBook> books;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getMonthRank() {
            return monthRank;
        }

        public void setMonthRank(String monthRank) {
            this.monthRank = monthRank;
        }

        public String getTotalRank() {
            return totalRank;
        }

        public void setTotalRank(String totalRank) {
            this.totalRank = totalRank;
        }

        public boolean isIsSub() {
            return isSub;
        }

        public void setIsSub(boolean isSub) {
            this.isSub = isSub;
        }

        public boolean isCollapse() {
            return collapse;
        }

        public void setCollapse(boolean collapse) {
            this.collapse = collapse;
        }

        public boolean isNewX() {
            return newX;
        }

        public void setNewX(boolean newX) {
            this.newX = newX;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<RankingBook> getBooks() {
            return books;
        }

        public void setBooks(List<RankingBook> books) {
            this.books = books;
        }

    }
}
