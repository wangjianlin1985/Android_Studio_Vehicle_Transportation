package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CarModel {
    /*车型id*/
    private int modelId;
    public int getModelId() {
        return modelId;
    }
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    /*车型名称*/
    private String modelName;
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}