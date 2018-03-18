package com.femtoapp.expressterml.fragment.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.femtoapp.expressterml.MainActivity;
import com.femtoapp.expressterml.activity.other.LoginActivity;
import com.femtoapp.expressterml.activity.person.AccountInfoActivity;
import com.femtoapp.expressterml.activity.person.ContactHdqActivity;
import com.femtoapp.expressterml.activity.person.DotInfoActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.person.wallet.WalletActivity;
import com.femtoapp.expressterml.fragment.BaseFragment;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class MeMgrFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout accountInfoLayt;
    private RelativeLayout walletLayt;
    private RelativeLayout contactHdqLayt;
    private RelativeLayout dotInfoLayt;
    private TextView tv_nickName;
    private TextView tv_phoneNumber;
    private TextView tv_isLogin;
    private Button btn_logout;
    private String user_id;
    private SharedPreferencesHelper sharedPreferencesHelper;


    @Override
    public void onStart() {
        super.onStart();
        sharedPreferencesHelper = new SharedPreferencesHelper(
                getActivity(), "sp_data");

        if (sharedPreferencesHelper.contain("isLogin")) {
            Boolean isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                tv_isLogin.setVisibility(View.GONE);
                tv_nickName.setText(sharedPreferencesHelper.getSharedPreference("USER_NAME", "").toString().trim());
                tv_phoneNumber.setText(sharedPreferencesHelper.getSharedPreference("BYNAME", "").toString().trim());
                user_id = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
                tv_nickName.setVisibility(View.VISIBLE);
                tv_phoneNumber.setVisibility(View.VISIBLE);
                btn_logout.setVisibility(View.VISIBLE);
            } else {
                tv_isLogin.setVisibility(View.VISIBLE);
                btn_logout.setVisibility(View.GONE);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_mgr, container, false);

        initView(view);

        initEvent();

        return view;
    }

    private void initView(View view) {
        accountInfoLayt = (RelativeLayout) view.findViewById(R.id.rl_account_info);
        walletLayt = (RelativeLayout) view.findViewById(R.id.rl_wallet);
        contactHdqLayt = (RelativeLayout) view.findViewById(R.id.rl_contact_headquarters);
        dotInfoLayt = (RelativeLayout) view.findViewById(R.id.rl_dot_info);
        tv_nickName = (TextView) view.findViewById(R.id.tv_nickName);
        tv_phoneNumber = (TextView) view.findViewById(R.id.tv_phoneNumber);
        tv_isLogin = (TextView) view.findViewById(R.id.tv_isLogin);
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
    }

    private void initEvent() {
        accountInfoLayt.setOnClickListener(this);
        walletLayt.setOnClickListener(this);
        contactHdqLayt.setOnClickListener(this);
        dotInfoLayt.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        tv_isLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_account_info:
                Intent intent = new Intent(getActivity(), AccountInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_wallet:
                Intent intent2 = new Intent(getActivity(), WalletActivity.class);
                startActivity(intent2);
                break;
            case R.id.rl_contact_headquarters:
                Intent intent3 = new Intent(getActivity(), ContactHdqActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_dot_info:
                Intent intent4 = new Intent(getActivity(), DotInfoActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_logout:
                showlogoutDialog();
                break;
            case R.id.tv_isLogin:
                Intent intent5 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent5);
                break;
        }
    }

    private void showlogoutDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("温馨提示");
        normalDialog.setMessage("确定退出登录?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPreferencesHelper.clear();
                        if (sharedPreferencesHelper.contain("isLogin")) {
                            tv_isLogin.setVisibility(View.GONE);
                            tv_nickName.setVisibility(View.VISIBLE);
                            tv_phoneNumber.setVisibility(View.VISIBLE);
                            btn_logout.setVisibility(View.VISIBLE);
                        } else {
                            tv_nickName.setVisibility(View.GONE);
                            tv_phoneNumber.setVisibility(View.GONE);
                            tv_isLogin.setVisibility(View.VISIBLE);
                            btn_logout.setVisibility(View.GONE);
                        }
                        Intent intent3 = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent3);
                        getActivity().finish();
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
}
