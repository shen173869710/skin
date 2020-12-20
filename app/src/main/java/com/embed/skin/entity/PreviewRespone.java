package com.embed.skin.entity;

public class PreviewRespone {
    private boolean code;
    private String msg;
    private PreviewResult result;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PreviewResult getResult() {
        return result;
    }

    public void setResult(PreviewResult result) {
        this.result = result;
    }
}
