package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.fragment.BaseFragment;
import com.ancx.mvdnovel.fragment.RankingTabFragment;
import com.ancx.mvdnovel.view.RankingTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterRankingTab {

    private RankingTabView rankingTabView;

    public PresenterRankingTab(RankingTabView rankingTabView) {
        this.rankingTabView = rankingTabView;
    }

    private List<BaseFragment> fragments = new ArrayList<>();

    public void getFragments() {
        fragments.add(new RankingTabFragment(rankingTabView.getRanking().get_id()));
        fragments.add(new RankingTabFragment(rankingTabView.getRanking().getMonthRank()));
        fragments.add(new RankingTabFragment(rankingTabView.getRanking().getTotalRank()));
        rankingTabView.setViewPager(fragments);
    }
}
