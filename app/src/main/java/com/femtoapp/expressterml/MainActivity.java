package com.femtoapp.expressterml;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.femtoapp.expressterml.adapter.other.TabPageAdapter;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Version;
import com.femtoapp.expressterml.fragment.main.GetGoodsMgrFragment;
import com.femtoapp.expressterml.fragment.main.MeMgrFragment;
import com.femtoapp.expressterml.fragment.main.SendGoodsMgrFragment;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    public static final String CONNECT_STATUS = "connect.status";
    private RadioGroup mRadioGroup;
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPager mViewPager;
    private TabPageAdapter mTabPageAdapter;
    private RadioButton gGoodsRadioButton;
    private RadioButton sGoodsRadioButton;
    private RadioButton meRadioButton;
    private boolean isExit;
    private String downLoadUrl;
    private SharedPreferencesHelper sharedPreferencesHelper;

    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();

        //初始化控件
        initView();

        //初始化事件
        initEvents();

        checkVersion();
    }

    private void checkVersion() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.CHECK_VERSION + "?para_code=kd_version&para_value=1.0.0")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Version version = gson.fromJson(str, Version.class);
                    if (version.getStatus() == 1) {
                        downLoadUrl = version.getUrl();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showLogoutDialog();
                            }
                        });
                    }
                }
            }
        });
    }

    private void showLogoutDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("有可用更新");
        normalDialog.setMessage("是否立即前往下载?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(downLoadUrl));
                        startActivity(intent);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        normalDialog.show();
    }

    private void initFragment() {
        GetGoodsMgrFragment getGoodsMgrFragment = new GetGoodsMgrFragment();
        SendGoodsMgrFragment sendGoodsMgrFragment = new SendGoodsMgrFragment();
        MeMgrFragment meMgrFragment = new MeMgrFragment();
        mFragments.add(getGoodsMgrFragment);
        mFragments.add(sendGoodsMgrFragment);
        mFragments.add(meMgrFragment);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        gGoodsRadioButton = (RadioButton) findViewById(R.id.rb_get_goods);
        sGoodsRadioButton = (RadioButton) findViewById(R.id.rb_send_goods);
        meRadioButton = (RadioButton) findViewById(R.id.rb_me);
        mTabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), mFragments);
    }

    private void initEvents() {

        mViewPager.setAdapter(mTabPageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabPageAdapter.updateData(mFragments);


        //底部导航栏
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_get_goods:
                        selectPage(0);
                        break;
                    case R.id.rb_send_goods:
                        selectPage(1);
                        break;
                    case R.id.rb_me:
                        selectPage(2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void selectPage(int position) {
        switch (position) {
            case 0:
                gGoodsRadioButton.setChecked(true);
                break;
            case 1:
                sGoodsRadioButton.setChecked(true);
                break;
            case 2:
                meRadioButton.setChecked(true);
                break;
        }
        mViewPager.setCurrentItem(position, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mhandler.sendEmptyMessageDelayed(0, 2000);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                System.exit(0);
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
