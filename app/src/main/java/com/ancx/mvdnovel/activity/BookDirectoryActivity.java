package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.BookDirectoryAdapter;
import com.ancx.mvdnovel.presenter.PresenterBookDirectory;
import com.ancx.mvdnovel.view.BookDirectoryView;
import com.ancx.mvdnovel.widget.LoadView;

/**
 * 目录
 */
public class BookDirectoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, BookDirectoryView, LoadView.OnReloadDataListener {

    private boolean isEnd;
    private String title, _id;
    private PresenterBookDirectory presenterBookDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_directory);
        isEnd = getIntent().getBooleanExtra("isEnd", false);
        title = getIntent().getStringExtra("title");
        _id = getIntent().getStringExtra("_id");
        presenterBookDirectory = new PresenterBookDirectory(this);

        initView();
    }

    private TextView tv_name;
    private LoadView mLoadView;
    private ListView mListView;

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(title);
        mLoadView = (LoadView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setOnItemClickListener(this);
        presenterBookDirectory.getDirectory();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO 进入指定章节阅读
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public boolean isEnd() {
        return isEnd;
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
    public void loadComplete() {
        mLoadView.loadComplete();
    }

    @Override
    public void setSelection(int selection) {
        mListView.setSelection(selection);
    }

    @Override
    public void setAdapter(BookDirectoryAdapter bookDirectoryAdapter) {
        mListView.setAdapter(bookDirectoryAdapter);
    }

    @Override
    public void onReload() {
        presenterBookDirectory.getDirectory();
    }
}
