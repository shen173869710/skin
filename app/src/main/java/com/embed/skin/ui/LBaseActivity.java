package com.embed.skin.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.ActivityHelper;
import com.embed.skin.view.BaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;


public abstract class LBaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {

    public Activity mContext;
    protected T mPresenter;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setLayout());
        EventBus.getDefault().register(this);
        mContext = this;
        createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        init();
        setListener();

    }

    /**
     * 设置layout
     *
     * @return
     */
    protected abstract int setLayout();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    /**
     * 初始化Presenter
     */
    public abstract void createPresenter();



    public void startActivity(Class cla) {
        startActivity(new Intent(this, cla));
    }

    @Override
    public void showDialog() {
        if (mDialog == null) {
            mDialog = ActivityHelper.createLoadingDialog(this);
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void back(View view) {
        finish();
    }

    @Override
    public LifecycleTransformer bindLifecycle() {
        LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
        return objectLifecycleTransformer;
    }
}
