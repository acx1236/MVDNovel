package com.ancx.mvdnovel.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ancx.mvdnovel.R;

/**
 * Created by Ancx on 2016/4/19.
 */
public class LoadView extends LinearLayout implements View.OnClickListener {

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_loading, this);
        initView();
    }

    private ImageView iv_load;
    private LinearLayout ll_error_net, ll_no_data;
    private AnimationDrawable mAnimationDrawable;

    private void initView() {
        iv_load = (ImageView) findViewById(R.id.iv_load);
        iv_load.setBackgroundResource(R.drawable.loading);
        mAnimationDrawable = (AnimationDrawable) iv_load.getBackground();
        mAnimationDrawable.setOneShot(false);
        startAnim();
        ll_error_net = (LinearLayout) findViewById(R.id.ll_error_net);
        ll_error_net.setOnClickListener(this);
        ll_no_data = (LinearLayout) findViewById(R.id.ll_no_data);
        ll_no_data.setOnClickListener(this);
    }

    public interface OnReloadDataListener {
        void onReload();
    }

    private OnReloadDataListener onReloadDataListener;

    public void setOnReloadDataListener(OnReloadDataListener onReloadDataListener) {
        this.onReloadDataListener = onReloadDataListener;
    }

    @Override
    public void onClick(View v) {
        if (onReloadDataListener != null) {
            loading();
            onReloadDataListener.onReload();
        }
    }

    public void errorNet() {
        ll_error_net.setVisibility(VISIBLE);
        iv_load.setVisibility(GONE);
        ll_no_data.setVisibility(GONE);
    }

    public void noData() {
        ll_no_data.setVisibility(VISIBLE);
        iv_load.setVisibility(GONE);
        ll_error_net.setVisibility(GONE);
    }

    public void loading() {
        iv_load.setVisibility(VISIBLE);
        ll_no_data.setVisibility(GONE);
        ll_error_net.setVisibility(GONE);
    }

    public void loadComplete() {
        setVisibility(GONE);
        stopAnim();
    }

    private void startAnim() {
        if (!mAnimationDrawable.isRunning())
            mAnimationDrawable.start();
    }

    private void stopAnim() {
        if (mAnimationDrawable.isRunning())
            mAnimationDrawable.stop();
    }

}