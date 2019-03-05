package com.embed.skin.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.embed.skin.R;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.LoginPresenter;
import com.embed.skin.util.ShareUtil;
import com.embed.skin.view.ILoginView;


public class LoginActivity extends LBaseActivity <LoginPresenter> implements ILoginView {

    private EditText login_input_username;
    private Button login_del_username;

    private EditText login_input_password;
    private Button login_passwd_eye;
    private Button login_del_passwd;
    private Button login_btn;

    private TextView login_setting;
    private TextView login_name;
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
        login_name = findViewById(R.id.login_name);
        login_setting = findViewById(R.id.login_setting);


    }


    @Override
    protected void onResume() {
        super.onResume();
        String name = ShareUtil.getName(this);
        if (!TextUtils.isEmpty(name)) {
            login_name.setText(name);
        }
    }

    @Override
    protected void setListener() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.doLogin();
            }
        });

        login_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SettingActivity.class));
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
