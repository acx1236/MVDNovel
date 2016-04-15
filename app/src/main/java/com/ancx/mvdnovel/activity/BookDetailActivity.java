package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.presenter.PresenterBookDetail;
import com.ancx.mvdnovel.util.ImageLoader;
import com.ancx.mvdnovel.view.BookDetailView;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener, BookDetailView {

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
    private TextView tv_cache, tv_title, tv_author, tv_cat_serial, tv_update;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_cache = (TextView) findViewById(R.id.tv_cache);
        tv_cache.setOnClickListener(this);
        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_cat_serial = (TextView) findViewById(R.id.tv_cat_serial);
        tv_update = (TextView) findViewById(R.id.tv_update);

        presenterBookDetail.getDetail();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cache:
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
}
