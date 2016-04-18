package com.ancx.mvdnovel.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.view.BaseViewHolder;
import com.mingle.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/3/1.
 */
public class SuperRefreshLayout extends LinearLayout implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public SuperRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_super_refresh, this);
        initView();
    }

    private LoadingView mLoadingView;
    private RelativeLayout layout_no_data, layout_no_internet;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private View onLoadView, noDataView;

    private void initView() {
        mLoadingView = (LoadingView) findViewById(R.id.mLoadingView);
        layout_no_data = (RelativeLayout) findViewById(R.id.layout_no_data);
        layout_no_data.setOnClickListener(this);
        layout_no_internet = (RelativeLayout) findViewById(R.id.layout_no_internet);
        layout_no_internet.setOnClickListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                boolean isBottom = mGridLayoutManager.findLastCompletelyVisibleItemPosition() == mGridLayoutManager.getItemCount() - 1;
                if (isBottom && onLoadListener != null && !isLoading && !isRefreshing && !isLoadFinish) {
                    isLoading = true;
                    mSwipeRefreshLayout.setEnabled(false);
                    onLoadListener.onLoad();
                }
            }
        });
        onLoadView = LayoutInflater.from(getContext()).inflate(R.layout.layout_load, null, false);
        onLoadView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        noDataView = LayoutInflater.from(getContext()).inflate(R.layout.layout_load_no_data, null, false);
        noDataView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void setSpanCount(int spanCount) {
        mGridLayoutManager.setSpanCount(spanCount);
    }

    public void setNoInternet() {
        mLoadingView.setVisibility(View.GONE);
        layout_no_data.setVisibility(View.GONE);
        layout_no_internet.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_no_data:
                layout_no_data.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
                break;
            case R.id.layout_no_internet:
                layout_no_internet.setVisibility(View.GONE);
                mLoadingView.setVisibility(View.VISIBLE);
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
                break;
        }
    }

    private boolean isRefreshing;

    @Override
    public void onRefresh() {
        isRefreshing = true;
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    private OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    private boolean isLoading;

    public interface OnLoadListener {
        void onLoad();
    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }

    private List<View> headerViews = new ArrayList<>();

    public void addHeaderView(View headerView) {
        headerViews.add(0, headerView);
    }

    private List<View> footerViews = new ArrayList<>();

    public void addFooterView(View footerView) {
        footerViews.add(0, footerView);
    }

    private List mData;
    private SuperAdapter mAdapter;

    public SuperAdapter setData(List mData, DataAdapter dataAdapter) {
        this.mData = mData;
        this.dataAdapter = dataAdapter;
        mAdapter = new SuperAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return mAdapter;
    }

    public class SuperAdapter extends RecyclerView.Adapter {

        private final int BASEVIEWHOLDER = 2147483607;
        private final int ONLOADVIEWHOLDER = 2147483617;
        private final int LOADFINISHVIEWHOLDER = 2147483627;

        private class HeaderViewHolder extends RecyclerView.ViewHolder {

            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class OnLoadViewHolder extends RecyclerView.ViewHolder {

            public OnLoadViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class LoadFinishViewHolder extends RecyclerView.ViewHolder {

            public LoadFinishViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position < headerViews.size()) {
                return position;
            } else if (position >= headerViews.size() && position < headerViews.size() + mData.size()) {
                return BASEVIEWHOLDER;
            } else if (position >= headerViews.size() + mData.size() && position < headerViews.size() + mData.size() + footerViews.size()) {
                return position;
            } else {
                if (isLoadFinish) {
                    return LOADFINISHVIEWHOLDER;
                } else {
                    return ONLOADVIEWHOLDER;
                }
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType < headerViews.size()) {
                return new HeaderViewHolder(headerViews.get(viewType));
            } else if (viewType == BASEVIEWHOLDER) {
                return dataAdapter.onCreateViewHolder(parent, viewType);
            } else if (viewType >= headerViews.size() + mData.size() && viewType < headerViews.size() + mData.size() + footerViews.size()) {
                return new FooterViewHolder(headerViews.get(viewType - headerViews.size() - mData.size()));
            } else if (viewType == ONLOADVIEWHOLDER) {
                return new OnLoadViewHolder(onLoadView);
            } else {
                return new LoadFinishViewHolder(noDataView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof BaseViewHolder) {
                position -= headerViews.size();
                dataAdapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            return mData.size() + headerViews.size() + footerViews.size() + 1;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position >= headerViews.size() && position < headerViews.size() + mData.size()) {
                        return 1;
                    } else {
                        return mGridLayoutManager.getSpanCount();
                    }
                }
            });
        }

        public void notifyUpdate(int total) {
            mLoadingView.setVisibility(View.GONE);
            if (isLoading) {
                isLoading = false;
                mSwipeRefreshLayout.setEnabled(true);
            }
            if (isRefreshing) {
                isRefreshing = false;
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (layout_no_data.getVisibility() == View.VISIBLE) {
                layout_no_data.setVisibility(View.GONE);
            }
            if ((mData.size() == 0 || total == 0) && headerViews.size() == 0) {
                layout_no_data.setVisibility(View.VISIBLE);
            }
            if (mData.size() < total) {
                isLoadFinish = false;
            } else {
                isLoadFinish = true;
            }
            notifyDataSetChanged();
        }

    }

    private boolean isLoadFinish;

    public interface DataAdapter {
        RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
    }

    private DataAdapter dataAdapter;

    public void setRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                isRefreshing = true;
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

}
