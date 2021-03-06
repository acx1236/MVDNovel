package com.ancx.mvdnovel.presenter;

import android.content.Intent;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.activity.BookDirectoryActivity;
import com.ancx.mvdnovel.activity.ReadBookActivity;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.listener.OnBookDetailListener;
import com.ancx.mvdnovel.model.ModelBookDetail;
import com.ancx.mvdnovel.service.CacheBookService;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.view.BookDetailView;

/**
 * Created by Ancx on 2016/4/15.
 */
public class PresenterBookDetail implements OnBookDetailListener {

    private BookDetailView bookDetailView;

    public PresenterBookDetail(BookDetailView bookDetailView) {
        this.bookDetailView = bookDetailView;
    }

    private ModelBookDetail modelBookDetail = new ModelBookDetail();

    public void getDetail() {
        modelBookDetail.setOnBookDetailListener(this);
        modelBookDetail.getDetail(bookDetailView.getId());
    }

    private BookDetail bookDetail;

    @Override
    public void setDetail(BookDetail bookDetail) {
        this.bookDetail = bookDetail;
        bookDetailView.showCover(bookDetail.getCover());
        bookDetailView.showTitle(bookDetail.getTitle());
        StringBuilder sb = new StringBuilder(bookDetail.getCat() + " - ");
        if (bookDetail.isIsSerial())
            sb.append("连载");
        else
            sb.append("完本");
        bookDetailView.showCat_Serial(sb.toString());
        bookDetailView.showAuthor(bookDetail.getAuthor() + "  著");
        bookDetailView.showUpdate(bookDetail.getUpdated() + "  更新");
        bookDetailView.showLastChapter(bookDetail.getLastChapter());
        bookDetailView.showLongIntro(bookDetail.getLongIntro());
        if (NovelApp.bookIds.contains(bookDetail.get_id()))
            bookDetailView.setAddText("移除此书");
        else
            bookDetailView.setAddText("开始追书");
    }

    @Override
    public void onFailed() {
        bookDetailView.errorNet();
    }

    public void operateBook() {
        if (NovelApp.bookIds.contains(bookDetail.get_id())) {
            // 书在书架里了
            int deleteBook = DatabaseManager.deleteBook(bookDetail.get_id());
            if (deleteBook == 1) {
                MsgUtil.ToastShort("小说已经从书架移除!");
                bookDetailView.setAddText("开始追书");
            } else {
                MsgUtil.ToastShort("移除失败!");
            }
        } else {
            // 书没在书架里
            int addBook = DatabaseManager.addBook(bookDetail);
            if (addBook == 1) {
                MsgUtil.ToastShort("小说已经添加到书架!");
                bookDetailView.setAddText("移除此书");
            } else {
                MsgUtil.ToastShort("添加失败!");
            }
        }
    }

    public void cache() {
        Intent cacheService = new Intent(NovelApp.getInstance(), CacheBookService.class);
        cacheService.putExtra("_id", bookDetail.get_id());
        if (!NovelApp.bookIds.contains(bookDetail.get_id())) {
            // 书没在书架里
            int addBook = DatabaseManager.addBook(bookDetail);
            if (addBook == 1) {
                MsgUtil.ToastShort("小说已经添加到书架!");
                bookDetailView.setAddText("移除此书");
                bookDetailView.startCache(cacheService);
            } else {
                // 添加失败
                MsgUtil.ToastShort("小说添加失败，目前无法缓存！");
            }
            return;
        }
        // 小说在书架里，直接缓存
        bookDetailView.startCache(cacheService);
    }

    public void openDirectory(boolean isEnd) {
        Intent intent = new Intent(NovelApp.getInstance(), BookDirectoryActivity.class);
        intent.putExtra("title", bookDetail.getTitle());
        intent.putExtra("_id", bookDetail.get_id());
        intent.putExtra("isEnd", isEnd);
        bookDetailView.openDirectory(intent);
    }

    public void nowRead() {
        if (!NovelApp.bookIds.contains(bookDetail.get_id())) {
            DatabaseManager.addBook(bookDetail);
            MsgUtil.ToastShort("小说已经添加到书架!");
            bookDetailView.setAddText("移除此书");
        }
        Intent intent = new Intent(NovelApp.getInstance(), ReadBookActivity.class);
        intent.putExtra("_id", bookDetailView.getId());
        bookDetailView.startIntent(intent);
    }
}
