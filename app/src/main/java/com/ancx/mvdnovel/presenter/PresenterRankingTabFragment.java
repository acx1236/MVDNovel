package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.RankingBook;
import com.ancx.mvdnovel.listener.OnRankingBookListListener;
import com.ancx.mvdnovel.model.ModelRankingTabFragment;
import com.ancx.mvdnovel.view.RankingTabFragmentView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterRankingTabFragment implements OnRankingBookListListener {

    private RankingTabFragmentView rankingTabFragmentView;

    public PresenterRankingTabFragment(RankingTabFragmentView rankingTabFragmentView) {
        this.rankingTabFragmentView = rankingTabFragmentView;
    }

    private ModelRankingTabFragment modelRankingTabFragment = new ModelRankingTabFragment();

    public void getContent() {
        modelRankingTabFragment.setOnRankingBookListListener(this);
        modelRankingTabFragment.getContent(rankingTabFragmentView.getRankId());
    }

    @Override
    public void onRankingBookList(List<RankingBook> books) {
        rankingTabFragmentView.setContent(books);
    }

    @Override
    public void onFailed() {
        rankingTabFragmentView.errorNet();
    }
}
