package com.kier.companytest.presenter;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import com.kier.companytest.api.HttpManager;
import com.kier.companytest.model.request.LoginRequest;
import com.kier.companytest.model.respone.BaseRespone;
import com.kier.companytest.model.respone.LoginRespone;
import com.kier.companytest.view.ILoginView;

/**

 */

public class LoginPresenter extends BasePresenter<ILoginView>{

    /**登录请求**/
    public void doLogin() {
       final LoginRequest request = new LoginRequest();

        request.buildModel = Build.MODEL;
        request.sdkInt = String.valueOf(Build.VERSION.SDK_INT);
        request.currentTime = String.valueOf(System.currentTimeMillis());
        doHttpTaskWihtDialog(getApiService().login(request.toJson()), new HttpManager.OnResultListener() {
            @Override
            public void onSuccess(BaseRespone respone) {
                if(respone != null && respone.data != null){
                    LoginRespone loginRespone = (LoginRespone) respone.data;

                }
            }
            @Override
            public void onError(Throwable error, String msg) {
                getBaseView().loginFail(error,msg);
            }
        });
    }



}
