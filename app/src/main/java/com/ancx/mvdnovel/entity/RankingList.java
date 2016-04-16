package com.ancx.mvdnovel.entity;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class RankingList {

    private boolean ok;

    private List<Ranking> male;

    private List<Ranking> female;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<Ranking> getMale() {
        return male;
    }

    public void setMale(List<Ranking> male) {
        this.male = male;
    }

    public List<Ranking> getFemale() {
        return female;
    }

    public void setFemale(List<Ranking> female) {
        this.female = female;
    }

}
