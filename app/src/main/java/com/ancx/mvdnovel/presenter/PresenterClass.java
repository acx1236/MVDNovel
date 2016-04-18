package com.ancx.mvdnovel.presenter;

import com.ancx.mvdnovel.entity.ClassName;
import com.ancx.mvdnovel.listener.OnClassListener;
import com.ancx.mvdnovel.model.ModelClass;
import com.ancx.mvdnovel.view.ClassView;

import java.util.List;

/**
 * Created by Ancx on 2016/4/18.
 */
public class PresenterClass implements OnClassListener {

    private ClassView classView;

    public PresenterClass(ClassView classView) {
        this.classView = classView;
    }

    private ModelClass modelClass = new ModelClass();

    public void getClassList() {
        modelClass.setOnClassListener(this);
        modelClass.getClassList();
    }

    @Override
    public void onClassName(List<ClassName> male, List<ClassName> female) {
        classView.setClass(male, female);
    }
}
