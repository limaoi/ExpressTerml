package com.femtoapp.expressterml.activity.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class ContactHdqActivity extends BaseActivity implements View.OnClickListener {

    private WebView wv_web;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_hdq);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        wv_web = (WebView) findViewById(R.id.wv_web);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    private void initEvents() {
        iv_back.setOnClickListener(this);

        wv_web.loadUrl("http://hl.honghehello.com/general/hello/api/contact_header.php");
        WebSettings wSet = wv_web.getSettings();
        wSet.setJavaScriptEnabled(true);
        wv_web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
