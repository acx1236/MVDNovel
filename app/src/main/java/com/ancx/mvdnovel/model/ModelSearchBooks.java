package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.Books;
import com.ancx.mvdnovel.entity.SearchBooksList;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnSearchBooksListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Ancx on 2016/4/15.
 */
public class ModelSearchBooks {

    public void searchBooks(String bookname) {
        try {
            HttpUtil.addRequest("http://api.zhuishushenqi.com/book/fuzzy-search?query=" + URLEncoder.encode(bookname, "UTF-8"),
                    new HttpRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            SearchBooksList booksList = GsonUtil.getObject(response, SearchBooksList.class);
                            if (booksList.isOk()) {
                                if (onSearchBooksListener != null)
                                    if (booksList.getBooks() != null)
                                        onSearchBooksListener.setBooks(booksList.getBooks());
                                    else
                                        onSearchBooksListener.setBooks(new ArrayList<Books>());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            MsgUtil.LogTag("PresenterSearchBooks -> search -> URL编码异常");
            MsgUtil.LogException(e);
        }
    }

    private OnSearchBooksListener onSearchBooksListener;

    public void setOnSearchBooksListener(OnSearchBooksListener onSearchBooksListener) {
        this.onSearchBooksListener = onSearchBooksListener;
    }
}
