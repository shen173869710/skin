package com.embed.skin.presenter;


import com.embed.skin.view.BaseView;

public interface Presenter<V extends BaseView> {
    void attachView(V mvpView);
    void detachView();
}
