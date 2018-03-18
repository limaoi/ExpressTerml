package com.femtoapp.expressterml.activity.other;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Dot;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Autism on 2018/2/7.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class MyApplication extends Application {
    private String address_port;  //经纬度
    private String upload_time;  //上传时间
    private SharedPreferencesHelper sharedPreferencesHelper;
    private Boolean isLogin;
    private String USER_ID;
    private int time_interval;

    //当前位置坐标
    private String currLocationX;
    private String currLocationY;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    address_port = aMapLocation.getLongitude() + "," + aMapLocation.getLatitude();
                    currLocationX = String.valueOf(aMapLocation.getLatitude());
                    currLocationY = String.valueOf(aMapLocation.getLongitude());
                    if (sharedPreferencesHelper.contain("isLogin")) {
                        isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
                        if (isLogin) {
                            USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
                            Log.i("TAG", "USER_ID:" + USER_ID);
                            getTime();
                            uploadPort();
                        }
                    }
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    private void uploadPort() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("USER_ID", USER_ID).add("address_port", address_port).add("upload_time", upload_time).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.PORT)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("TAG", "PortSTR:" + str);
                Log.i("TAG", "上传地址成功！" + str);
            }
        });
    }

    private void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        upload_time = formatter.format(curDate);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initEvent();
        setAddress_port(address_port);
        setCurrLocationX(currLocationX);
        setCurrLocationY(currLocationY);
    }

    private void getIntervalTime() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.INTERVAL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Dot dot = gson.fromJson(str, Dot.class);
                    time_interval = dot.getTime_interval();
                    getLocation();
                }
            }
        });
    }


    private void initEvent() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        getIntervalTime();

        sharedPreferencesHelper = new SharedPreferencesHelper(
                getApplicationContext(), "sp_data");
    }


    private void getLocation() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //5s定位一次
        mLocationOption.setInterval(time_interval * 1000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }


    public void setAddress_port(String address_port) {
        this.address_port = address_port;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getAddress_port() {
        return address_port;
    }

    public String getUpload_time() {
        return upload_time;
    }


    public void setCurrLocationX(String currLocationX) {
        this.currLocationX = currLocationX;
    }

    public void setCurrLocationY(String currLocationY) {
        this.currLocationY = currLocationY;
    }

    public String getCurrLocationX() {
        return currLocationX;
    }

    public String getCurrLocationY() {
        return currLocationY;
    }
}
