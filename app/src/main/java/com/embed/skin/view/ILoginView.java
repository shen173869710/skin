package com.embed.skin.view;


import com.embed.skin.model.respone.BaseRespone;

/**
 * Created by Administrator on 2017/6/26.
 * 登录页面抽象的接口
 */

public interface ILoginView extends BaseView{
    /***登录成功**/
    void loginSuccess(BaseRespone respone);
    /***登录失败**/
    void loginFail(Throwable error, String msg);
}
