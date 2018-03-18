package com.femtoapp.expressterml.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.MainActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.User;
import com.femtoapp.expressterml.util.CheckPermissionsActivity;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Autism on 2018/2/7.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class LoginActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText phoneNumEt;
    private EditText pswNumEt;
    private TextView tv_new_psw;
    private Button btn_login;
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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        initEvent();

    }

    private void initView() {
        phoneNumEt = (EditText) findViewById(R.id.asp_et_phone_number);
        pswNumEt = (EditText) findViewById(R.id.asp_et_psw);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_new_psw = (TextView) findViewById(R.id.tv_new_psw);
        btn_login.setOnClickListener(this);
        tv_new_psw.setOnClickListener(this);
    }

    private void initEvent() {
        sharedPreferencesHelper = new SharedPreferencesHelper(
                LoginActivity.this, "sp_data");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_new_psw:
                Intent intent = new Intent(this, ForgetPswActivity.class);
                startActivity(intent);
                break;
        }
    }


    private void login() {

        String phoneNum = phoneNumEt.getText().toString();
        String pswNum = pswNumEt.getText().toString();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.LOGIN1 + "?BYNAME=" + phoneNum + "&PASSWORD=" + pswNum)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    final User user = gson.fromJson(str, User.class);
                    Log.i(TAG, "STR:" + str);
                    if (user.getResult() != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, user.getResult_data().getERR_MSG(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                        sharedPreferencesHelper.put("isLogin", true);
                        sharedPreferencesHelper.put("DEPT_ID", user.getResult_data().getDEPT_ID());
                        sharedPreferencesHelper.put("DEPT_NAME", user.getResult_data().getDEPT_NAME());
                        sharedPreferencesHelper.put("USER_ID", user.getResult_data().getUSER_ID());
                        sharedPreferencesHelper.put("BYNAME", user.getResult_data().getBYNAME());
                        sharedPreferencesHelper.put("USER_NAME", user.getResult_data().getUSER_NAME());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i(TAG, "ceshi---------" + str);
                } else {
                    String str = response.body().toString();
                    Log.i(TAG, "ceshi---------" + str);
                }
            }
        });
    }
}
