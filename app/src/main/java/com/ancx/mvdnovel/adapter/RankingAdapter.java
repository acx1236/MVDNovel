package com.ancx.mvdnovel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.Ranking;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class RankingAdapter extends BaseAdapter {

    private List<Ranking> mData;
    private int male, female;

    public RankingAdapter(List<Ranking> mData, int male, int female) {
        this.mData = mData;
        this.male = male;
        this.female = female;
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_ranking_list, null);
            holder.tv_sex = (TextView) convertView.findViewById(R.id.tv_sex);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        if (position == 0) {
            holder.tv_sex.setVisibility(View.VISIBLE);
            holder.tv_sex.setText("男生");
        } else if (position == male) {
            holder.tv_sex.setVisibility(View.VISIBLE);
            holder.tv_sex.setText("女生");
        } else {
            holder.tv_sex.setVisibility(View.GONE);
        }
        holder.tv_title.setText(mData.get(position).getTitle());
        return convertView;
    }

    private class ViewHolder {
        TextView tv_sex, tv_title;
    }

}
