package com.mobileclient.domain;

import java.io.Serializable;

public class Yunshu implements Serializable {
    /*��¼id*/
    private int yunshuId;
    public int getYunshuId() {
        return yunshuId;
    }
    public void setYunshuId(int yunshuId) {
        this.yunshuId = yunshuId;
    }

    /*�ݺ�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����*/
    private int carObj;
    public int getCarObj() {
        return carObj;
    }
    public void setCarObj(int carObj) {
        this.carObj = carObj;
    }

    /*�������*/
    private String yshw;
    public String getYshw() {
        return yshw;
    }
    public void setYshw(String yshw) {
        this.yshw = yshw;
    }

    /*����(��)*/
    private String zl;
    public String getZl() {
        return zl;
    }
    public void setZl(String zl) {
        this.zl = zl;
    }

    /*��Ҫʱ��*/
    private String xysj;
    public String getXysj() {
        return xysj;
    }
    public void setXysj(String xysj) {
        this.xysj = xysj;
    }

    /*��ʼ��*/
    private String qsd;
    public String getQsd() {
        return qsd;
    }
    public void setQsd(String qsd) {
        this.qsd = qsd;
    }

    /*Ŀ�ĵ�*/
    private String mudidi;
    public String getMudidi() {
        return mudidi;
    }
    public void setMudidi(String mudidi) {
        this.mudidi = mudidi;
    }

    /*������*/
    private String gonglishu;
    public String getGonglishu() {
        return gonglishu;
    }
    public void setGonglishu(String gonglishu) {
        this.gonglishu = gonglishu;
    }

    /*���䱸ע*/
    private String yunshuMemo;
    public String getYunshuMemo() {
        return yunshuMemo;
    }
    public void setYunshuMemo(String yunshuMemo) {
        this.yunshuMemo = yunshuMemo;
    }

}