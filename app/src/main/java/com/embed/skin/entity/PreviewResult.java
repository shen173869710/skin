package com.embed.skin.entity;

import java.util.List;

public class PreviewResult {
    private float score;
    private ResultMetrics metrics;
    private List<ResultFeature>features;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public ResultMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(ResultMetrics metrics) {
        this.metrics = metrics;
    }

    public List<ResultFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<ResultFeature> features) {
        this.features = features;
    }
}
