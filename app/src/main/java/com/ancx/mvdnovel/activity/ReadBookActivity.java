package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.presenter.PresenterReadBook;
import com.ancx.mvdnovel.view.ReadBookView;
import com.ancx.mvdnovel.widget.BookTextView;
import com.ancx.mvdnovel.widget.LoadView;

public class ReadBookActivity extends AppCompatActivity implements BookTextView.OnChapterChangeListener, ReadBookView, View.OnClickListener, LoadView.OnReloadDataListener {

    private String _id;
    private PresenterReadBook presenterReadBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        _id = getIntent().getStringExtra("_id");
        presenterReadBook = new PresenterReadBook(this);

        initView();
    }

    private LoadView mLoadView;
    private BookTextView mBookTextView;
    private LinearLayout rl_menu;
    private View hideView;

    private void initView() {
        mLoadView = (LoadView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        mBookTextView = (BookTextView) findViewById(R.id.mBookTextView);
        mBookTextView.setOnChapterChangeListener(this);
        setResult(RESULT_OK);
        presenterReadBook.getBookText();
        rl_menu = (LinearLayout) findViewById(R.id.ll_menu);
        hideView = findViewById(R.id.hideView);
        hideView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hideView:
                rl_menu.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPreChapter() {
        presenterReadBook.preChapter();
    }

    @Override
    public void onNextChapter() {
        presenterReadBook.nextChapter();
    }

    @Override
    public void onMenu() {
        rl_menu.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRecord(int currentPage) {
        presenterReadBook.updateRecord(currentPage);
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public void setText(String content) {
        mBookTextView.setContent(content);
    }

    @Override
    public void setHint(String title, int currentChapter, String chaptersCount) {
        mBookTextView.setHintText(title, currentChapter, chaptersCount);
    }

    @Override
    public void errorNet() {
        mLoadView.errorNet();
    }

    @Override
    public void noData() {
        mLoadView.noData();
    }

    @Override
    public void showLoading() {
        mLoadView.loading();
    }

    @Override
    public void onReload() {
        presenterReadBook.getBookText();
    }

    @Override
    public void loadComplete() {
        mLoadView.loadComplete();
    }

    @Override
    public void setReadPage(int readPage) {
        mBookTextView.setCurrentPage(readPage);
    }

    @Override
    public void readComplete() {
        mBookTextView.readComplete();
    }
}
