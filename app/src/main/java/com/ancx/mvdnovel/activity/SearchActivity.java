package com.ancx.mvdnovel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.HistoryAdapter;
import com.ancx.mvdnovel.adapter.KeyWordsAdapter;
import com.ancx.mvdnovel.presenter.PresenterKeyWords;
import com.ancx.mvdnovel.view.SearchView;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView, View.OnClickListener, AdapterView.OnItemClickListener {

    private PresenterKeyWords presenterKeyWords;
    private String bookname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        presenterKeyWords = new PresenterKeyWords(this);
        bookname = getIntent().getStringExtra("bookname");

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
                presenterKeyWords.showKeyWords();
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
        inputHintListView.setOnItemClickListener(this);
        historyListView = (ListView) findViewById(R.id.historyListView);
        historyListView.setOnItemClickListener(this);
        presenterKeyWords.showHistory();
        et_name.setText(bookname);
        et_name.setSelection(getText().length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cha:
                clearEditText();
                hideChaView();
                hideKeyWordsView();
                break;
            case R.id.tv_search:
                search();
                break;
            case R.id.tv_clearhistory:
                presenterKeyWords.clearHistory();
                break;
        }
    }

    @Override
    public String getText() {
        return et_name.getText().toString().trim();
    }

    @Override
    public void showChaView() {
        iv_cha.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideChaView() {
        iv_cha.setVisibility(View.GONE);
    }

    @Override
    public void clearEditText() {
        et_name.setText(null);
    }

    @Override
    public void showKeyWordView() {
        inputHintListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideKeyWordsView() {
        inputHintListView.setVisibility(View.GONE);
    }

    private KeyWordsAdapter keyWordsAdapter;

    @Override
    public void setKeyWord(List<String> keyWords) {
        if (keyWordsAdapter == null) {
            keyWordsAdapter = new KeyWordsAdapter(keyWords);
            inputHintListView.setAdapter(keyWordsAdapter);
        } else
            keyWordsAdapter.notifyByData(keyWords);
    }

    private HistoryAdapter historyAdapter;

    @Override
    public void setHistory(List<String> historys) {
        if (historyAdapter == null) {
            historyAdapter = new HistoryAdapter(historys);
            historyListView.setAdapter(historyAdapter);
        } else
            historyAdapter.notifyByData(historys);
    }

    private void search() {
        if (TextUtils.isEmpty(getText()))
            return;
        presenterKeyWords.search();
        Intent intent = new Intent(getApplicationContext(), SearchBooksActivity.class);
        intent.putExtra("bookname", getText());
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == inputHintListView) {
            et_name.setText(keyWordsAdapter.getItem(position).toString());
        } else {
            et_name.setText(historyAdapter.getItem(position).toString());
        }
        et_name.setSelection(getText().length());
        search();
    }
}
