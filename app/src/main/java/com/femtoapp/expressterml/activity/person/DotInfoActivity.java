package com.femtoapp.expressterml.activity.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.adapter.other.DotInfoRecyAdapter;
import com.femtoapp.expressterml.bean.Dot;

import java.util.ArrayList;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class DotInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private RecyclerView rv_recyclerView;
    private DotInfoRecyAdapter mDotInfoRecyAdapter;
    private ArrayList<Dot> mDotList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_info);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }


    private void initView() {
        rv_recyclerView = (RecyclerView) findViewById(R.id.rv_recyclerView);
        iv_back = (ImageView) findViewById(R.id.iv_back);
    }

    private void initEvents() {
        iv_back.setOnClickListener(this);

        for (int i = 0; i < 5; i++) {
            Dot dot = new Dot("广东深圳公司福田区中电分部", "八丁街18号", "1.34km");
            mDotList.add(dot);
        }

        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(this);
        rv_recyclerView.setLayoutManager(LinearLayoutManager);
        mDotInfoRecyAdapter = new DotInfoRecyAdapter(mDotList);
        rv_recyclerView.setAdapter(mDotInfoRecyAdapter);
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
