package com.femtoapp.expressterml.activity.other;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.MainActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Code;
import com.femtoapp.expressterml.bean.User;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Autism on 2018/2/8.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class PswChangeActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PswChangeActivity";

    private EditText et_phone_number;
    private EditText et_idcode;
    private EditText et_psw;
    private EditText et_new_psw;
    private TextView asp_iv_del_pwd;
    private TextView tv_hint_phoneNum;
    private TextView tv_hint_psw;
    private TextView tv_hint_psw2;
    private TextView btn_commit;
    private ImageView iv_back;
    private TimeCount time;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String USER_ID;
    private Boolean isLogin;

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferencesHelper = new SharedPreferencesHelper(
                this, "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psw_change);


        initView();

        initEvent();
    }


    private void initView() {
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        et_idcode = (EditText) findViewById(R.id.et_idcode);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_new_psw = (EditText) findViewById(R.id.et_new_psw);
        asp_iv_del_pwd = (TextView) findViewById(R.id.asp_iv_del_pwd);
        tv_hint_phoneNum = (TextView) findViewById(R.id.tv_hint_phoneNum);
        tv_hint_psw = (TextView) findViewById(R.id.tv_hint_psw);
        tv_hint_psw2 = (TextView) findViewById(R.id.tv_hint_psw2);
        btn_commit = (TextView) findViewById(R.id.btn_commit);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        time = new PswChangeActivity.TimeCount(60000, 1000); //60s
    }


    private void initEvent() {
        asp_iv_del_pwd.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.asp_iv_del_pwd:
                if (et_phone_number.getText().toString().length() != 11) {
                    tv_hint_phoneNum.setVisibility(View.VISIBLE);
                } else {
                    tv_hint_phoneNum.setVisibility(View.GONE);
                    getCode();
                    time.start();
                }
                break;
            case R.id.btn_commit:
                if (et_phone_number.getText().toString().length() == 11) {
                    tv_hint_phoneNum.setVisibility(View.GONE);
                } else {
                    tv_hint_phoneNum.setVisibility(View.VISIBLE);
                }
                if (et_psw.getText().toString().matches("[A-Za-z0-9]+")) {
                    tv_hint_psw.setVisibility(View.GONE);
                } else {
                    tv_hint_psw.setVisibility(View.VISIBLE);
                }
                if (et_new_psw.getText().toString().matches(et_psw.getText().toString())) {
                    tv_hint_psw2.setVisibility(View.GONE);
                } else {
                    tv_hint_psw2.setVisibility(View.VISIBLE);
                }
                if (et_phone_number.getText().toString().length() == 11 && et_psw.getText().toString().matches("[A-Za-z0-9]+") && et_new_psw.getText().toString().matches("[A-Za-z0-9]+")) {
                    modify();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void modify() {
        final String telephone = et_phone_number.getText().toString();
        final String code1 = et_idcode.getText().toString();
        final String psw = et_psw.getText().toString();
        final String psw2 = et_new_psw.getText().toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.SMS_CODE_CHECK + telephone + "&code=" + code1)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PswChangeActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Code code = gson.fromJson(str, Code.class);
                    Log.i(TAG, "sms_check---" + "status----" + code.getStatus());
                    if (code.getStatus() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PswChangeActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                                .get()
                                .url(api.PSWChange + "?USER_ID=" + USER_ID + "&TELPHONE=" + telephone + "&PASSWORD=" + psw + "&PASSWORD1=" + psw2 + "&code=" + code1)
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
                                    Log.i(TAG, "STR:" + str);
                                    final User user = gson.fromJson(str, User.class);
                                    if (user.getResult() != 0) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PswChangeActivity.this, user.getResult_data().getERR_MSG(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PswChangeActivity.this, user.getResult_data().getERR_MSG(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        finish();
                                    }
                                } else {
                                    String str = response.body().string();
                                    Log.i(TAG, "network---" + str);
                                }
                            }
                        });
                    }
                }
            }
        });

    }


    //获取验证码
    private void getCode() {
        String telephone = et_phone_number.getText().toString();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.SMS_CODE + telephone)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PswChangeActivity.this, "请求获取验证码失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e(TAG, "失败---");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Code code = gson.fromJson(str, Code.class);
                    if (code.getStatus() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PswChangeActivity.this, "获取验证码失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    Log.i(TAG, "sms_code--" + "status----" + code.getStatus() + "----code-----" + code.getCode());
                } else {
                    String str = response.body().string();
                    Log.i(TAG, "network---" + str);
                }
            }
        });
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            asp_iv_del_pwd.setEnabled(false);
            asp_iv_del_pwd.setTextColor(Color.parseColor("#00aeff"));
            asp_iv_del_pwd.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            asp_iv_del_pwd.setText("重新获取");
            asp_iv_del_pwd.setEnabled(true);
            asp_iv_del_pwd.setTextColor(Color.parseColor("#00aeff"));
        }
    }
}
