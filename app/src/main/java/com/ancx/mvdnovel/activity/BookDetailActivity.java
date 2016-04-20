package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.presenter.PresenterBookDetail;
import com.ancx.mvdnovel.service.CacheBookService;
import com.ancx.mvdnovel.util.ImageLoader;
import com.ancx.mvdnovel.view.BookDetailView;
import com.ancx.mvdnovel.widget.LoadView;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener, BookDetailView, LoadView.OnReloadDataListener {

    private String _id;
    private PresenterBookDetail presenterBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        _id = getIntent().getStringExtra("_id");
        presenterBookDetail = new PresenterBookDetail(this);

        initView();
    }

    private ImageView iv_back, iv_cover;
    private TextView tv_cache, tv_title, tv_author, tv_cat_serial, tv_update, tv_lastChapterName, tv_content, tv_add, tv_now_read;
    private LinearLayout ll_look_dir;
    private LoadView mLoadView;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        tv_cache.setOnClickListener(this);
        mLoadView = (LoadView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_cat_serial = (TextView) findViewById(R.id.tv_cat_serial);
        tv_update = (TextView) findViewById(R.id.tv_update);
        tv_lastChapterName = (TextView) findViewById(R.id.tv_lastChapterName);
        tv_lastChapterName.setOnClickListener(this);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);
        tv_now_read = (TextView) findViewById(R.id.tv_now_read);
        tv_now_read.setOnClickListener(this);
        ll_look_dir = (LinearLayout) findViewById(R.id.ll_look_dir);
        ll_look_dir.setOnClickListener(this);
        presenterBookDetail.getDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cache:
                Intent cacheService = new Intent(getApplicationContext(), CacheBookService.class);
                cacheService.putExtra("book", book);
                startService(cacheService);
                tv_add.setText("移除此书");
                break;
            case R.id.tv_add:
                presenterBookDetail.operateBook();
                break;
            case R.id.tv_now_read:
                break;
            case R.id.ll_look_dir:
                Intent intent1 = new Intent(getApplicationContext(), BookDirectoryActivity.class);
                intent1.putExtra("title", tv_title.getText().toString());
                intent1.putExtra("_id", _id);
                startActivity(intent1);
                break;
            case R.id.tv_lastChapterName:
                Intent intent2 = new Intent(getApplicationContext(), BookDirectoryActivity.class);
                intent2.putExtra("isEnd", true);
                intent2.putExtra("title", tv_title.getText());
                intent2.putExtra("_id", _id);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public String getId() {
        return _id;
    }

    @Override
    public void showCover(String imgPath) {
        ImageLoader.display(imgPath, iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
    }

    @Override
    public void showTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void showCat_Serial(String cat_Serial) {
        tv_cat_serial.setText(cat_Serial);
    }

    @Override
    public void showAuthor(String author) {
        tv_author.setText(author);
    }

    @Override
    public void showUpdate(String update) {
        tv_update.setText(update);
    }

    @Override
    public void showLastChapter(String lastChapter) {
        tv_lastChapterName.setText(lastChapter);
    }

    @Override
    public void showLongIntro(String longIntro) {
        tv_content.setText(longIntro);
    }

    @Override
    public void setAddText(String text) {
        tv_add.setText(text);
        mLoadView.loadComplete();
    }

    @Override
    public void errorNet() {
        mLoadView.errorNet();
    }

    private BookDetail book;

    @Override
    public void setBook(BookDetail book) {
        this.book = book;
    }

    @Override
    public void onReload() {
        presenterBookDetail.getDetail();
    }
}
