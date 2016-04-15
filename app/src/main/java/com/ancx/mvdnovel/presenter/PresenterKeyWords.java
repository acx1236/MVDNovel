package com.ancx.mvdnovel.presenter;

import android.text.TextUtils;

import com.ancx.mvdnovel.listener.OnKeyWordsListener;
import com.ancx.mvdnovel.model.ModelKeyWords;
import com.ancx.mvdnovel.view.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public class PresenterKeyWords implements OnKeyWordsListener {

    private SearchView searchView;

    public PresenterKeyWords(SearchView searchView) {
        this.searchView = searchView;
    }

    private ModelKeyWords modelKeyWords = new ModelKeyWords();

    public void showKeyWords() {
        if (TextUtils.isEmpty(searchView.getText())) {
            searchView.hideChaView();
            searchView.hideKeyWordsView();
            return;
        }
        searchView.showChaView();
        modelKeyWords.setOnKeyWordsListener(this);
        modelKeyWords.getKeyWords(searchView.getText());
    }

    @Override
    public void setKeyWords(List<String> keyWords) {
        if (keyWords == null)
            return;
        if (keyWords.size() == 0) {
            searchView.hideKeyWordsView();
            return;
        }
        searchView.setKeyWord(keyWords);
        searchView.showKeyWordView();
    }

    public void showHistory() {
        List<String> history = modelKeyWords.getHistory();
        if (history == null || history.size() == 0)
            return;
        searchView.setHistory(history);
    }

    public void clearHistory() {
        modelKeyWords.clearHistory();
        searchView.setHistory(new ArrayList<String>());
    }

    public void search() {
        modelKeyWords.addHistory(searchView.getText());
        showHistory();
    }
}
