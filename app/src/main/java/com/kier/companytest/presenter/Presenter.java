package com.kier.companytest.presenter;


import com.kier.companytest.view.BaseView;

public interface Presenter<V extends BaseView> {
    void attachView(V mvpView);
    void detachView();
}
