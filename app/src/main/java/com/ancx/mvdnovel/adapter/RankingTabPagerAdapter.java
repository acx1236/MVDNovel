package com.ancx.mvdnovel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ancx.mvdnovel.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class RankingTabPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public RankingTabPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch (position) {
            case 0:
                pageTitle = "周榜";
                break;
            case 1:
                pageTitle = "月榜";
                break;
            case 2:
                pageTitle = "总榜";
                break;
        }
        return pageTitle;
    }
}
