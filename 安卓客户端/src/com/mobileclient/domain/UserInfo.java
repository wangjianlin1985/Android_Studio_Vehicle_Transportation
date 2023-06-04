package com.mobileclient.domain;

import java.io.Serializable;

public class UserInfo implements Serializable {
    /*驾号*/
    private String jiahao;
    public String getJiahao() {
        return jiahao;
    }
    public void setJiahao(String jiahao) {
        this.jiahao = jiahao;
    }

    /*登录密码*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*电话*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*驾照类型*/
    private int jzTypeObj;
    public int getJzTypeObj() {
        return jzTypeObj;
    }
    public void setJzTypeObj(int jzTypeObj) {
        this.jzTypeObj = jzTypeObj;
    }

    /*驾龄*/
    private String jialing;
    public String getJialing() {
        return jialing;
    }
    public void setJialing(String jialing) {
        this.jialing = jialing;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}