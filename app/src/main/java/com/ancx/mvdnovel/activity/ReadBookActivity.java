package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.widget.BookTextView;

public class ReadBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        initView();
    }

    private BookTextView mBookTextView;

    private void initView() {
        mBookTextView = (BookTextView) findViewById(R.id.mBookTextView);
        mBookTextView.setHintText("第一场：序章", "50", "1900");
    }
}
