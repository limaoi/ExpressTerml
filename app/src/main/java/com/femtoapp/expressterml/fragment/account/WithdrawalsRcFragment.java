package com.femtoapp.expressterml.fragment.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.adapter.account.WithdrawalsRcRecyAdapter;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Acount;
import com.femtoapp.expressterml.bean.Pay;
import com.femtoapp.expressterml.fragment.BaseFragment;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Autism on 2018/2/9.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class WithdrawalsRcFragment extends BaseFragment {

    private static final String TAG = "WithdrawalsRcFragment";

    private RecyclerView rv_recyclerView;
    private WithdrawalsRcRecyAdapter mWithdrawalsRcRecyAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Pay> mPayList = new ArrayList<>();

    private boolean isViewCreated;//Fragment的View加载完毕的标记
    private boolean isUIVisible; //Fragment对用户可见的标记

    private SharedPreferencesHelper sharedPreferencesHelper;
    private String USER_ID;
    private Boolean isLogin;
    private RelativeLayout empty;

    public static WithdrawalsRcFragment newInstance(int type) {
        WithdrawalsRcFragment fragment = new WithdrawalsRcFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (isViewCreated && isUIVisible) {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawals_rc, container, false);

        initView(view);

        initEvent();

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        sharedPreferencesHelper = new SharedPreferencesHelper(
                getActivity(), "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
            }
        }
        lazyLoad();
    }


    public void initView(View view) {
        rv_recyclerView = (RecyclerView) view.findViewById(R.id.rv_recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_ly);
        empty = (RelativeLayout) view.findViewById(R.id.empty);
    }

    private void initEvent() {

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        mPayList.clear();
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("USER_ID", USER_ID).build();
        Request request = new Request.Builder()
                .post(body)
                .url(api.TXQUERY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "请求失败，请重试！", Toast.LENGTH_SHORT).show();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    Gson gson = new Gson();
                    Pay pay = gson.fromJson(str, Pay.class);
                    List<Pay> payBeanList = pay.getResult_data();
                    if (pay.getStatus() == 0) {
                        if (payBeanList != null) {
                            for (Pay p : payBeanList) {
                                Pay pay1 = new Pay(p.getApply_time(), p.getMoney(), p.getApply_status());
                                mPayList.add(pay1);
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(getContext());
                                rv_recyclerView.setLayoutManager(LinearLayoutManager);
                                mWithdrawalsRcRecyAdapter = new WithdrawalsRcRecyAdapter(mPayList);
                                rv_recyclerView.setAdapter(mWithdrawalsRcRecyAdapter);
                                if (mPayList.size() == 0) {
                                    empty.setVisibility(View.VISIBLE);
                                } else {
                                    empty.setVisibility(View.GONE);
                                }
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }
            }
        });
    }

}
