package com.ancx.mvdnovel.presenter;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.adapter.BookDirectoryAdapter;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.model.ModelBookDirectory;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.view.BookDirectoryView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class PresenterBookDirectory implements OnBookDirectoryListener {

    private BookDirectoryView bookDirectoryView;
    private BookDirectoryAdapter bookDirectoryAdapter;

    public PresenterBookDirectory(BookDirectoryView bookDirectoryView) {
        this.bookDirectoryView = bookDirectoryView;
    }

    private ModelBookDirectory modelBookDirectory = new ModelBookDirectory();

    public void getDirectory() {
        // 设置监听
        modelBookDirectory.setOnBookDirectoryListener(this);
        // 获取源
        modelBookDirectory.getSource(bookDirectoryView.getId());
    }

    private SharedPreferences sharedPreferences;

    @Override
    public void setSource(String _id, List<Source> sources) {
        sharedPreferences = NovelApp.getInstance().getSharedPreferences(_id + sources.get(0).get_id(), Activity.MODE_PRIVATE);
        // 从网路中获取到源数据，开始获取小说章节信息
        modelBookDirectory.getDirectory(_id, sources.get(0).get_id());
        // 替换数据库中的小说源id为选择的源id，方便下次缓存
        DatabaseManager.updateSourceId(bookDirectoryView.getId(), sources.get(0).get_id());
    }

    @Override
    public void setDirectory(List<Chapter> chapters) {
        if (sharedPreferences == null) {
            String sourceId = DatabaseManager.getSourceId(bookDirectoryView.getId());
            sharedPreferences = NovelApp.getInstance().getSharedPreferences(bookDirectoryView.getId() + sourceId, Activity.MODE_PRIVATE);
        }
        int selection = bookDirectoryView.getSelection();
        if (bookDirectoryView.isEnd())
            selection = chapters.size() - 1;
        if (bookDirectoryAdapter == null) {
            bookDirectoryAdapter = new BookDirectoryAdapter(chapters, selection, sharedPreferences);
            bookDirectoryView.setAdapter(bookDirectoryAdapter);
        } else {
            bookDirectoryAdapter.notifyDataSetChanged();
        }
        bookDirectoryView.setSelection(selection);
        bookDirectoryView.loadComplete();
    }

    @Override
    public void noData() {
        bookDirectoryView.noData();
    }

    @Override
    public void onFailed() {
        bookDirectoryView.errorNet();
    }
}
