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
    private int chaptersCount;

    public PresenterReadBook(ReadBookView readBookView) {
        this.readBookView = readBookView;
        modelBookDirectory.setOnBookDirectoryListener(this);
        modelReadBook.setOnReadBookListener(this);
        dirIsCreated = modelReadBook.createDir(readBookView.getId());
        readCount = DatabaseManager.getReadCount(readBookView.getId());
        readPage = DatabaseManager.getReadPage(readBookView.getId());
        chaptersCount = DatabaseManager.getChaptersCount(readBookView.getId());
    }

    private List<Chapter> chapters;

    public void getBookText() {
        modelBookDirectory.getSource(readBookView.getId());
    }

    @Override
    public void setSource(String _id, List<Source> sources) {
        modelBookDirectory.getDirectory(_id, sources.get(0).get_id());
        DatabaseManager.updateSourceId(readBookView.getId(), sources.get(0).get_id());
        sharedPreferences = NovelApp.getInstance().getSharedPreferences(_id + sources.get(0).get_id(), Activity.MODE_PRIVATE);
    }

    @Override
    public void noData() {
        readBookView.noData();
    }

    @Override
    public void setDirectory(List<Chapter> chapters) {
        if (sharedPreferences == null)
            sharedPreferences = NovelApp.getInstance().getSharedPreferences(readBookView.getId() + DatabaseManager.getSourceId(readBookView.getId()), Activity.MODE_PRIVATE);
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
    private int readCount, readPage;
    private boolean isFirstOpend = true;

    private void getBody() {
        link = chapters.get(readCount).getLink();
        title = chapters.get(readCount).getTitle();
        if (sharedPreferences.getBoolean(link, false)) {
            String saveNovelPath = MemoryUtil.getSaveNovelPath(readBookView.getId(), title);
            if (saveNovelPath != null)
                try {
                    String text = ReaderUtil.getString(new FileInputStream(saveNovelPath));
                    readBookView.setHint(title, readCount, chaptersCount + "");
                    if (isFirstOpend) {
                        readBookView.setReadPage(readPage);
                        isFirstOpend = false;
                    }
                    readBookView.setText(text);
                    readBookView.loadComplete();
                    for (int i = 1; i <= 5; i++) {
                        if (readCount + i >= chaptersCount)
                            break;
                        modelReadBook.cacheChacpter(readBookView.getId(), chapters.get(readCount + i), sharedPreferences);
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
        readBookView.setHint(title, readCount, chaptersCount + "");
        readBookView.setText(chapterBody.getBody());
        readBookView.loadComplete();
        if (dirIsCreated)
            modelReadBook.cacheText(readBookView.getId(), title, chapterBody.getBody(), sharedPreferences, link);
    }

    @Override
    public void onBodyFailed() {
        readBookView.errorNet();
    }

    public void nextChapter() {
        if (readCount == chaptersCount - 1) {
            readBookView.readComplete();
            MsgUtil.ToastShort("亲，您已经读完啦!");
            return;
        }
        readCount++;
        DatabaseManager.updateRead(readBookView.getId(), readCount, 1);
        getBody();
    }

    public void preChapter() {
        if (readCount == 0) {
            MsgUtil.ToastShort("亲，前面没有内容啦!");
            return;
        }
        readCount--;
        DatabaseManager.updateRead(readBookView.getId(), readCount, 1);
        getBody();
    }

    public void updateRecord(int currentPage) {
        DatabaseManager.updateRead(readBookView.getId(), readCount, currentPage);
    }
}
