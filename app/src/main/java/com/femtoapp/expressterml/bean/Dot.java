package com.femtoapp.expressterml.bean;

/**
 * Created by Autism on 2018/1/23.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class Dot {

    private String region;  //所在区域
    private String street;  //所在街道
    private String distance;    //距离
    private int status;
    private String result_data;
    private int time_interval;

    public Dot(String region, String street, String distance) {
        this.region = region;
        this.street = street;
        this.distance = distance;
    }

    public Dot(int status, String result_data) {
        this.status = status;
        this.result_data = result_data;
    }

    public Dot(int time_interval) {
        this.time_interval = time_interval;
    }

    public void setTime_interval(int time_interval) {
        this.time_interval = time_interval;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setResult_data(String result_data) {
        this.result_data = result_data;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet() {
        return street;
    }

    public String getDistance() {
        return distance;
    }

    public int getStatus() {
        return status;
    }

    public String getResult_data() {
        return result_data;
    }

    public int getTime_interval() {
        return time_interval;
    }
}
