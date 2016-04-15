package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.BookDetail;
import com.ancx.mvdnovel.listener.OnBookDetailListener;
import com.ancx.mvdnovel.model.ModelBookDetail;
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

    @Override
    public void setDetail(BookDetail bookDetail) {
        bookDetailView.showCover(bookDetail.getCover());
        bookDetailView.showTitle(bookDetail.getTitle());
        StringBuilder sb = new StringBuilder("bookDetail.getCat() - ");
        if (bookDetail.isIsSerial())
            sb.append("连载");
        else
            sb.append("完本");
        bookDetailView.showCat_Serial(sb.toString());
        bookDetailView.showAuthor(bookDetail.getAuthor());
        bookDetailView.showUpdate(bookDetail.getUpdated());
    }
}
