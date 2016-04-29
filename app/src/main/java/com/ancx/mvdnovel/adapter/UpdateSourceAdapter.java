package com.ancx.mvdnovel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.Source;

import java.util.List;

/**
 * Created by Ancx on 2016/4/29.
 */
public class UpdateSourceAdapter extends BaseAdapter {

    private List<Source> sources;
    private String currentSourceId;

    public UpdateSourceAdapter(List<Source> sources, String currentSourceId) {
        this.sources = sources;
        this.currentSourceId = currentSourceId;
    }

    @Override
    public int getCount() {
        return sources.size();
    }

    @Override
    public Object getItem(int position) {
        return sources.get(position);
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_update_source, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(sources.get(position).getName());
        if (sources.get(position).get_id().equals(currentSourceId))
            holder.tv_title.setEnabled(true);
        else
            holder.tv_title.setEnabled(false);
        holder.tv_content.setText(sources.get(position).getUpdated() + "：" + sources.get(position).getLastChapter());
        return convertView;
    }

    private class ViewHolder {
        TextView tv_title, tv_content;
    }
}
