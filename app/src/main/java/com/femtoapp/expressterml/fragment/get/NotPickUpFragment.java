package com.femtoapp.expressterml.fragment.get;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.adapter.get.NotPuRecyAdapter;
import com.femtoapp.expressterml.api.api;
import com.femtoapp.expressterml.bean.Order;
import com.femtoapp.expressterml.fragment.BaseFragment;
import com.femtoapp.expressterml.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Autism on 2018/1/31.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class NotPickUpFragment extends BaseFragment {
    private static final String TAG = "NotPickUpFragment";
    private int type;
    private RelativeLayout empty;
    private RecyclerView rv_recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NotPuRecyAdapter mNotPuRecyAdapter;
    private ArrayList<Order> mOrderList = new ArrayList<>();
    private boolean isViewCreated;//Fragment的View加载完毕的标记
    private boolean isUIVisible; //Fragment对用户可见的标记
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String USER_ID;
    private Boolean isLogin;

    public static NotPickUpFragment newInstance(int type) {
        NotPickUpFragment fragment = new NotPickUpFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferencesHelper = new SharedPreferencesHelper(
                getActivity(), "sp_data");
        if (sharedPreferencesHelper.contain("isLogin")) {
            isLogin = (Boolean) sharedPreferencesHelper.getSharedPreference("isLogin", false);
            if (isLogin) {
                USER_ID = sharedPreferencesHelper.getSharedPreference("USER_ID", "").toString().trim();
            }
        }
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

    private void loadData() {
        mOrderList.clear();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(api.LANSHOU + "?USER_ID=" + USER_ID + "&status=2")
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
                    Log.i(TAG, "str:" + str);
                    List<Order> orderList = new ArrayList<Order>();
                    Type type = new TypeToken<ArrayList<Order>>() {
                    }.getType();
                    orderList = gson.fromJson(str, type);
                    for (Order o : orderList) {
                        Order order = new Order(o.getId(),
                                o.getF_cFromPerson(),
                                o.getF_cFromTel(),
                                o.getF_cFromStation(),
                                o.getF_cFromAddress(),
                                o.getF_cFromAddress_port(),
                                o.getF_cToPerson(),
                                o.getF_cToTel(),
                                o.getF_cToStation(),
                                o.getF_cToAddress(),
                                o.getF_cToAddress_port(),
                                o.getF_cGoods(),
                                o.getF_nPiece(),
                                o.getF_nWeight(),
                                o.getNlong(),
                                o.getNwidth(),
                                o.getNheight(),
                                o.getF_nCubicMetre(),
                                o.getF_cOrderNumber(),
                                o.getStatus(),
                                o.getF_nMoney());
                        mOrderList.add(order);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(getContext());
                            rv_recyclerView.setLayoutManager(LinearLayoutManager);
                            mNotPuRecyAdapter = new NotPuRecyAdapter(mOrderList);
                            rv_recyclerView.setAdapter(mNotPuRecyAdapter);
                            if (mOrderList.size() == 0) {
                                empty.setVisibility(View.VISIBLE);
                            } else {
                                empty.setVisibility(View.GONE);
                            }
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (int) getArguments().getSerializable(TAG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_pu, container, false);

        initView(view);

        initEvent();

        return view;
    }

    private void initView(View view) {
        rv_recyclerView = (RecyclerView) view.findViewById(R.id.rv_recyclerView);
        empty = (RelativeLayout) view.findViewById(R.id.empty);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_ly);
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }
}
