package com.embed.skin.model.respone;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/26.
 */

public class BaseRespone<T> implements Serializable {
    /**
     * 请求返回的参数 r = 0 正常
     */

    private String msg;
    private String ret;
    private T result;
    private ArrayList<T> datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
    }

    public boolean isOk() {
        if (TextUtils.isEmpty(ret)) {
            return false;
        }
        if (ret.toLowerCase().equals("true")) {
            return true;
        }
        return false;
    }
}
