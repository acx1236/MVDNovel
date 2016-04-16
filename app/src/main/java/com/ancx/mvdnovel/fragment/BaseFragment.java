package com.ancx.mvdnovel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ancx on 2016/3/4.
 */
public abstract class BaseFragment extends Fragment {

    public View parentView;
    private boolean theFragmentIsVisible;
    private boolean activityIsCreated;
    private boolean theDelayLoad;
    private boolean theViewIsInit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (parentView == null) {
            parentView = onCreateView_(inflater, container, savedInstanceState);
        }
        return parentView;
    }

    public abstract View onCreateView_(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            theFragmentIsVisible = true;
            onVisible();
        } else {
            theFragmentIsVisible = false;
            onInvisible();
        }
    }

    private void onVisible() {
        if (activityIsCreated && !theDelayLoad) {
            theDelayLoad = true;
            delayLoad();
        }
    }

    public void onInvisible() {
    }

    @Nullable
    @Override
    public View getView() {
        if (parentView == null) {
            return super.getView();
        } else {
            return parentView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityIsCreated = true;
        if (!theViewIsInit) {
            theViewIsInit = true;
            initView();
        }
        if (theFragmentIsVisible && !theDelayLoad) {
            theDelayLoad = true;
            delayLoad();
        }
    }

    public abstract void initView();

    public abstract void delayLoad();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (parentView != null) {
            ((ViewGroup) parentView.getParent()).removeView(parentView);
        }
    }

}
