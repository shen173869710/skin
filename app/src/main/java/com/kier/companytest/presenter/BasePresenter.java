package com.kier.companytest.presenter;

import com.kier.companytest.api.ApiService;
import com.kier.companytest.api.ApiUtil;
import com.kier.companytest.api.HttpManager;
import com.kier.companytest.view.BaseView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<T extends BaseView> implements Presenter<T> {


    private T mBaseView;
    private ApiService apiService;
    private HttpManager httpManager;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public void attachView(T baseView) {
        mBaseView = baseView;
        apiService = ApiUtil.createApiService();
        httpManager = new HttpManager();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void detachView() {
        mBaseView = null;
        mCompositeDisposable.dispose();
    }


    public T getBaseView() {
        return mBaseView;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public CompositeDisposable getmCompositeDisposable() {
        return mCompositeDisposable;
    }

    public HttpManager getHttpManager() {
        return httpManager;
    }

    public void doHttpTask(Observable observable, final HttpManager.OnResultListener onResultListener) {
        httpManager.doHttpTask(getBaseView(), observable,mCompositeDisposable,onResultListener);
    }

    public void doHttpTaskWihtDialog(Observable observable, final HttpManager.OnResultListener onResultListener) {
        httpManager.doHttpTaskWithDialog(getBaseView(), observable,mCompositeDisposable,onResultListener);
    }
}

