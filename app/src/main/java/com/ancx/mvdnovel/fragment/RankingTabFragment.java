package com.ancx.mvdnovel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.activity.BookDetailActivity;
import com.ancx.mvdnovel.adapter.RankingBookAdapter;
import com.ancx.mvdnovel.entity.RankingBook;
import com.ancx.mvdnovel.presenter.PresenterRankingTabFragment;
import com.ancx.mvdnovel.view.RankingTabFragmentView;
import com.ancx.mvdnovel.widget.LoadView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class RankingTabFragment extends BaseFragment implements RankingTabFragmentView, AdapterView.OnItemClickListener, LoadView.OnReloadDataListener {

    private String rankId;

    private PresenterRankingTabFragment presenterRankingTabFragment;

    public RankingTabFragment(String rankId) {
        this.rankId = rankId;
    }

    @Override
    public View onCreateView_(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getContext(), R.layout.fragment_ranking_tab, null);
    }

    private ListView mListView;
    private LoadView mLoadView;

    @Override
    public void initView() {
        mListView = (ListView) getView().findViewById(R.id.mListView);
        mListView.setOnItemClickListener(this);
        mLoadView = (LoadView) getView().findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
    }

    @Override
    public void delayLoad() {
        presenterRankingTabFragment = new PresenterRankingTabFragment(this);
        presenterRankingTabFragment.getContent();
    }

    @Override
    public String getRankId() {
        return rankId;
    }

    private RankingBookAdapter rankingBookAdapter;

    @Override
    public void setContent(List<RankingBook> books) {
        rankingBookAdapter = new RankingBookAdapter(books);
        mListView.setAdapter(rankingBookAdapter);
        mLoadView.loadComplete();
    }

    @Override
    public void errorNet() {
        mLoadView.errorNet();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), BookDetailActivity.class);
        intent.putExtra("_id", ((RankingBook) rankingBookAdapter.getItem(position)).get_id());
        startActivity(intent);
    }

    @Override
    public void onReload() {
        presenterRankingTabFragment.getContent();
    }
}
