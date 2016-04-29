package com.ancx.mvdnovel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.adapter.UpdateSourceAdapter;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnSourceSelectedListener;

import java.util.List;

/**
 * 换源对话框
 * Created by Ancx on 2016/4/29.
 */
public class UpdateSourceDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<Source> sources;
    private String currentSourceId;

    public UpdateSourceDialog(Context context, List<Source> sources, String currentSourceId) {
        super(context, R.style.MyDialog);
        this.sources = sources;
        this.currentSourceId = currentSourceId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update_source);
        initView();
    }

    private ImageView iv_close;
    private ListView mListView;

    private void initView() {
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
        mListView.setAdapter(new UpdateSourceAdapter(sources, currentSourceId));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onSourceSelectedListener != null)
            onSourceSelectedListener.onSourceSelected(sources.get(position).get_id());
        dismiss();
    }

    private OnSourceSelectedListener onSourceSelectedListener;

    public void setOnSourceSelectedListener(OnSourceSelectedListener onSourceSelectedListener) {
        this.onSourceSelectedListener = onSourceSelectedListener;
    }
}
