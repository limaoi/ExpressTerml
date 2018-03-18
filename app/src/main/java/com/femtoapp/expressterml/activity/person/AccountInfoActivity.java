package com.femtoapp.expressterml.activity.person;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.MainActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.other.LoginActivity;
import com.femtoapp.expressterml.activity.other.PswChangeActivity;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class AccountInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_phoneNumber_value;
    private TextView tv_name_value;
    private TextView tv_region_value;
    private ImageView iv_back;
    private Button btn_psw_change;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private Boolean isLogin;


    @Override
    public void onStart() {
        super.onStart();
        sharedPreferencesHelper = new SharedPreferencesHelper(
                this, "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                tv_phoneNumber_value.setText(sharedPreferencesHelper.getSharedPreference("BYNAME", "").toString().trim());
                tv_name_value.setText(sharedPreferencesHelper.getSharedPreference("USER_NAME", "").toString().trim());
                tv_region_value.setText(sharedPreferencesHelper.getSharedPreference("DEPT_NAME", "").toString().trim());
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }


    private void initView() {
        btn_psw_change = (Button) findViewById(R.id.btn_psw_change);
        tv_phoneNumber_value = (TextView) findViewById(R.id.tv_phoneNumber_value);
        tv_name_value = (TextView) findViewById(R.id.tv_name_value);
        tv_region_value = (TextView) findViewById(R.id.tv_region_value);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    private void initEvents() {
        btn_psw_change.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_psw_change:
                Intent intent = new Intent(this, PswChangeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
