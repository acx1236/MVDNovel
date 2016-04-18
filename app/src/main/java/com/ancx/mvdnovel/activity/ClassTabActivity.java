package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.ClassTabPagerAdapter;
import com.ancx.mvdnovel.fragment.BaseFragment;
import com.ancx.mvdnovel.presenter.PresenterClassTab;
import com.ancx.mvdnovel.view.ClassTabView;

import java.util.List;

public class ClassTabActivity extends AppCompatActivity implements View.OnClickListener, ClassTabView {

    private String gender, major;

    private PresenterClassTab presenterClassTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_tab);
        gender = getIntent().getStringExtra("gender");
        major = getIntent().getStringExtra("major");

        presenterClassTab = new PresenterClassTab(this);

        initView();
    }

    private ImageView iv_back;
    private TextView tv_title;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(major);
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        presenterClassTab.getFragments();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getMajor() {
        return major;
    }

    private ClassTabPagerAdapter classTabPagerAdapter;

    @Override
    public void setViewPager(List<BaseFragment> fragments) {
        classTabPagerAdapter = new ClassTabPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(classTabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
