package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.Ranking;

public class RankingListActivity extends AppCompatActivity {

    private Ranking ranking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_list);
        ranking = (Ranking) getIntent().getSerializableExtra("ranking");

        initView();
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    }
}
