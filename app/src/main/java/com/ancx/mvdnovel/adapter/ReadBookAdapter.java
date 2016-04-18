package com.ancx.mvdnovel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.util.ImageLoader;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ReadBookAdapter extends RecyclerView.Adapter {

    private List<BookDetail> books;

    public ReadBookAdapter(List<BookDetail> books) {
        this.books = books;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView iv_cover, iv_more;
        TextView tv_title, tv_author, tv_lastChapter;
        ProgressBar pb_read;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.item_my_read_book, parent, false);
        ViewHolder holder = new ViewHolder(convertView);
        holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
        holder.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
        holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
        holder.tv_lastChapter = (TextView) convertView.findViewById(R.id.tv_lastChapter);
        holder.pb_read = (ProgressBar) convertView.findViewById(R.id.pb_read);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ImageLoader.display(books.get(position).getCover(), viewHolder.iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
        viewHolder.tv_title.setText(books.get(position).getTitle());
        viewHolder.tv_author.setText(books.get(position).getAuthor());
        viewHolder.tv_lastChapter.setText(books.get(position).getUpdated() + " : " + books.get(position).getLastChapter());
    }

}
