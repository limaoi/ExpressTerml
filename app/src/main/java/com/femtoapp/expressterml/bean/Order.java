package com.femtoapp.expressterml.bean;

import java.util.List;

/**
 * Created by Autism on 2018/1/17.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class Order {

    private String id;
    private String f_cFromPerson;
    private String f_cOrderNumber;
    private String f_cFromTel;
    private String f_cFromStation;
    private String f_cFromAddress;
    private String f_cFromAddress_port;
    private String f_cGoods;
    private String f_nPiece;
    private String f_nWeight;
    private String nlong;
    private String nwidth;
    private String nheight;
    private String f_nCubicMetre;
    private String f_cToPerson;
    private String f_cToTel;
    private String f_cToStation;
    private String f_cToAddress;
    private String f_cToAddress_port;
    private String file_name;
    private float f_nMoney;

    private int status;
    private List<String> transportDyna; //运送动态
    private List<String> dynaTime; //运送动态时间

    public Order(String id, String f_cFromPerson, String f_cFromTel, String f_cFromStation, String f_cFromAddress, String f_cFromAddress_port, String f_cToPerson, String f_cToTel, String f_cToStation, String f_cToAddress, String f_cToAddress_port, String f_cGoods, String f_nPiece, String f_nWeight, String nlong, String nwidth, String nheight, String f_nCubicMetre, String f_cOrderNumber, int status,float f_nMoney) {
        this.id = id;
        this.f_cFromPerson = f_cFromPerson;
        this.f_cFromTel = f_cFromTel;
        this.f_cFromStation = f_cFromStation;
        this.f_cFromAddress = f_cFromAddress;
        this.f_cFromAddress_port = f_cFromAddress_port;
        this.f_cFromPerson = f_cFromPerson;
        this.f_cToPerson = f_cToPerson;
        this.f_cToTel = f_cToTel;
        this.f_cToStation = f_cToStation;
        this.f_cToAddress = f_cToAddress;
        this.f_cToAddress_port = f_cToAddress_port;
        this.f_cGoods = f_cGoods;
        this.f_nPiece = f_nPiece;
        this.f_nWeight = f_nWeight;
        this.nlong = nlong;
        this.nwidth = nwidth;
        this.nheight = nheight;
        this.f_nCubicMetre = f_nCubicMetre;
        this.f_cOrderNumber = f_cOrderNumber;
        this.status = status;
        this.f_nMoney = f_nMoney;
    }

    public Order(String f_cOrderNumber, String f_cFromPerson, int status) {
        this.f_cOrderNumber = f_cOrderNumber;
        this.f_cFromPerson = f_cFromPerson;
        this.status = status;
    }


    public void setF_nWeight(String f_nWeight) {
        this.f_nWeight = f_nWeight;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public void setF_nMoney(float f_nMoney) {
        this.f_nMoney = f_nMoney;
    }

    public Order(int status) {
        this.status = status;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setF_cFromTel(String f_cFromTel) {
        this.f_cFromTel = f_cFromTel;
    }

    public void setF_cFromStation(String f_cFromStation) {
        this.f_cFromStation = f_cFromStation;
    }

    public void setF_cFromAddress(String f_cFromAddress) {
        this.f_cFromAddress = f_cFromAddress;
    }

    public void setF_cFromAddress_port(String f_cFromAddress_port) {
        this.f_cFromAddress_port = f_cFromAddress_port;
    }

    public void setF_cGoods(String f_cGoods) {
        this.f_cGoods = f_cGoods;
    }

    public void setF_nPiece(String f_nPiece) {
        this.f_nPiece = f_nPiece;
    }

    public void setF_nWeigh(String f_nWeight) {
        this.f_nWeight = f_nWeight;
    }

    public void setNlong(String nlong) {
        this.nlong = nlong;
    }

    public void setNwidth(String nwidth) {
        this.nwidth = nwidth;
    }

    public void setNheight(String nheight) {
        this.nheight = nheight;
    }

    public void setF_nCubicMetre(String f_nCubicMetre) {
        this.f_nCubicMetre = f_nCubicMetre;
    }

    public void setF_cToPerson(String f_cToPerson) {
        this.f_cToPerson = f_cToPerson;
    }

    public void setF_cToTel(String f_cToTel) {
        this.f_cToTel = f_cToTel;
    }

    public void setF_cToStation(String f_cToStation) {
        this.f_cToStation = f_cToStation;
    }

    public void setF_cToAddress(String f_cToAddress) {
        this.f_cToAddress = f_cToAddress;
    }

    public void setF_cToAddress_port(String f_cToAddress_port) {
        this.f_cToAddress_port = f_cToAddress_port;
    }

    public void setF_cFromPerson(String f_cFromPerson) {
        this.f_cFromPerson = f_cFromPerson;
    }

    public void setF_cOrderNumber(String f_cOrderNumber) {
        this.f_cOrderNumber = f_cOrderNumber;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTransportDyna(List<String> transportDyna) {
        this.transportDyna = transportDyna;
    }

    public void setDynaTime(List<String> dynaTime) {
        this.dynaTime = dynaTime;
    }

    public List<String> getTransportDyna() {
        return transportDyna;
    }

    public List<String> getDynaTime() {
        return dynaTime;
    }

    public String getF_cFromPerson() {
        return f_cFromPerson;
    }

    public String getF_cOrderNumber() {
        return f_cOrderNumber;
    }

    public int getStatus() {
        return status;
    }

    public String getF_cFromTel() {
        return f_cFromTel;
    }

    public String getF_cFromStation() {
        return f_cFromStation;
    }

    public String getF_cFromAddress() {
        return f_cFromAddress;
    }

    public String getF_cFromAddress_port() {
        return f_cFromAddress_port;
    }

    public String getF_cGoods() {
        return f_cGoods;
    }

    public String getF_nPiece() {
        return f_nPiece;
    }

    public String getF_nWeight() {
        return f_nWeight;
    }

    public String getNlong() {
        return nlong;
    }

    public String getNwidth() {
        return nwidth;
    }

    public String getNheight() {
        return nheight;
    }

    public String getF_nCubicMetre() {
        return f_nCubicMetre;
    }

    public String getF_cToPerson() {
        return f_cToPerson;
    }

    public String getF_cToTel() {
        return f_cToTel;
    }

    public String getF_cToStation() {
        return f_cToStation;
    }

    public String getF_cToAddress() {
        return f_cToAddress;
    }

    public String getF_cToAddress_port() {
        return f_cToAddress_port;
    }

    public String getId() {
        return id;
    }

    public String getFile_name() {
        return file_name;
    }

    public float getF_nMoney() {
        return f_nMoney;
    }
}
