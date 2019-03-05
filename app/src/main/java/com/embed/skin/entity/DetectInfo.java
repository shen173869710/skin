package com.embed.skin.entity;

/**
 * Created by Administrator on 2019/3/5.
 */

public class DetectInfo {
    public int postion;
    public String waterValue;
    public String oilValue;
    public String elasticValue;
    public boolean isSel;

    public DetectInfo(int postion, String waterValue, String oilValue, String elasticValue, boolean isSel) {
        this.postion = postion;
        this.waterValue = waterValue;
        this.oilValue = oilValue;
        this.elasticValue = elasticValue;
        this.isSel = isSel;
    }
}
