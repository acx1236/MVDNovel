package com.ancx.mvdnovel.presenter;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.ChapterBody;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.listener.OnReadBookListener;
import com.ancx.mvdnovel.model.ModelBookDirectory;
import com.ancx.mvdnovel.model.ModelReadBook;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.MemoryUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.util.ReaderUtil;
import com.ancx.mvdnovel.view.ReadBookView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Ancx on 2016/4/21.
 */
public class PresenterReadBook implements OnBookDirectoryListener, OnReadBookListener {

    private ReadBookView readBookView;
    private SharedPreferences sharedPreferences;
    private ModelBookDirectory modelBookDirectory = new ModelBookDirectory();
    private ModelReadBook modelReadBook = new ModelReadBook();

    private boolean dirIsCreated;

    public PresenterReadBook(ReadBookView readBookView) {
        this.readBookView = readBookView;
        sharedPreferences = NovelApp.getInstance().getSharedPreferences(readBookView.getBook().get_id(), Activity.MODE_PRIVATE);
        modelBookDirectory.setOnBookDirectoryListener(this);
        modelReadBook.setOnReadBookListener(this);
        dirIsCreated = modelReadBook.createDir(readBookView.getBook().get_id());
    }

    private List<Chapter> chapters;

    public void getBookText() {
        modelBookDirectory.getSource(readBookView.getBook().get_id());
    }

    @Override
    public void setSource(String _id, List<Source> sources) {
        modelBookDirectory.getDirectory(_id, sources.get(0).get_id());
        DatabaseManager.updateSourceId(readBookView.getBook().get_id(), sources.get(0).get_id());
    }

    @Override
    public void noData() {

    }

    @Override
    public void setDirectory(List<Chapter> chapters) {
        this.chapters = chapters;
        if (chapters.size() > 0)
            getBody();
        else
            readBookView.noData();
    }

    @Override
    public void onFailed() {
        readBookView.errorNet();
    }

    private String link, title;

    private void getBody() {
        link = chapters.get((readBookView.getBook().getReadCount() - 1)).getLink();
        title = chapters.get((readBookView.getBook().getReadCount() - 1)).getTitle();
        if (sharedPreferences.getBoolean(link, false)) {
            String saveNovelPath = MemoryUtil.getSaveNovelPath(readBookView.getBook().get_id(), title);
            if (saveNovelPath != null)
                try {
                    String text = ReaderUtil.getString(new FileInputStream(saveNovelPath));
                    readBookView.setHint(title, readBookView.getBook().getReadCount() + "", readBookView.getBook().getChaptersCount() + "");
                    readBookView.setText(text);
                    for (int i = 0; i < 5; i++) {
                        modelReadBook.cacheChacpter(readBookView.getBook().get_id(), chapters.get(readBookView.getBook().getReadCount() + i), sharedPreferences);
                    }
                    return;
                } catch (FileNotFoundException e) {
                    MsgUtil.LogException(e);
                    MsgUtil.LogTag("PresenterReadBook -> setDirectory -> FileNotFoundException");
                }
        }
        readBookView.showLoading();
        modelReadBook.getBookBody(link);
    }

    @Override
    public void setBookBody(ChapterBody chapterBody) {
        readBookView.setHint(title, readBookView.getBook().getReadCount() + "", readBookView.getBook().getChaptersCount() + "");
        readBookView.setText(chapterBody.getBody());
        if (dirIsCreated)
            modelReadBook.cacheText(readBookView.getBook().get_id(), title, chapterBody.getBody(), sharedPreferences, link);
    }

    @Override
    public void onBodyFailed() {
        readBookView.errorNet();
    }

    public void nextChapter() {
        if (readBookView.getBook().getReadCount() == readBookView.getBook().getChaptersCount()) {
            MsgUtil.ToastShort("亲，您已经读完啦!");
            return;
        }
        readBookView.getBook().setReadCount(readBookView.getBook().getReadCount() + 1);
        readBookView.getBook().setReadPage(1);
        getBody();
    }

    public void preChapter() {
        if (readBookView.getBook().getReadCount() == 1) {
            MsgUtil.ToastShort("亲，前面没有内容啦!");
            return;
        }
        readBookView.getBook().setReadCount(readBookView.getBook().getReadCount() - 1);
        readBookView.getBook().setReadPage(-1);
        getBody();
    }
}
