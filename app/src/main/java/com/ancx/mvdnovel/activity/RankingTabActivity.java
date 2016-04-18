package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.RankingTabPagerAdapter;
import com.ancx.mvdnovel.entity.Ranking;
import com.ancx.mvdnovel.fragment.BaseFragment;
import com.ancx.mvdnovel.presenter.PresenterRankingTab;
import com.ancx.mvdnovel.view.RankingTabView;

import java.util.List;

public class RankingTabActivity extends AppCompatActivity implements RankingTabView, View.OnClickListener {

    private Ranking ranking;
    private PresenterRankingTab presenterRankingTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_tab);
        ranking = (Ranking) getIntent().getSerializableExtra("ranking");

        presenterRankingTab = new PresenterRankingTab(this);

        initView();
    }

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView iv_back;
    private TextView tv_title;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(ranking.getTitle());
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        presenterRankingTab.getFragments();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public Ranking getRanking() {
        return ranking;
    }

    private RankingTabPagerAdapter rankingTabPagerAdapter;

    @Override
    public void setViewPager(List<BaseFragment> fragments) {
        rankingTabPagerAdapter = new RankingTabPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(rankingTabPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
