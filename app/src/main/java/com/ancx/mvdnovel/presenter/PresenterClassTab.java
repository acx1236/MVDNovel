package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.fragment.BaseFragment;
import com.ancx.mvdnovel.fragment.ClassTabFragment;
import com.ancx.mvdnovel.view.ClassTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterClassTab {

    private ClassTabView classTabView;

    public PresenterClassTab(ClassTabView classTabView) {
        this.classTabView = classTabView;
    }

    private List<BaseFragment> fragments = new ArrayList<>(4);

    public void getFragments() {
        fragments.add(new ClassTabFragment(classTabView.getGender(), classTabView.getMajor(), "hot"));
        fragments.add(new ClassTabFragment(classTabView.getGender(), classTabView.getMajor(), "new"));
        fragments.add(new ClassTabFragment(classTabView.getGender(), classTabView.getMajor(), "reputation"));
        fragments.add(new ClassTabFragment(classTabView.getGender(), classTabView.getMajor(), "over"));
        classTabView.setViewPager(fragments);
    }
}
