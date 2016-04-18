package com.ancx.mvdnovel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.RankingBook;
import com.ancx.mvdnovel.util.ImageLoader;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class RankingBookAdapter extends BaseAdapter {

    private List<RankingBook> books;

    public RankingBookAdapter(List<RankingBook> books) {
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_book_list, null);
            holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_author_cat = (TextView) convertView.findViewById(R.id.tv_author_cat);
            holder.tv_shortIntro = (TextView) convertView.findViewById(R.id.tv_shortIntro);
            holder.tv_retentionRatio = (TextView) convertView.findViewById(R.id.tv_retentionRatio);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        RankingBook rankingBook = (RankingBook) getItem(position);
        ImageLoader.display(rankingBook.getCover(), holder.iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
        holder.tv_title.setText(rankingBook.getTitle());
        holder.tv_author_cat.setText(rankingBook.getAuthor() + "  |  " + rankingBook.getCat());
        holder.tv_shortIntro.setText(rankingBook.getShortIntro());
        StringBuilder sb = new StringBuilder("留存率: ");
        if ("null".equals(rankingBook.getRetentionRatio()) || rankingBook.getRetentionRatio() == null)
            sb.append("0");
        else
            sb.append(rankingBook.getRetentionRatio());
        sb.append("%");
        holder.tv_retentionRatio.setText(sb.toString());
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_cover;
        TextView tv_title, tv_author_cat, tv_shortIntro, tv_retentionRatio;
    }
}
