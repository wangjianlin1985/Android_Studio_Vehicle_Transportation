package com.mobileclient.domain;

import java.io.Serializable;

public class Car implements Serializable {
    /*车辆id*/
    private int carId;
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }

    /*车牌*/
    private String carNo;
    public String getCarNo() {
        return carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    /*车型*/
    private int carModel;
    public int getCarModel() {
        return carModel;
    }
    public void setCarModel(int carModel) {
        this.carModel = carModel;
    }

    /*品牌*/
    private String pinpai;
    public String getPinpai() {
        return pinpai;
    }
    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    /*油型*/
    private String youxing;
    public String getYouxing() {
        return youxing;
    }
    public void setYouxing(String youxing) {
        this.youxing = youxing;
    }

    /*耗油量*/
    private String haoyouliang;
    public String getHaoyouliang() {
        return haoyouliang;
    }
    public void setHaoyouliang(String haoyouliang) {
        this.haoyouliang = haoyouliang;
    }

    /*车险*/
    private String chexian;
    public String getChexian() {
        return chexian;
    }
    public void setChexian(String chexian) {
        this.chexian = chexian;
    }

    /*总里程(公里)*/
    private String zonglicheng;
    public String getZonglicheng() {
        return zonglicheng;
    }
    public void setZonglicheng(String zonglicheng) {
        this.zonglicheng = zonglicheng;
    }

    /*维修次数*/
    private String wxcs;
    public String getWxcs() {
        return wxcs;
    }
    public void setWxcs(String wxcs) {
        this.wxcs = wxcs;
    }

    /*车辆备注*/
    private String carMemo;
    public String getCarMemo() {
        return carMemo;
    }
    public void setCarMemo(String carMemo) {
        this.carMemo = carMemo;
    }

}