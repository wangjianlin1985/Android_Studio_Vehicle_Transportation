package com.mobileclient.domain;

import java.io.Serializable;

public class CarModel implements Serializable {
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