package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.SearchBooksAdapter;
import com.ancx.mvdnovel.entity.Books;
import com.ancx.mvdnovel.presenter.PresenterSearchBooks;
import com.ancx.mvdnovel.view.SearchBooksView;

import java.util.List;

public class SearchBooksActivity extends AppCompatActivity implements View.OnClickListener, SearchBooksView, AdapterView.OnItemClickListener {

    private String bookname;
    private PresenterSearchBooks presenterSearchBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);
        bookname = getIntent().getStringExtra("bookname");
        presenterSearchBooks = new PresenterSearchBooks(this);
        initView();
    }

    private ImageView iv_back;
    private TextView tv_name, tv_bookshelf;
    private ListView mListView;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(bookname);
        tv_name.setOnClickListener(this);
        tv_bookshelf = (TextView) findViewById(R.id.tv_bookshelf);
        tv_bookshelf.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setOnItemClickListener(this);
        presenterSearchBooks.search();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        bookname = intent.getStringExtra("bookname");
        tv_name.setText(bookname);
        presenterSearchBooks.search();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_name:
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("bookname", bookname);
                startActivity(intent);
                break;
            case R.id.tv_bookshelf:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;
        }
    }

    @Override
    public String getBookName() {
        return bookname;
    }

    private SearchBooksAdapter searchBooksAdapter;

    @Override
    public void setResult(List<Books> mData) {
        if (searchBooksAdapter == null) {
            searchBooksAdapter = new SearchBooksAdapter(mData);
            mListView.setAdapter(searchBooksAdapter);
        } else
            searchBooksAdapter.notifyByData(mData);
        mListView.setSelection(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
        intent.putExtra("_id", ((Books) searchBooksAdapter.getItem(position)).get_id());
        startActivity(intent);
    }
}
