package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.Books;
import com.ancx.mvdnovel.listener.OnSearchBooksListener;
import com.ancx.mvdnovel.model.ModelSearchBooks;
import com.ancx.mvdnovel.view.SearchBooksView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/15.
 */
public class PresenterSearchBooks implements OnSearchBooksListener {

    private SearchBooksView searchBooksView;

    public PresenterSearchBooks(SearchBooksView searchBooksView) {
        this.searchBooksView = searchBooksView;
    }

    private ModelSearchBooks modelSearchBooks = new ModelSearchBooks();

    public void search() {
        modelSearchBooks.setOnSearchBooksListener(this);
        modelSearchBooks.searchBooks(searchBooksView.getBookName());
    }

    @Override
    public void setBooks(List<Books> mData) {
        searchBooksView.setResult(mData);
    }

    @Override
    public void onFailed() {
        searchBooksView.errorNet();
    }
}
