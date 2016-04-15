package com.ancx.mvdnovel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.Books;
import com.ancx.mvdnovel.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public class SearchBooksAdapter extends BaseAdapter {

    private List<Books> mData;

    public SearchBooksAdapter(List<Books> mData) {
        if (mData == null)
            this.mData = new ArrayList<>();
        else
            this.mData = mData;
    }

    public void notifyByData(List<Books> mData) {
        if (mData == null)
            this.mData = new ArrayList<>();
        else
            this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_search_books, null);
            holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            holder.tv_retentionRatio = (TextView) convertView.findViewById(R.id.tv_retentionRatio);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        Books books = (Books) getItem(position);
        ImageLoader.display(books.getCover(), holder.iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
        holder.tv_title.setText(books.getTitle());
        holder.tv_author.setText(books.getAuthor() + "著");
        StringBuilder sb = new StringBuilder("留存率: ");
        if ("null".equals(books.getRetentionRatio()) || books.getRetentionRatio() == null)
            sb.append("0");
        else
            sb.append(books.getRetentionRatio());
        sb.append("%");
        holder.tv_retentionRatio.setText(sb.toString());
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_cover;
        TextView tv_title, tv_author, tv_retentionRatio;
    }
}
