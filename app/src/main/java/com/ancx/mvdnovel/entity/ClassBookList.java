package com.ancx.mvdnovel.entity;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassBookList {

    private boolean ok;

    private List<ClassBook> books;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<ClassBook> getBooks() {
        return books;
    }

    public void setBooks(List<ClassBook> books) {
        this.books = books;
    }

}
