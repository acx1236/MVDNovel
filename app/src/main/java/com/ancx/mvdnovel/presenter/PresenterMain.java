package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.NovelApp;
import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.entity.UpdateBook;
import com.ancx.mvdnovel.listener.OnUpdateBookListener;
import com.ancx.mvdnovel.model.ModelMain;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.MsgUtil;
import com.ancx.mvdnovel.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterMain implements OnUpdateBookListener {

    private MainView mainView;

    public PresenterMain(MainView mainView) {
        this.mainView = mainView;
    }

    private List<BookDetail> books = new ArrayList<>();

    public void getMyBooks() {
        books.clear();
        books.addAll(DatabaseManager.getBooks());
        mainView.showBook(books);
    }

    private ModelMain modelMain = new ModelMain();

    public void updateBooks() {
        if (NovelApp.bookIds.size() == 0) {
            mainView.updateComplete(false);
            return;
        }
        modelMain.setOnUpdateBookListener(this);
        modelMain.updateBooks();
    }

    public void removeBook(String _id, int position) {
        int deleteBook = DatabaseManager.deleteBook(_id);
        if (deleteBook == 1) {
            MsgUtil.ToastShort("小说移除成功!");
            books.remove(position);
            mainView.updateComplete(true);
        }
    }

    @Override
    public void onUpdata(List<UpdateBook> updateBooks) {
        int updateCount = 0;
        for (int i = 0; i < books.size(); i++) {
            if (updateBooks.get(i).getChaptersCount() > books.get(i).getChaptersCount()) {
                // 有更新
                books.get(i).setUpdate(true);
                books.get(i).setUpdated(updateBooks.get(i).getUpdated());
                books.get(i).setChaptersCount(updateBooks.get(i).getChaptersCount());
                books.get(i).setLastChapter(updateBooks.get(i).getLastChapter());
                DatabaseManager.updateBook(books.get(i).get_id(), updateBooks.get(i).getUpdated(),
                        updateBooks.get(i).getChaptersCount(), updateBooks.get(i).getLastChapter());
                updateCount++;
            }
        }
        if (updateCount == 0) {
            MsgUtil.ToastShort("小说暂无更新!");
            mainView.updateComplete(false);
        } else {
            MsgUtil.ToastShort("您有" + updateCount + "本小说更新!");
            mainView.updateComplete(true);
        }
    }
}
