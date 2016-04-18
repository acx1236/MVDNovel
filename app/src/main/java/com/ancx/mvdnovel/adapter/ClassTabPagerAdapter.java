package com.ancx.mvdnovel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ancx.mvdnovel.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassTabPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public ClassTabPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
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
                pageTitle = "热门";
                break;
            case 1:
                pageTitle = "新书";
                break;
            case 2:
                pageTitle = "好评";
                break;
            case 3:
                pageTitle = "完结";
                break;
        }
        return pageTitle;
    }
}
