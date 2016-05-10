package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.dialog.UpdateSourceDialog;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnSourceSelectedListener;
import com.ancx.mvdnovel.presenter.PresenterReadBook;
import com.ancx.mvdnovel.service.CacheBookService;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.view.ReadBookView;
import com.ancx.mvdnovel.widget.BookTextView;
import com.ancx.mvdnovel.widget.LoadView;

import java.util.List;

public class ReadBookActivity extends AppCompatActivity implements BookTextView.OnChapterChangeListener, ReadBookView, View.OnClickListener, LoadView.OnReloadDataListener, OnSourceSelectedListener {

    private String _id;
    private PresenterReadBook presenterReadBook;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ll_menu.getVisibility() == View.VISIBLE) {
                ll_menu.setVisibility(View.GONE);
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            mBookTextView.nextPage();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mBookTextView.prePage();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_read_book);
        _id = getIntent().getStringExtra("_id");
        presenterReadBook = new PresenterReadBook(this);

        initView();
    }

    private LoadView mLoadView;
    private BookTextView mBookTextView;
    private LinearLayout ll_menu;
    private View hideView;
    private ImageView iv_back;
    private TextView tv_source, tv_dir, tv_cache, tv_night, tv_aadd, tv_aminus, tv_prechapter, tv_nextchapter;

    private void initView() {
        mLoadView = (LoadView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        mBookTextView = (BookTextView) findViewById(R.id.mBookTextView);
        mBookTextView.setOnChapterChangeListener(this);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        hideView = findViewById(R.id.hideView);
        hideView.setOnClickListener(this);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_source = (TextView) findViewById(R.id.tv_source);
        tv_source.setOnClickListener(this);
        tv_dir = (TextView) findViewById(R.id.tv_dir);
        tv_dir.setOnClickListener(this);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        tv_cache.setOnClickListener(this);
        tv_night = (TextView) findViewById(R.id.tv_night);
        tv_night.setOnClickListener(this);
        tv_aadd = (TextView) findViewById(R.id.tv_aadd);
        tv_aadd.setOnClickListener(this);
        tv_aminus = (TextView) findViewById(R.id.tv_aminus);
        tv_aminus.setOnClickListener(this);
        tv_prechapter = (TextView) findViewById(R.id.tv_prechapter);
        tv_prechapter.setOnClickListener(this);
        tv_nextchapter = (TextView) findViewById(R.id.tv_nextchapter);
        tv_nextchapter.setOnClickListener(this);
        setResult(RESULT_OK);
        presenterReadBook.getBookText();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        _id = getIntent().getStringExtra("_id");
        presenterReadBook.newChapter();
        presenterReadBook.getBookText();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hideView:
                ll_menu.setVisibility(View.GONE);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_source:
                presenterReadBook.updateSorce();
                break;
            case R.id.tv_dir:
                Intent intent = new Intent(getApplicationContext(), BookDirectoryActivity.class);
                intent.putExtra("_id", _id);
                intent.putExtra("title", DatabaseManager.getTitle(_id));
                intent.putExtra("selection", DatabaseManager.getReadCount(_id));
                startActivity(intent);
                break;
            case R.id.tv_cache:
                Intent cacheService = new Intent(getApplicationContext(), CacheBookService.class);
                cacheService.putExtra("_id", _id);
                startService(cacheService);
                break;
            case R.id.tv_night:
                mBookTextView.setNight(!mBookTextView.isNight());
                break;
            case R.id.tv_aadd:
                mBookTextView.addTextSize();
                break;
            case R.id.tv_aminus:
                mBookTextView.minusTextSize();
                break;
            case R.id.tv_prechapter:
                mBookTextView.setCurrentPage(1);
                presenterReadBook.preChapter();
                break;
            case R.id.tv_nextchapter:
                mBookTextView.setCurrentPage(1);
                presenterReadBook.nextChapter();
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
        ll_menu.setVisibility(View.VISIBLE);
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

    private UpdateSourceDialog updateSourceDialog;

    @Override
    public void setSource(List<Source> sources) {
        if (updateSourceDialog == null) {
            updateSourceDialog = new UpdateSourceDialog(this, sources, DatabaseManager.getSourceId(_id));
            updateSourceDialog.setOnSourceSelectedListener(this);
        }
        updateSourceDialog.show();
    }

    @Override
    public void onSourceSelected(String sourceId) {
        DatabaseManager.updateSourceId(_id, sourceId);
        presenterReadBook.getBookText();
    }
}
