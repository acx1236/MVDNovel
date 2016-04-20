package com.ancx.mvdnovel.presenter;

import android.text.TextUtils;

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

    public PresenterBookDirectory(BookDirectoryView bookDirectoryView) {
        this.bookDirectoryView = bookDirectoryView;
    }

    private ModelBookDirectory modelBookDirectory = new ModelBookDirectory();
    private DatabaseManager databaseManager = new DatabaseManager();

    public void getDirectory() {
        modelBookDirectory.setOnBookDirectoryListener(this);
        String sourceId = databaseManager.getSourceId(bookDirectoryView.getId());
        if (TextUtils.isEmpty(sourceId))
            // 获取小说源
            modelBookDirectory.getSource(bookDirectoryView.getId());
        else
            modelBookDirectory.getDirectory(sourceId);
    }

    @Override
    public void setSource(List<Source> sources) {
        modelBookDirectory.getDirectory(sources.get(0).get_id());
        databaseManager.updateSourceId(bookDirectoryView.getId(), sources.get(0).get_id());
    }

    @Override
    public void setDirectory(List<Chapter> chapters) {
        bookDirectoryView.setDirectory(chapters);
    }

    @Override
    public void onFailed() {
        bookDirectoryView.errorNet();
    }
}
