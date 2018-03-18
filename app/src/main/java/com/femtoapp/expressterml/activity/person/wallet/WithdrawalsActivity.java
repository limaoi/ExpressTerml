package com.femtoapp.expressterml.activity.person.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Acount;
import com.femtoapp.expressterml.bean.Dot;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.mingle.widget.LoadingView;

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
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class WithdrawalsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "WithdrawalsActivity";
    private EditText et_balance_value;
    private ImageView iv_delete;
    private TextView tv_account_name_value;
    private TextView tv_account_card_value;
    private TextView tv_account_bank_value;
    private TextView tv_account_bank_address_value;
    private ProgressBar loadView;
    private LinearLayout ll_tx_content;
    private Button bt_withdrawals;
    private TextView tv_available_balance_value;
    private TextView tv_all_withdrawals;
    private ImageView iv_back;

    private String money;
    private String bank;
    private String account_name;
    private String account_number;
    private String account_address;

    private String apply_time;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String USER_ID;
    private Boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawals);

        //初始化控件
        initView();

        //初始化事件
        initEvents();

        showWalletNum();
    }

    private void initView() {
        et_balance_value = (EditText) findViewById(R.id.et_balance_value);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_account_name_value = (TextView) findViewById(R.id.tv_account_name_value);
        tv_account_card_value = (TextView) findViewById(R.id.tv_account_card_value);
        tv_account_bank_value = (TextView) findViewById(R.id.tv_account_bank_value);
        tv_account_bank_address_value = (TextView) findViewById(R.id.tv_account_bank_address_value);
        loadView = (ProgressBar) findViewById(R.id.loadView);
        ll_tx_content = (LinearLayout) findViewById(R.id.ll_tx_content);
        bt_withdrawals = (Button) findViewById(R.id.bt_withdrawals);
        tv_available_balance_value = (TextView) findViewById(R.id.tv_available_balance_value);
        tv_all_withdrawals = (TextView) findViewById(R.id.tv_all_withdrawals);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    private void initEvents() {
        bt_withdrawals.setOnClickListener(this);
        tv_all_withdrawals.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        et_balance_value.findFocus();
        et_balance_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    iv_delete.setVisibility(View.VISIBLE);
                } else {
                    iv_delete.setVisibility(View.GONE);
                }
            }
        });
        iv_delete.setOnClickListener(this);
    }

    private void showWalletNum() {
        sharedPreferencesHelper = new SharedPreferencesHelper(
                this, "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
            }
        }
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
                        Toast.makeText(WithdrawalsActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
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
                                tv_available_balance_value.setText(acount.getMoney() + "");
                            }
                        });
                        loadData();
                    }
                }
            }
        });
    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("USER_ID", USER_ID).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.TXAPPLY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WithdrawalsActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i(TAG, "str:" + str);
                    Gson gson = new Gson();
                    Acount acount = gson.fromJson(str, Acount.class);
                    if (acount.getStatus() == 0) {
                        bank = acount.getResult_data().getBank();
                        account_name = acount.getResult_data().getAccount_name();
                        account_number = acount.getResult_data().getAccount_number();
                        account_address = acount.getResult_data().getAccount_address();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_account_name_value.setText(account_name);
                                tv_account_card_value.setText(account_number);
                                tv_account_bank_value.setText(bank);
                                tv_account_bank_address_value.setText(account_address);
                                ll_tx_content.setVisibility(View.VISIBLE);
                                loadView.setVisibility(View.GONE);
                            }
                        });
                    }
                }
            }
        });
    }

    private void getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        apply_time = formatter.format(curDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete:
                et_balance_value.setText("");
                break;
            case R.id.bt_withdrawals:
                money = et_balance_value.getText().toString();
                getTime();
                if (et_balance_value.getText().toString().length() == 0) {
                    Toast.makeText(this, "提现金额不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (Float.valueOf(et_balance_value.getText().toString()) > Float.valueOf(tv_available_balance_value.getText().toString())) {
                        Toast.makeText(this, "提现金额不能大于可用金额", Toast.LENGTH_SHORT).show();
                    } else {
                        txCommit();
                    }
                }
                break;
            case R.id.tv_all_withdrawals:
                et_balance_value.setText(tv_available_balance_value.getText().toString());
                et_balance_value.setSelection(et_balance_value.getText().length());
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void txCommit() {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("USER_ID", USER_ID).add("money", money).add("apply_time", apply_time).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.TXSUBMIT)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WithdrawalsActivity.this, "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Log.i(TAG, "str:" + str);
                    Gson gson = new Gson();
                    Dot dot = gson.fromJson(str, Dot.class);
                    if (dot.getStatus() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(WithdrawalsActivity.this, "提现申请成功！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
}
