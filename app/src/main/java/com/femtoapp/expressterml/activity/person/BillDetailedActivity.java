package com.femtoapp.expressterml.activity.person;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.femtoapp.expressterml.BaseActivity;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.adapter.account.BillDtdTabAdapter;
import com.femtoapp.expressterml.fragment.account.IncomeDtdFragment;
import com.femtoapp.expressterml.fragment.account.WithdrawalsRcFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class BillDetailedActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private BillDtdTabAdapter mBillDtdTabAdapter;
    public static final String[] tabTitle = new String[]{"收入明细", "提现记录"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detailed);


        //初始化控件
        initView();

        //初始化事件
        initEvents();
    }

    private void initView() {
        mTableLayout = (TabLayout) findViewById(R.id.tl_tabs);
        mViewPager = (ViewPager) findViewById(R.id.vp_viewPager);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        mTitles = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            mTitles.add(tabTitle[i]);
        }
    }

    private void initEvents() {
        iv_back.setOnClickListener(this);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(IncomeDtdFragment.newInstance(1));
        fragments.add(WithdrawalsRcFragment.newInstance(1));
        mBillDtdTabAdapter = new BillDtdTabAdapter(this.getSupportFragmentManager(), fragments, mTitles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mBillDtdTabAdapter);
        //将TabLayout和ViewPager关联
        mTableLayout.setupWithViewPager(mViewPager);
        //设置可以滑动
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);

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
