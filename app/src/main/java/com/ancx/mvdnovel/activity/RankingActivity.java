package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.RankingAdapter;
import com.ancx.mvdnovel.entity.Ranking;
import com.ancx.mvdnovel.presenter.PresenterRanking;
import com.ancx.mvdnovel.view.RankingView;

import java.util.List;

/**
 * 榜单页面
 */
public class RankingActivity extends AppCompatActivity implements View.OnClickListener, RankingView, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private PresenterRanking presenterRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        presenterRanking = new PresenterRanking(this);

        initView();
    }

    private ImageView iv_back;
    private ListView mListView;
    private TextView tv_sex;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);
        presenterRanking.getRanking();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), RankingTabActivity.class);
        intent.putExtra("ranking", (Ranking) rankingAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (male == 0) {
            tv_sex.setVisibility(View.GONE);
            return;
        }
        tv_sex.setVisibility(View.VISIBLE);
        if (firstVisibleItem < male)
            tv_sex.setText("男生");
        else
            tv_sex.setText("女生");
    }

    private RankingAdapter rankingAdapter;
    private int male;

    @Override
    public void setRanking(List<Ranking> mData, int male, int female) {
        rankingAdapter = new RankingAdapter(mData, male, female);
        mListView.setAdapter(rankingAdapter);
        this.male = male;
    }
}
