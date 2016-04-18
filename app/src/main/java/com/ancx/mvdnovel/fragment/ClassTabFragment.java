package com.ancx.mvdnovel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.activity.BookDetailActivity;
import com.ancx.mvdnovel.adapter.ClassBookAdapter;
import com.ancx.mvdnovel.entity.ClassBook;
import com.ancx.mvdnovel.listener.OnItemClickListener;
import com.ancx.mvdnovel.presenter.PresenterClassTabFragment;
import com.ancx.mvdnovel.view.ClassTabFragmentView;
import com.ancx.mvdnovel.widget.SuperRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassTabFragment extends BaseFragment implements SuperRefreshLayout.OnRefreshListener, SuperRefreshLayout.OnLoadListener, ClassTabFragmentView, OnItemClickListener {

    private String gender, major, type;

    private PresenterClassTabFragment presenterClassTabFragment;

    public ClassTabFragment(String gender, String major, String type) {
        this.gender = gender;
        this.major = major;
        this.type = type;
    }

    @Override
    public View onCreateView_(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_class_tab, null);
    }

    private SuperRefreshLayout mSuperRefreshLayout;

    @Override
    public void initView() {
        mSuperRefreshLayout = (SuperRefreshLayout) getView().findViewById(R.id.mSuperRefreshLayout);
        mSuperRefreshLayout.setOnRefreshListener(this);
        mSuperRefreshLayout.setOnLoadListener(this);
    }

    private int start; // 每次增加65
    private List<ClassBook> mData = new ArrayList<>();
    private ClassBookAdapter classBookAdapter;
    private SuperRefreshLayout.SuperAdapter mAdapter;

    @Override
    public void delayLoad() {
        classBookAdapter = new ClassBookAdapter(mData);
        classBookAdapter.setOnItemClickListener(this);
        mAdapter = mSuperRefreshLayout.setData(mData, classBookAdapter);
        presenterClassTabFragment = new PresenterClassTabFragment(this);
        presenterClassTabFragment.getBookList();
    }

    @Override
    public void onRefresh() {
        start = 0;
        presenterClassTabFragment.getBookList();
    }

    private int addCount;

    @Override
    public void onLoad() {
        start += addCount;
        presenterClassTabFragment.getBookList();
    }

    @Override
    public String getStart() {
        return start + "";
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getMajor() {
        return major;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public List<ClassBook> getData() {
        return mData;
    }

    @Override
    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    @Override
    public void setBookList(int total) {
        mAdapter.notifyUpdate(total);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra("_id", mData.get(position).get_id());
        startActivity(intent);
    }
}
