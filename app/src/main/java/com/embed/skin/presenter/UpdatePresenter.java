package com.embed.skin.presenter;

import android.os.Build;

import com.embed.skin.api.HttpManager;
import com.embed.skin.model.request.LoginRequest;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.model.respone.LoginRespone;
import com.embed.skin.view.ILoginView;
import com.embed.skin.view.IUpdateView;

/**

 */

public class UpdatePresenter extends BasePresenter<IUpdateView>{

    /**上传图片**/
    public void doUpdate() {
       final LoginRequest request = new LoginRequest();

        request.buildModel = Build.MODEL;
        request.sdkInt = String.valueOf(Build.VERSION.SDK_INT);
        request.currentTime = String.valueOf(System.currentTimeMillis());
        doHttpTaskWihtDialog(getApiService().login(request.toJson()), new HttpManager.OnResultListener() {
            @Override
            public void onSuccess(BaseRespone respone) {
                if(respone != null && respone.getResult() != null){
                    getBaseView().updateSuccess(respone);

                }
            }
            @Override
            public void onError(Throwable error, String msg) {
                getBaseView().updateFail(error,msg);
            }
        });
    }



}
