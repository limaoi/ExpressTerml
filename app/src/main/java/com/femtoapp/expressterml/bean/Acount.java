package com.femtoapp.expressterml.bean;

import android.accounts.Account;

/**
 * Created by Autism on 2018/2/8.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class Acount {

    private int status;
    private float money;
    private String bank;
    private String account_name;
    private String account_number;
    private String account_address;
    private String ERR_MSG;
    private String apply_time;
    private int apply_status;
    private Acount result_data;

    public Acount(int status, float money) {
        this.status = status;
        this.money = money;
    }

    public Acount(int status, Acount result_data, String ERR_MSG) {
        this.status = status;
        this.result_data = result_data;
        this.ERR_MSG = ERR_MSG;
    }

    public Acount(String bank, String account_name, String account_number, String account_address) {
        this.bank = bank;
        this.account_name = account_name;
        this.account_number = account_number;
        this.account_address = account_address;
    }

    public Acount(int status, Acount result_data) {
        this.status = status;
        this.result_data = result_data;
    }

    public Acount(String apply_time, float money, int apply_status) {
        this.apply_time = apply_time;
        this.money = money;
        this.apply_status = apply_status;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    public void setApply_status(int apply_status) {
        this.apply_status = apply_status;
    }

    public void setResult_data(Acount result_data) {
        this.result_data = result_data;
    }

    public void setERR_MSG(String ERR_MSG) {
        this.ERR_MSG = ERR_MSG;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public void setAccount_address(String account_address) {
        this.account_address = account_address;
    }

    public int getStatus() {
        return status;
    }

    public float getMoney() {
        return money;
    }

    public String getBank() {
        return bank;
    }

    public String getAccount_name() {
        return account_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getAccount_address() {
        return account_address;
    }

    public String getERR_MSG() {
        return ERR_MSG;
    }

    public Acount getResult_data() {
        return result_data;
    }

    public String getApply_time() {
        return apply_time;
    }

    public int getApply_status() {
        return apply_status;
    }
}
