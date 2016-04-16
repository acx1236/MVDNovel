package com.ancx.mvdnovel.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.Chapter;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class BookDirectoryAdapter extends BaseAdapter {

    private List<Chapter> chapters;
    private int selection;
    private String _id;
    private SharedPreferences sharedPreferences;

    public BookDirectoryAdapter(List<Chapter> chapters, int selection, String _id) {
        this.chapters = chapters;
        this.selection = selection;
        this._id = _id;
        sharedPreferences = NovelApp.getInstance().getSharedPreferences(_id, Activity.MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return chapters.size();
    }

    @Override
    public Object getItem(int position) {
        return chapters.get(position);
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_book_directory, null);
            holder.tv_chapterName = (TextView) convertView.findViewById(R.id.tv_chapterName);
            holder.tv_has_cache = (TextView) convertView.findViewById(R.id.tv_has_cache);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.tv_chapterName.setText(chapters.get(position).getTitle());
        holder.tv_chapterName.setEnabled(selection == position);
        if (sharedPreferences.getBoolean(chapters.get(position).getLink(), false)) {
            holder.tv_has_cache.setVisibility(View.VISIBLE);
        } else {
            holder.tv_has_cache.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_chapterName, tv_has_cache;
    }
}
