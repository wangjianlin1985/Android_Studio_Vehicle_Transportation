package com.mobileclient.domain;

import java.io.Serializable;

public class CarModel implements Serializable {
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