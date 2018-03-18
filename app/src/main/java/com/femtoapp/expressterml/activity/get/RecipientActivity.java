package com.femtoapp.expressterml.activity.get;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.MainActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.other.MyApplication;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Order;
import com.femtoapp.expressterml.util.CheckApkExist;
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

public class RecipientActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RecipientActivity";
    private TextView tv_phoneNumber_value;
    private TextView tv_sender_name_value;
    private TextView tv_sender_city_value;
    private TextView tv_sender_address_value;
    private TextView tv_goods_long_value;
    private TextView tv_goods_wide_value;
    private TextView tv_goods_high_value;
    private TextView tv_goods_weigth_value;
    private TextView tv_phoneNumber_value2;
    private TextView tv_receiver_name_value;
    private TextView tv_receiver_city_value;
    private TextView tv_receiver_address_value;
    private TextView tv_goods_money_value;
    private Button btn_commit;
    private ImageView iv_Navigation;
    private ImageView iv_back;

    private String id;
    private String f_cFromPerson;
    private String f_cFromTel;
    private String f_cFromStation;
    private String f_cFromAddress;
    private String f_cFromAddress_port;
    private String f_cToPerson;
    private String f_cToTel;
    private String f_cToStation;
    private String f_cToAddress;
    private String f_cToAddress_port;
    private String f_cGoods;
    private String f_nPiece;
    private String f_nWeight;
    private String nlong;
    private String nwidth;
    private String nheight;
    private String f_nCubicMetre;
    private String f_cOrderNumber;
    private String status;
    private float f_nMoney;


    private MyApplication mMyApplication;
    //当前位置
    private String currLocationX;
    private String currLocationY;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        tv_phoneNumber_value = (TextView) findViewById(R.id.tv_phoneNumber_value);
        tv_sender_name_value = (TextView) findViewById(R.id.tv_sender_name_value);
        tv_sender_city_value = (TextView) findViewById(R.id.tv_sender_city_value);
        tv_sender_address_value = (TextView) findViewById(R.id.tv_sender_address_value);
        tv_goods_long_value = (TextView) findViewById(R.id.tv_goods_long_value);
        tv_goods_wide_value = (TextView) findViewById(R.id.tv_goods_wide_value);
        tv_goods_high_value = (TextView) findViewById(R.id.tv_goods_high_value);
        tv_goods_weigth_value = (TextView) findViewById(R.id.tv_goods_weigth_value);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        iv_Navigation = (ImageView) findViewById(R.id.iv_Navigation);
        tv_goods_money_value = (TextView) findViewById(R.id.tv_goods_money_value);
        tv_phoneNumber_value2 = (TextView) findViewById(R.id.tv_phoneNumber_value2);
        tv_receiver_name_value = (TextView) findViewById(R.id.tv_receiver_name_value);
        tv_receiver_city_value = (TextView) findViewById(R.id.tv_receiver_city_value);
        tv_receiver_address_value = (TextView) findViewById(R.id.tv_receiver_address_value);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mMyApplication = (MyApplication) getApplication();
    }

    private void initEvents() {
        btn_commit.setOnClickListener(this);
        iv_Navigation.setOnClickListener(this);
        iv_back.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        f_cFromPerson = intent.getStringExtra("f_cFromPerson");
        f_cFromTel = intent.getStringExtra("f_cFromTel");
        f_cFromStation = intent.getStringExtra("f_cFromStation");
        f_cFromAddress = intent.getStringExtra("f_cFromAddress");
        f_cFromAddress_port = intent.getStringExtra("f_cFromAddress_port");
        f_cToPerson = intent.getStringExtra("f_cToPerson");
        f_cToTel = intent.getStringExtra("f_cToTel");
        f_cToStation = intent.getStringExtra("f_cToStation");
        f_cToAddress = intent.getStringExtra("f_cToAddress");
        f_cToAddress_port = intent.getStringExtra("f_cToAddress_port");
        f_cGoods = intent.getStringExtra("f_cGoods");
        f_nPiece = intent.getStringExtra("f_nPiece");
        f_nWeight = intent.getStringExtra("f_nWeight");
        nlong = intent.getStringExtra("nlong");
        nwidth = intent.getStringExtra("nwidth");
        nheight = intent.getStringExtra("nheight");
        f_nCubicMetre = intent.getStringExtra("f_nCubicMetre");
        f_cOrderNumber = intent.getStringExtra("f_cOrderNumber");
        status = intent.getStringExtra("status");
        f_nMoney = intent.getFloatExtra("f_nMoney", 0);

        tv_phoneNumber_value.setText(f_cFromTel);
        tv_sender_name_value.setText(f_cFromPerson);
        tv_sender_city_value.setText(f_cFromStation);
        tv_sender_address_value.setText(f_cFromAddress);
        tv_phoneNumber_value2.setText(f_cToTel);
        tv_receiver_name_value.setText(f_cToPerson);
        tv_receiver_city_value.setText(f_cToStation);
        tv_receiver_address_value.setText(f_cToAddress);
        tv_goods_long_value.setText(nlong);
        tv_goods_wide_value.setText(nwidth);
        tv_goods_high_value.setText(nheight);
        tv_goods_weigth_value.setText(f_nWeight);
        tv_goods_money_value.setText(f_nMoney + "元");
    }

    private String getId(Intent intent, String id) {
        return intent.getStringExtra(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                confirmOder();
                break;
            case R.id.iv_Navigation:
                currLocationX = mMyApplication.getCurrLocationX();
                currLocationY = mMyApplication.getCurrLocationY();
                if (CheckApkExist.checkGaoDEExist(this)) {
                    openGaodeMapToGuide();
                } else {
                    openBrowserToGuide();
                    Toast.makeText(RecipientActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
                    /*Uri uri = Uri.parse("http://wap.amap.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void openGaodeMapToGuide() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String url = "androidamap://route?sourceApplication=amap" + "&dname=" + f_cToStation + f_cToAddress + "&dev=0&t=2";
        Uri uri = Uri.parse(url);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        startActivity(intent);
    }

    private void openBrowserToGuide() {
        String url = "http://uri.amap.com/navigation?to=null,null," + f_cToStation + f_cToAddress + "&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void confirmOder() {

        f_cFromTel = tv_phoneNumber_value.getText().toString();
        f_cFromPerson = tv_sender_name_value.getText().toString();
        f_cFromStation = tv_sender_city_value.getText().toString();
        f_cFromAddress = tv_sender_address_value.getText().toString();
        f_cFromTel = tv_phoneNumber_value.getText().toString();
        nlong = tv_goods_long_value.getText().toString();
        nwidth = tv_goods_wide_value.getText().toString();
        nheight = tv_goods_high_value.getText().toString();
        f_nWeight = tv_goods_weigth_value.getText().toString();

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("id", id)
                .add("f_cOrderNumber", f_cOrderNumber)
                .add("f_cFromPerson", f_cFromPerson)
                .add("f_cFromTel", f_cFromTel)
                .add("f_cFromStation", f_cFromStation)
                .add("f_cFromAddress", f_cFromAddress)
                .add("f_cFromAddress_port", "25.3343,30.1123")
                .add("f_cToPerson", f_cToPerson)
                .add("f_cToTel", f_cToTel)
                .add("f_cToStation", f_cToStation)
                .add("f_cToAddress", f_cToAddress)
                .add("f_cToAddress_port", "25.3343,30.1123")
                .add("f_cGoods", f_cGoods)
                .add("f_nPiece", f_nPiece)
                .add("f_nWeight", f_nWeight)
                .add("nlong", nlong)
                .add("nwidth", nwidth)
                .add("nheight", nheight)
                .add("f_nCubicMetre", f_nCubicMetre)
                .add("status", "3")
                .add("f_nMoney", f_nMoney + "")
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.LANSHOU_SUBMIT).
                        build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RecipientActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Order order = gson.fromJson(str, Order.class);
                    Log.i(TAG, "STR:" + str);
                    if (order.getStatus() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RecipientActivity.this, "收件成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }
}
