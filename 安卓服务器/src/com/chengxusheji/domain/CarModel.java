package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CarModel {
    /*����id*/
    private int modelId;
    public int getModelId() {
        return modelId;
    }
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    /*��������*/
    private String modelName;
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}