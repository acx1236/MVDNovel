package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.ClassBook;
import com.ancx.mvdnovel.listener.OnClassBookListListener;
import com.ancx.mvdnovel.model.ModelClassTabFragment;
import com.ancx.mvdnovel.view.ClassTabFragmentView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterClassTabFragment implements OnClassBookListListener {

    private ClassTabFragmentView classTabFragmentView;

    public PresenterClassTabFragment(ClassTabFragmentView classTabFragmentView) {
        this.classTabFragmentView = classTabFragmentView;
    }

    private ModelClassTabFragment modelClassTabFragment = new ModelClassTabFragment();

    public void getBookList() {
        modelClassTabFragment.setOnClassBookListListener(this);
        modelClassTabFragment.getBookList(classTabFragmentView.getGender(), classTabFragmentView.getType(), classTabFragmentView.getMajor(), classTabFragmentView.getStart());
    }

    private int addCount = -1;

    @Override
    public void onClassBookList(List<ClassBook> books) {
        if (addCount == -1) {
            addCount = books.size();
            classTabFragmentView.setAddCount(addCount);
        }
        if ("0".equals(classTabFragmentView.getStart()))
            classTabFragmentView.getData().clear();
        classTabFragmentView.getData().addAll(books);
        if (books.size() < addCount)
            classTabFragmentView.setBookList(classTabFragmentView.getData().size());
        else
            classTabFragmentView.setBookList(classTabFragmentView.getData().size() + 65);
    }

    @Override
    public void onFailed() {
        classTabFragmentView.errorNet();
    }
}
