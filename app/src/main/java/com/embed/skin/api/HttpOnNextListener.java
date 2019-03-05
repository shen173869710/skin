package com.embed.skin.api;


import com.embed.skin.model.respone.BaseRespone;

/**
 * 成功回调处理
 * Created by WZG on 2016/7/16.
 */
public interface HttpOnNextListener {

    /**
     * 成功后回调方法
     *
     * @param resulte
     * @param mothead
     */
    void onNext(String resulte, BaseRespone mothead);

    /**
     * 失败
     * 失败或者错误方法
     * 自定义异常处理
     *
     */
    void onError();


}
