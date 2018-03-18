package com.femtoapp.expressterml.activity.get;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.other.MyApplication;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Order;
import com.femtoapp.expressterml.util.BitmapToBase64Util;
import com.femtoapp.expressterml.util.BitmapUtil;
import com.femtoapp.expressterml.util.CheckApkExist;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Autism on 2018/1/23.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class TakePhotoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "TakePhotoActivity";
    private EditText et_sign_person;
    private EditText et_gsign_person_id;
    private EditText et_gsign_person_number;
    private TextView tv_order_number;
    private TextView tv_time_value;
    private Button btn_commit;
    private ImageView iv_take_photo;
    private TextView tv_take_photo;
    private ImageView iv_take_photo_url;
    private ImageView iv_Navigation;
    private ImageView iv_back;

    private String time;
    private String time2;
    private String sign_user;
    private String sign_id_card;
    private String sign_telephone;
    private String attach1;
    private String record_address_port;

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

    //上传图片
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    public static final int TAKE_PHOTO = 111;
    //保存 照片的目录
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "mms";
    private File photo_file = new File(path);
    private String photoPath;


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
        setContentView(R.layout.activity_take_photo);
        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        et_sign_person = (EditText) findViewById(R.id.et_sign_person);
        et_gsign_person_id = (EditText) findViewById(R.id.et_gsign_person_id);
        et_gsign_person_number = (EditText) findViewById(R.id.et_gsign_person_number);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_time_value = (TextView) findViewById(R.id.tv_time_value);
        iv_take_photo = (ImageView) findViewById(R.id.iv_take_photo);
        tv_take_photo = (TextView) findViewById(R.id.tv_take_photo);
        iv_take_photo_url = (ImageView) findViewById(R.id.iv_take_photo_url);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        mMyApplication = (MyApplication) getApplication();
        iv_Navigation = (ImageView) findViewById(R.id.iv_Navigation);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mMyApplication = (MyApplication) getApplication();
    }

    private void initEvents() {
        btn_commit.setOnClickListener(this);
        iv_take_photo.setOnClickListener(this);
        tv_take_photo.setOnClickListener(this);
        iv_Navigation.setOnClickListener(this);
        iv_back.setOnClickListener(this);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());
        time = formatter.format(curDate);
        time2 = formatter2.format(curDate);

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


        tv_time_value.setText(time);
        tv_order_number.setText(f_cOrderNumber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                sign_user = et_sign_person.getText().toString();
                sign_id_card = et_gsign_person_id.getText().toString();
                sign_telephone = et_gsign_person_number.getText().toString();
                record_address_port = mMyApplication.getAddress_port();
                attach1 = time2 + "01";
                if (sign_user.length() == 0) {
                    Toast.makeText(this, "签收人不能为空！", Toast.LENGTH_SHORT).show();
                }
                if (photoPath == null) {
                    Toast.makeText(this, "请上传图片！", Toast.LENGTH_SHORT).show();
                }
                if (mMyApplication.getAddress_port() == null) {
                    Toast.makeText(this, "位置信息为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (sign_user.length() != 0 && photoPath != null) {
                        uploadPhoto();
                        sign();
                    }
                }
                break;
            case R.id.tv_take_photo:
                openCanera();
                break;
            case R.id.iv_take_photo:
                openCanera();
                break;
            case R.id.iv_Navigation:
                currLocationX = mMyApplication.getCurrLocationX();
                currLocationY = mMyApplication.getCurrLocationY();
                if (CheckApkExist.checkGaoDEExist(this)) {
                    openGaodeMapToGuide();
                } else {
                    openBrowserToGuide();
                    Toast.makeText(TakePhotoActivity.this, "您尚未安装高德地图", Toast.LENGTH_LONG).show();
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
        String url = "androidamap://route?sourceApplication=amap&slat=" + currLocationX + "&slon=" + currLocationY
                + "&dname=" + f_cToStation + f_cToAddress + "&dev=0&t=2";
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

    private void uploadPhoto() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Filedata", attach1 + ".jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File(photoPath)))
                .build();

        Request request = new Request.Builder()
                .url(api.UPLOAD)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i(TAG, "STR:" + str);
                }
            }
        });
    }

    private void sign() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("id", id).add("sign_user", sign_user).add("sign_time", time).add("sign_id_card", sign_id_card).add("sign_telephone", sign_telephone).add("attach1", attach1).add("record_user", USER_ID).add("record_address_port", record_address_port).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.SIGN)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TakePhotoActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Log.i(TAG, "str:" + str);
                    Order order = gson.fromJson(str, Order.class);
                    if (order.getStatus() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(TakePhotoActivity.this, "签收成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PHOTO) {
                //这中情况下 data是为null的，因为自定义了路径 所以通过这个路径来获取
                Bitmap smallBitmap = BitmapUtil.getSmallBitmap(photoPath);
                iv_take_photo_url.setVisibility(View.VISIBLE);
                iv_take_photo_url.setImageBitmap(smallBitmap);
                tv_take_photo.setVisibility(View.GONE);

                // ok 拿到图片的base64 上传
                String base64 = BitmapToBase64Util.bitmapToBase64(smallBitmap);
            }
        }
    }


    private void openCanera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (!photo_file.exists()) {
            photo_file.mkdirs();
        }
        photo_file = new File(path, "/temp.jpg");
        photoPath = path + "/temp.jpg";
        if (photo_file != null) {
            Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", photo_file);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(captureIntent, TAKE_PHOTO);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("photoPath", photoPath);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (TextUtils.isEmpty(photoPath)) {
            photoPath = savedInstanceState.getString("photoPath");
        }
        Log.d(TAG, "onRestoreInstanceState");
    }

}
