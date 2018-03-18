package com.femtoapp.expressterml.bean;

/**
 * Created by Autism on 2018/2/7.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class User {

    private String BYNAME;
    private String PASSWORD;
    private String DEPT_ID;
    private String DEPT_NAME;
    private String USER_ID;
    private String USER_NAME;
    private String ERR_MSG;
    private int result;
    private User result_data;

    public User(int result, User result_data) {
        this.result = result;
        this.result_data = result_data;
    }

    public User(String USER_ID, String DEPT_ID, String DEPT_NAME, String BYNAME, String USER_NAME, String PASSWORD, String ERR_MSG) {
        this.USER_ID = USER_ID;
        this.DEPT_ID = DEPT_ID;
        this.DEPT_NAME = DEPT_NAME;
        this.BYNAME = BYNAME;
        this.USER_NAME = USER_NAME;
        this.PASSWORD = PASSWORD;
        this.ERR_MSG = ERR_MSG;
    }


    public void setResult(int result) {
        this.result = result;
    }

    public void setResult_data(User result_data) {
        this.result_data = result_data;
    }

    public void setBYNAME(String BYNAME) {
        this.BYNAME = BYNAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setDEPT_ID(String DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public void setERR_MSG(String ERR_MSG) {
        this.ERR_MSG = ERR_MSG;
    }

    public String getBYNAME() {
        return BYNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getDEPT_ID() {
        return DEPT_ID;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public String getERR_MSG() {
        return ERR_MSG;
    }

    public int getResult() {
        return result;
    }

    public User getResult_data() {
        return result_data;
    }
}
