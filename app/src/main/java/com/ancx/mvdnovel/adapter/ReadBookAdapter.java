package com.ancx.mvdnovel.adapter;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.R;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.listener.OnItemClickListener;
import com.ancx.mvdnovel.listener.OnMoreOperationListener;
import com.ancx.mvdnovel.util.DisplayUtil;
import com.ancx.mvdnovel.util.ImageLoader;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class ReadBookAdapter extends RecyclerView.Adapter {

    private List<BookDetail> books;

    public BookDetail getBook(int position) {
        return books.get(position);
    }

    public ReadBookAdapter(List<BookDetail> books) {
        this.books = books;
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        }

        RecyclerView.LayoutParams params;
        ImageView iv_cover, iv_more, iv_read_circle;
        TextView tv_title, tv_author, tv_lastChapter;
        ProgressBar pb_read;
        PopupWindow moreWindow;

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.item_my_read_book, parent, false);
        ViewHolder holder = new ViewHolder(convertView);
        holder.iv_cover = (ImageView) convertView.findViewById(R.id.iv_cover);
        holder.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
        holder.iv_read_circle = (ImageView) convertView.findViewById(R.id.iv_read_circle);
        holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
        holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
        holder.tv_lastChapter = (TextView) convertView.findViewById(R.id.tv_lastChapter);
        holder.pb_read = (ProgressBar) convertView.findViewById(R.id.pb_read);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        if (position == books.size() - 1)
            viewHolder.params.setMargins((int) DisplayUtil.dip2px(8), (int) DisplayUtil.dip2px(8),
                    (int) DisplayUtil.dip2px(8), (int) DisplayUtil.dip2px(30));
        else
            viewHolder.params.setMargins((int) DisplayUtil.dip2px(8), (int) DisplayUtil.dip2px(8),
                    (int) DisplayUtil.dip2px(8), 0);
        if (books.get(position).isUpdate())
            viewHolder.iv_read_circle.setVisibility(View.VISIBLE);
        else
            viewHolder.iv_read_circle.setVisibility(View.GONE);
        ImageLoader.display(books.get(position).getCover(), viewHolder.iv_cover, R.mipmap.icon_nopic, R.mipmap.icon_nopic, 120, 150);
        viewHolder.tv_title.setText(books.get(position).getTitle());
        viewHolder.tv_author.setText(books.get(position).getAuthor());
        viewHolder.tv_lastChapter.setText(books.get(position).getUpdated() + " : " + books.get(position).getLastChapter());
        viewHolder.pb_read.setMax(books.get(position).getChaptersCount());
        viewHolder.pb_read.setProgress(books.get(position).getReadCount());
        viewHolder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.moreWindow == null) {
                    View contentView = LayoutInflater.from(NovelApp.getInstance()).inflate(R.layout.layout_more_operation, null);
                    TextView tv_directory = (TextView) contentView.findViewById(R.id.tv_directory);
                    tv_directory.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onMoreOperationListener != null)
                                onMoreOperationListener.onBookDirectory(books.get(position).get_id(), books.get(position).getTitle(), position);
                            viewHolder.moreWindow.dismiss();
                        }
                    });
                    TextView tv_cache = (TextView) contentView.findViewById(R.id.tv_cache);
                    tv_cache.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onMoreOperationListener != null)
                                onMoreOperationListener.onCacheBook(books.get(position));
                            viewHolder.moreWindow.dismiss();
                        }
                    });
                    TextView tv_remove = (TextView) contentView.findViewById(R.id.tv_remove);
                    tv_remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onMoreOperationListener != null)
                                onMoreOperationListener.onRemoveBook(books.get(position).get_id(), position);
                            viewHolder.moreWindow.dismiss();
                        }
                    });
                    viewHolder.moreWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    viewHolder.moreWindow.setOutsideTouchable(true);
                    viewHolder.moreWindow.setBackgroundDrawable(new BitmapDrawable());
                }
                viewHolder.moreWindow.showAsDropDown(viewHolder.iv_more, (int) -DisplayUtil.dip2px(110), 0);
            }
        });
    }

    private OnMoreOperationListener onMoreOperationListener;

    public void setOnMoreOperationListener(OnMoreOperationListener onMoreOperationListener) {
        this.onMoreOperationListener = onMoreOperationListener;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
