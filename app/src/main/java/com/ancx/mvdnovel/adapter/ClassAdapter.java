package com.ancx.mvdnovel.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.ClassName;
import com.ancx.mvdnovel.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ClassAdapter extends RecyclerView.Adapter {

    private final int TITLEVIEW = 0, CONTENTVIEW = 1;

    private List<ClassName> male, female;

    public ClassAdapter(List<ClassName> male, List<ClassName> female) {
        this.male = male;
        this.female = female;
    }

    @Override
    public int getItemCount() {
        return male.size() + female.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == male.size() + 1)
            return TITLEVIEW;
        else
            return CONTENTVIEW;
    }

    private class TitleViewHolder extends RecyclerView.ViewHolder {

        public TitleViewHolder(View itemView) {
            super(itemView);
        }

        TextView tv_title;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        TextView tv_name, tv_bookCount;

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TITLEVIEW) {
            View titleView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.item_class_titleview, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(titleView);
            titleViewHolder.tv_title = (TextView) titleView.findViewById(R.id.tv_title);
            return titleViewHolder;
        }
        View contentView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.item_class_contentview, parent, false);
        ContentViewHolder contentViewHolder = new ContentViewHolder(contentView);
        contentViewHolder.tv_name = (TextView) contentView.findViewById(R.id.tv_name);
        contentViewHolder.tv_bookCount = (TextView) contentView.findViewById(R.id.tv_bookCount);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            if (position == 0)
                titleViewHolder.tv_title.setText("男生");
            else
                titleViewHolder.tv_title.setText("女生");
            return;
        }
        ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
        if (position <= male.size()) {
            contentViewHolder.tv_name.setText(male.get(position - 1).getName());
            contentViewHolder.tv_bookCount.setText(male.get(position - 1).getBookCount() + "本");
        } else {
            contentViewHolder.tv_name.setText(female.get(position - male.size() - 2).getName());
            contentViewHolder.tv_bookCount.setText(female.get(position - male.size() - 2).getBookCount() + "本");
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == male.size() + 1) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
    }

    public ClassName getClass(int position) {
        if (position <= male.size()) {
            male.get(position - 1).setGender("male");
            return male.get(position - 1);
        } else {
            female.get(position - male.size() - 2).setGender("female");
            return female.get(position - male.size() - 2);
        }
    }
}
