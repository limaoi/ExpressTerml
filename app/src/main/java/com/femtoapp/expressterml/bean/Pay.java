package com.femtoapp.expressterml.bean;

import java.util.List;

/**
 * Created by Autism on 2018/2/9.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class Pay {

    private int status;
    private float money;
    private String apply_time;
    private int apply_status;
    private String sr_time;
    private String sr_event;
    private int sr_status;
    private String sr_status_des;
    private List<Pay> result_data;
    private String ERR_MSG;

    public Pay(int status, List<Pay> result_data) {
        this.status = status;
        this.result_data = result_data;
    }

    public Pay(int status, List<Pay> result_data, String ERR_MSG) {
        this.status = status;
        this.result_data = result_data;
        this.ERR_MSG = ERR_MSG;
    }

    public Pay(String apply_time, float money, int apply_status) {
        this.apply_time = apply_time;
        this.money = money;
        this.apply_status = apply_status;
    }

    public Pay(float money, String sr_time, String sr_event, int sr_status) {
        this.money = money;
        this.sr_time = sr_time;
        this.sr_event = sr_event;
        this.sr_status = sr_status;
    }


    public void setSr_time(String sr_time) {
        this.sr_time = sr_time;
    }

    public void setSr_event(String sr_event) {
        this.sr_event = sr_event;
    }

    public void setSr_status(int sr_status) {
        this.sr_status = sr_status;
    }

    public void setSr_status_des(String sr_status_des) {
        this.sr_status_des = sr_status_des;
    }

    public void setERR_MSG(String ERR_MSG) {
        this.ERR_MSG = ERR_MSG;
    }

    public void setResult_data(List<Pay> result_data) {
        this.result_data = result_data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public void setApply_status(int apply_status) {
        this.apply_status = apply_status;
    }

    public int getStatus() {
        return status;
    }

    public float getMoney() {
        return money;
    }

    public String getApply_time() {
        return apply_time;
    }

    public int getApply_status() {
        return apply_status;
    }

    public List<Pay> getResult_data() {
        return result_data;
    }

    public String getSr_time() {
        return sr_time;
    }

    public String getSr_event() {
        return sr_event;
    }

    public int getSr_status() {
        return sr_status;
    }

    public String getSr_status_des() {
        return sr_status_des;
    }

    public String getERR_MSG() {
        return ERR_MSG;
    }
}
