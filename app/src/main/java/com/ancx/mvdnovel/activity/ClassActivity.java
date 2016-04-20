package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.ClassAdapter;
import com.ancx.mvdnovel.entity.ClassName;
import com.ancx.mvdnovel.listener.OnItemClickListener;
import com.ancx.mvdnovel.presenter.PresenterClass;
import com.ancx.mvdnovel.view.ClassView;
import com.ancx.mvdnovel.widget.LoadView;

import java.util.List;

/**
 * 分类页面
 */
public class ClassActivity extends AppCompatActivity implements View.OnClickListener, ClassView, OnItemClickListener, LoadView.OnReloadDataListener {

    private PresenterClass presenterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        presenterClass = new PresenterClass(this);

        initView();
    }

    private ImageView iv_back;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private LoadView mLoadView;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mLoadView = (LoadView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        presenterClass.getClassList();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    private ClassAdapter classAdapter;

    @Override
    public void setClass(List<ClassName> male, List<ClassName> female) {
        classAdapter = new ClassAdapter(male, female);
        classAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(classAdapter);
        mLoadView.loadComplete();
    }

    @Override
    public void errorNet() {
        mLoadView.errorNet();
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(getApplicationContext(), ClassTabActivity.class);
        intent.putExtra("gender", classAdapter.getClass(position).getGender());
        intent.putExtra("major", classAdapter.getClass(position).getName());
        startActivity(intent);
    }

    @Override
    public void onReload() {
        presenterClass.getClassList();
    }
}
