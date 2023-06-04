package com.mobileclient.domain;

import java.io.Serializable;

public class Yunshu implements Serializable {
    /*记录id*/
    private int yunshuId;
    public int getYunshuId() {
        return yunshuId;
    }
    public void setYunshuId(int yunshuId) {
        this.yunshuId = yunshuId;
    }

    /*驾号*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*车牌*/
    private int carObj;
    public int getCarObj() {
        return carObj;
    }
    public void setCarObj(int carObj) {
        this.carObj = carObj;
    }

    /*运输货物*/
    private String yshw;
    public String getYshw() {
        return yshw;
    }
    public void setYshw(String yshw) {
        this.yshw = yshw;
    }

    /*重量(吨)*/
    private String zl;
    public String getZl() {
        return zl;
    }
    public void setZl(String zl) {
        this.zl = zl;
    }

    /*需要时间*/
    private String xysj;
    public String getXysj() {
        return xysj;
    }
    public void setXysj(String xysj) {
        this.xysj = xysj;
    }

    /*起始地*/
    private String qsd;
    public String getQsd() {
        return qsd;
    }
    public void setQsd(String qsd) {
        this.qsd = qsd;
    }

    /*目的地*/
    private String mudidi;
    public String getMudidi() {
        return mudidi;
    }
    public void setMudidi(String mudidi) {
        this.mudidi = mudidi;
    }

    /*公里数*/
    private String gonglishu;
    public String getGonglishu() {
        return gonglishu;
    }
    public void setGonglishu(String gonglishu) {
        this.gonglishu = gonglishu;
    }

    /*运输备注*/
    private String yunshuMemo;
    public String getYunshuMemo() {
        return yunshuMemo;
    }
    public void setYunshuMemo(String yunshuMemo) {
        this.yunshuMemo = yunshuMemo;
    }

}