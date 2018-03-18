package com.femtoapp.expressterml.activity.person.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.get.TakePhotoActivity;
import com.femtoapp.expressterml.activity.person.BillDetailedActivity;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Acount;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class WalletActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_wallet_number;
    private ImageView iv_back_left;
    private RelativeLayout withdrawalsLayt;
    private RelativeLayout billDetailedLayt;

    private SharedPreferencesHelper sharedPreferencesHelper;
    private String USER_ID;
    private Boolean isLogin;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        tv_wallet_number = (TextView) findViewById(R.id.tv_wallet_number);
        withdrawalsLayt = (RelativeLayout) findViewById(R.id.rl_withdrawals);
        billDetailedLayt = (RelativeLayout) findViewById(R.id.rl_bill_detailed);
        iv_back_left = (ImageView) findViewById(R.id.iv_back_left);
    }

    private void initEvents() {
        withdrawalsLayt.setOnClickListener(this);
        billDetailedLayt.setOnClickListener(this);
        iv_back_left.setOnClickListener(this);

        sharedPreferencesHelper = new SharedPreferencesHelper(
                this, "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
            }
        }
        showWalletNum();
    }

    private void showWalletNum() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("USER_ID", USER_ID).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.ACCOUNT)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WalletActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    final Acount acount = gson.fromJson(str, Acount.class);
                    if (acount.getStatus() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_wallet_number.setText(acount.getMoney() + "");
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_withdrawals:
                Intent intent = new Intent(this, WithdrawalsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_bill_detailed:
                Intent intent3 = new Intent(this, BillDetailedActivity.class);
                startActivity(intent3);
                break;
            case R.id.iv_back_left:
                finish();
                break;
        }
    }
}
