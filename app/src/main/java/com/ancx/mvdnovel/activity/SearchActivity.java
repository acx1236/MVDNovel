package com.ancx.mvdnovel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.view.SearchView;

public class SearchActivity extends AppCompatActivity implements SearchView, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
    }

    private ImageView iv_back, iv_cha;
    private EditText et_name;
    private TextView tv_search, tv_clearhistory;
    private ListView inputHintListView, historyListView;

    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        iv_cha = (ImageView) findViewById(R.id.iv_cha);
        iv_cha.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showKeyWord();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        tv_clearhistory = (TextView) findViewById(R.id.tv_clearhistory);
        tv_clearhistory.setOnClickListener(this);
        inputHintListView = (ListView) findViewById(R.id.inputHintListView);
        historyListView = (ListView) findViewById(R.id.historyListView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.iv_cha:
                clearEditText();
                break;
            case R.id.tv_search:
                search();
                break;
            case R.id.tv_clearhistory:
                clearHistory();
                break;
        }
    }

    @Override
    public void back() {
        finish();
    }

    @Override
    public void clearEditText() {
        et_name.setText(null);
        inputHintListView.setVisibility(View.GONE);
    }

    @Override
    public String getText() {
        return et_name.getText().toString().trim();
    }

    @Override
    public void clearHistory() {

    }

    @Override
    public void showHisTory() {

    }

    @Override
    public void showKeyWordView() {
        inputHintListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showKeyWord() {

    }

    @Override
    public void search() {

    }
}
