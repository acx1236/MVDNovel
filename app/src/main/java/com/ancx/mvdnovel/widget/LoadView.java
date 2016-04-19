package com.ancx.mvdnovel.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ancx.mvdnovel.R;
import com.mingle.widget.LoadingView;

/**
 * Created by Ancx on 2016/4/19.
 */
public class LoadView extends LinearLayout implements View.OnClickListener {

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_loading, this);
        initView();
    }

    private LoadingView mLoadingView;
    private LinearLayout ll_error_net, ll_no_data;

    private void initView() {
        mLoadingView = (LoadingView) findViewById(R.id.loadingView);
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
        mLoadingView.setVisibility(GONE);
        ll_no_data.setVisibility(GONE);
    }

    public void noData() {
        ll_no_data.setVisibility(VISIBLE);
        mLoadingView.setVisibility(GONE);
        ll_error_net.setVisibility(GONE);
    }

    public void loading() {
        mLoadingView.setVisibility(VISIBLE);
        ll_no_data.setVisibility(GONE);
        ll_error_net.setVisibility(GONE);
    }

    public void loadComplete() {
        setVisibility(GONE);
    }
}
