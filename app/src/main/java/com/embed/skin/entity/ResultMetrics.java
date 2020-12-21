package com.embed.skin.entity;

import java.io.Serializable;

public class ResultMetrics implements Serializable {
    private int rank;
    private String faceStatus;
    private int age;
    private int skinType;
    private int skinColor;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFaceStatus() {
        return faceStatus;
    }

    public void setFaceStatus(String faceStatus) {
        this.faceStatus = faceStatus;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSkinType() {
        return skinType;
    }

    public void setSkinType(int skinType) {
        this.skinType = skinType;
    }

    public int getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(int skinColor) {
        this.skinColor = skinColor;
    }
}
