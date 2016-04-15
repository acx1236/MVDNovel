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
 * 关键字列表的Adapter
 * Created by Ancx on 2016/4/15.
 */
public class KeyWordsAdapter extends BaseAdapter {

    private List<String> keyWords;

    public KeyWordsAdapter(List<String> keyWords) {
        if (keyWords == null)
            this.keyWords = new ArrayList<>();
        else
            this.keyWords = keyWords;
    }

    public void notifyByData(List<String> keyWords) {
        if (keyWords == null)
            this.keyWords = new ArrayList<>();
        else
            this.keyWords = keyWords;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return keyWords.size();
    }

    @Override
    public Object getItem(int position) {
        return keyWords.get(position);
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
            convertView = View.inflate(NovelApp.getInstance(), R.layout.item_key_words, null);
            holder.tv_keywords = (TextView) convertView.findViewById(R.id.tv_keywords);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_keywords.setText(keyWords.get(position));
        return convertView;
    }

    private class ViewHolder {
        TextView tv_keywords;
    }
}
