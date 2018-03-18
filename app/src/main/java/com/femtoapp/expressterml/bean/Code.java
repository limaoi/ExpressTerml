package com.femtoapp.expressterml.bean;

/**
 * Created by Autism on 2018/1/23.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class Code {

    private String code;
    private int status;
    private int result;

    public Code(int status, String code) {
        this.status = status;
        this.code = code;
    }

    public Code(int status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
