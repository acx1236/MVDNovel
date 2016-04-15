package com.ancx.mvdnovel.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索历史列表的Adapter
 * Created by Ancx on 2016/4/15.
 */
public class HistoryAdapter extends BaseAdapter {

    private List<String> historys;

    public HistoryAdapter(List<String> historys) {
        if (historys == null)
            this.historys = new ArrayList<>();
        else
            this.historys = historys;
    }

    public void notifyByData(List<String> historys) {
        if (historys == null)
            this.historys = new ArrayList<>();
        else
            this.historys = historys;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return historys.size();
    }

    @Override
    public Object getItem(int position) {
        return historys.get(position);
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_search_history, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(historys.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView tv_name;
    }
}
