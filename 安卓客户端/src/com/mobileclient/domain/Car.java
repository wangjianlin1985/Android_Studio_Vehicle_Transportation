package com.mobileclient.domain;

import java.io.Serializable;

public class Car implements Serializable {
    /*����id*/
    private int carId;
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }

    /*����*/
    private String carNo;
    public String getCarNo() {
        return carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    /*����*/
    private int carModel;
    public int getCarModel() {
        return carModel;
    }
    public void setCarModel(int carModel) {
        this.carModel = carModel;
    }

    /*Ʒ��*/
    private String pinpai;
    public String getPinpai() {
        return pinpai;
    }
    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    /*����*/
    private String youxing;
    public String getYouxing() {
        return youxing;
    }
    public void setYouxing(String youxing) {
        this.youxing = youxing;
    }

    /*������*/
    private String haoyouliang;
    public String getHaoyouliang() {
        return haoyouliang;
    }
    public void setHaoyouliang(String haoyouliang) {
        this.haoyouliang = haoyouliang;
    }

    /*����*/
    private String chexian;
    public String getChexian() {
        return chexian;
    }
    public void setChexian(String chexian) {
        this.chexian = chexian;
    }

    /*�����(����)*/
    private String zonglicheng;
    public String getZonglicheng() {
        return zonglicheng;
    }
    public void setZonglicheng(String zonglicheng) {
        this.zonglicheng = zonglicheng;
    }

    /*ά�޴���*/
    private String wxcs;
    public String getWxcs() {
        return wxcs;
    }
    public void setWxcs(String wxcs) {
        this.wxcs = wxcs;
    }

    /*������ע*/
    private String carMemo;
    public String getCarMemo() {
        return carMemo;
    }
    public void setCarMemo(String carMemo) {
        this.carMemo = carMemo;
    }

}