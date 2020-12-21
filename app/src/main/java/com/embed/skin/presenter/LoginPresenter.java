package com.embed.skin.presenter;

import android.os.Build;

import com.embed.skin.api.HttpManager;
import com.embed.skin.model.request.LoginRequest;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.model.respone.LoginRespone;
import com.embed.skin.view.ILoginView;

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
                if(respone != null && respone.getResult() != null){
                    LoginRespone loginRespone = (LoginRespone) respone.getResult();

                }
            }
            @Override
            public void onError(Throwable error, String msg) {
                getBaseView().loginFail(error,msg);
            }
        });
    }



}
