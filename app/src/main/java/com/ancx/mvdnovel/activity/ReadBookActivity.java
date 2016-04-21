package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.util.MemoryUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.util.ReaderUtil;
import com.ancx.mvdnovel.widget.BookTextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ReadBookActivity extends AppCompatActivity implements BookTextView.OnChapterChangeListener {

    private BookDetail book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        book = (BookDetail) getIntent().getSerializableExtra("book");

        initView();
    }

    private BookTextView mBookTextView;

    private void initView() {
        mBookTextView = (BookTextView) findViewById(R.id.mBookTextView);
        mBookTextView.setOnChapterChangeListener(this);
        String saveNovelPath = MemoryUtil.getSaveNovelPath(book.get_id(), "第八十八章 灵目之变");
        if (saveNovelPath == null)
            return;
        try {
            mBookTextView.setHintText("第八十八章 灵目之变", "89", "251");
            String text = ReaderUtil.getString(new FileInputStream(saveNovelPath));
            mBookTextView.setContent(text);
        } catch (FileNotFoundException e) {
            MsgUtil.LogException(e);
            MsgUtil.LogTag("ReadBookActivity -> initView -> 获取文本异常");
        }
    }

    @Override
    public void onPreChapter() {
        MsgUtil.LogTag("onPreChapter ----- 本章结束");
    }

    @Override
    public void onNextChapter() {
        MsgUtil.LogTag("onNextChapter ----- 本章结束");
    }

    @Override
    public void onMenu() {
        MsgUtil.LogTag("onMenu ----- 呼出菜单");
    }
}
