package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.Ranking;
import com.ancx.mvdnovel.listener.OnRankingListener;
import com.ancx.mvdnovel.model.ModelRanking;
import com.ancx.mvdnovel.view.RankingView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class PresenterRanking implements OnRankingListener {

    private RankingView rankingView;

    public PresenterRanking(RankingView rankingView) {
        this.rankingView = rankingView;
    }

    private ModelRanking modelRanking = new ModelRanking();

    public void getRanking() {
        modelRanking.setOnRankingListener(this);
        modelRanking.getRanking();
    }

    @Override
    public void setRanking(List<Ranking> males, List<Ranking> females) {
        List<Ranking> mData = new ArrayList<>(males.size() + females.size());
        mData.addAll(males);
        mData.addAll(females);
        rankingView.setRanking(mData, males.size(), females.size());
    }

    @Override
    public void onFailed() {
        rankingView.errorNet();
    }
}
