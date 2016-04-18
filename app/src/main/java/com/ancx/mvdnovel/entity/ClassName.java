package com.ancx.mvdnovel.entity;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassName {

    private String name;
    private int bookCount;
    private String gender;

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

}
