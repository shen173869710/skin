package com.embed.skin.ui;

import android.content.Intent;
import android.view.View;

import com.embed.skin.R;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.LoginPresenter;
import com.embed.skin.view.ILoginView;


public class ResultActivity extends LBaseActivity <LoginPresenter> implements ILoginView {


    @Override
    protected int setLayout() {
        return R.layout.activity_result;
    }

    @Override
    protected void init() {


    }



    @Override
    protected void setListener() {
        findViewById(R.id.result_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, RecomActivity.class));
            }
        });
    }

    @Override
    public void createPresenter() {
        mPresenter = new LoginPresenter();
    }


    @Override
    public void loginSuccess(BaseRespone respone) {

    }

    @Override
    public void loginFail(Throwable error, String msg) {
        startActivity(new Intent(ResultActivity.this, HomeActivity.class));
        finish();
    }

    public void rightClick(View view) {
        startActivity(new Intent(ResultActivity.this, HistoryListActivity.class));
    }

}
