package com.embed.skin.view;


import com.embed.skin.model.respone.BaseRespone;

/**
 * 上传抽象的接口
 */

public interface IPreviewView extends BaseView{
    /***上传成功**/
    void updateSuccess(BaseRespone respone);
    /***上传失败**/
    void updateFail(Throwable error, String msg);
}
