package com.ancx.mvdnovel.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.ClassBook;
import com.ancx.mvdnovel.listener.OnItemClickListener;
import com.ancx.mvdnovel.util.ImageLoader;
import com.ancx.mvdnovel.view.BaseViewHolder;
import com.ancx.mvdnovel.widget.SuperRefreshLayout;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassBookAdapter implements SuperRefreshLayout.DataAdapter, BaseViewHolder.OnItemClickListener {

    private List<ClassBook> mData;

    public ClassBookAdapter(List<ClassBook> mData) {
        this.mData = mData;
    }

    private class ViewHolder extends BaseViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        ImageView iv_cover;
        TextView tv_title, tv_author_cat, tv_shortIntro, tv_retentionRatio;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.item_book_list, parent, false);
        ViewHolder holder = new ViewHolder(convertView);
        holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
        holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tv_author_cat = (TextView) convertView.findViewById(R.id.tv_author_cat);
        holder.tv_shortIntro = (TextView) convertView.findViewById(R.id.tv_shortIntro);
        holder.tv_retentionRatio = (TextView) convertView.findViewById(R.id.tv_retentionRatio);
        holder.setOnItemClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        ClassBook classBook = mData.get(position);
        ImageLoader.display(classBook.getCover(), viewHolder.iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
        viewHolder.tv_title.setText(classBook.getTitle());
        viewHolder.tv_author_cat.setText(classBook.getAuthor() + "  |  " + classBook.getMajorCate());
        viewHolder.tv_shortIntro.setText(classBook.getShortIntro());
        StringBuilder sb = new StringBuilder("留存率: ");
        if ("null".equals(classBook.getRetentionRatio()) || classBook.getRetentionRatio() == null)
            sb.append("0");
        else
            sb.append(classBook.getRetentionRatio());
        sb.append("%");
        viewHolder.tv_retentionRatio.setText(sb.toString());
    }

    @Override
    public void onItemClick(View v, int position) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(v, position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
