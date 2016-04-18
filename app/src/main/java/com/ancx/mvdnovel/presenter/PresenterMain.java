package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.model.ModelMain;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.view.MainView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterMain {

    private MainView mainView;

    public PresenterMain(MainView mainView) {
        this.mainView = mainView;
    }

    private DatabaseManager databaseManager = new DatabaseManager();

    private List<BookDetail> books;

    public void getMyBooks() {
        books = databaseManager.getBooks();
        mainView.showBook(books);
    }

    private ModelMain modelMain = new ModelMain();

    public void updateBooks() {
        modelMain.updateBooks();
        mainView.updateComplete();
    }
}
