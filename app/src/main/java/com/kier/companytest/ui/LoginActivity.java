package com.kier.companytest.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kier.companytest.R;
import com.kier.companytest.model.respone.BaseRespone;
import com.kier.companytest.presenter.LoginPresenter;
import com.kier.companytest.view.ILoginView;


public class LoginActivity extends LBaseActivity <LoginPresenter> implements ILoginView {

    private EditText login_input_username;
    private Button login_del_username;

    private EditText login_input_password;
    private Button login_passwd_eye;
    private Button login_del_passwd;
    private Button login_btn;
    @Override
    protected int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        login_input_username = findViewById(R.id.login_input_username);
        login_del_username = findViewById(R.id.login_del_username);
        login_input_password = findViewById(R.id.login_input_password);
        login_passwd_eye = findViewById(R.id.login_passwd_eye);
        login_del_passwd = findViewById(R.id.login_del_passwd);
        login_btn = findViewById(R.id.login_btn);
    }

    @Override
    protected void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doLogin();
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
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}
