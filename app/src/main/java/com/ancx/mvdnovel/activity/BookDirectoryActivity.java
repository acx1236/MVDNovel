package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.BookDirectoryAdapter;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.presenter.PresenterBookDirectory;
import com.ancx.mvdnovel.view.BookDirectoryView;

import java.util.List;

public class BookDirectoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, BookDirectoryView {

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
    private ListView mListView;

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(title);
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

    private BookDirectoryAdapter bookDirectoryAdapter;

    @Override
    public void setDirectory(List<Chapter> chapters) {
        int selection = 0;
        if (isEnd)
            selection = chapters.size() - 1;
        bookDirectoryAdapter = new BookDirectoryAdapter(chapters, selection, _id);
        mListView.setAdapter(bookDirectoryAdapter);
        mListView.setSelection(selection);
    }
}
