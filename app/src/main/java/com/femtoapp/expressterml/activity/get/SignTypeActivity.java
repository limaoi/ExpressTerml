package com.femtoapp.expressterml.activity.get;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;

import lib.kingja.switchbutton.SwitchMultiButton;

/**
 * Created by Autism on 2018/1/23.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class SignTypeActivity extends BaseActivity {

    private SwitchMultiButton mSwitchMultiButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_type);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        mSwitchMultiButton = (SwitchMultiButton) findViewById(R.id.sw_btn);
    }

    private void initEvents() {
        mSwitchMultiButton.setText("成功", "失败").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {

            }
        });
    }

}
