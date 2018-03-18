package com.femtoapp.expressterml.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.adapter.send.SendGoodsMgrAdapter;
import com.femtoapp.expressterml.fragment.BaseFragment;
import com.femtoapp.expressterml.fragment.send.AllSendOrdersFragment;
import com.femtoapp.expressterml.fragment.send.AlreadySignFragment;
import com.femtoapp.expressterml.fragment.send.NotSignFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class SendGoodsMgrFragment extends BaseFragment {

    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private List<String> mTitles;
    private SendGoodsMgrAdapter mSendGoodsMgrAdapter;
    public static final String[] tabTitle = new String[]{"全部", "未签收", "已签收"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_goods_mgr, container, false);

        initView(view);

        initEvent();

        return view;
    }

    private void initView(View view) {
        mTableLayout = (TabLayout) view.findViewById(R.id.tl_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_viewPager);
    }

    private void initEvent() {
        mTitles = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            mTitles.add(tabTitle[i]);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AllSendOrdersFragment.newInstance(1));
        fragments.add(NotSignFragment.newInstance(1));
        fragments.add(AlreadySignFragment.newInstance(1));
        mSendGoodsMgrAdapter = new SendGoodsMgrAdapter(this.getChildFragmentManager(), fragments, mTitles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mSendGoodsMgrAdapter);
        //将TabLayout和ViewPager关联
        mTableLayout.setupWithViewPager(mViewPager);
        //设置可以滑动
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
