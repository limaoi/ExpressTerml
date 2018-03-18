package com.femtoapp.expressterml.bean;

/**
 * Created by limaoi on 2018/2/26.
 * E-mail：autismlm20@vip.qq.com
 */

public class Version {

    private int status;
    private String url;


    public Version(int status, String url) {
        this.status = status;
        this.url = url;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }
}
