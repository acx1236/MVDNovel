package com.ancx.mvdnovel.entity;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassList {

    private boolean ok;

    private List<ClassName> male;

    private List<ClassName> female;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<ClassName> getMale() {
        return male;
    }

    public void setMale(List<ClassName> male) {
        this.male = male;
    }

    public List<ClassName> getFemale() {
        return female;
    }

    public void setFemale(List<ClassName> female) {
        this.female = female;
    }

}
