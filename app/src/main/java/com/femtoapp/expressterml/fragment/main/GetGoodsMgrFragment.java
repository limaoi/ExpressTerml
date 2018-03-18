package com.femtoapp.expressterml.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.femtoapp.expressterml.R;
import com.femtoapp.expressterml.activity.other.MyApplication;
import com.femtoapp.expressterml.adapter.get.GetGoodsMgrAdapter;
import com.femtoapp.expressterml.fragment.BaseFragment;
import com.femtoapp.expressterml.fragment.get.AllOrdersFragment;
import com.femtoapp.expressterml.fragment.get.AlreadyPickUpFragment;
import com.femtoapp.expressterml.fragment.get.NotPickUpFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Autism on 2018/1/22.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class GetGoodsMgrFragment extends BaseFragment {

    private static final String TAG = "GetGoodsMgrFragment";
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    private TextView tv_location;
    private List<String> mTitles;
    private GetGoodsMgrAdapter mGetGoodsMgrAdapter;
    public static final String[] tabTitle = new String[]{"全部", "待取件", "已取件"};

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    tv_location.setText(aMapLocation.getDistrict());
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_goods_mgr, container, false);

        initView(view);

        initEvent();

        return view;
    }

    private void initView(View view) {
        mTableLayout = (TabLayout) view.findViewById(R.id.tl_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_viewPager);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
    }

    private void initEvent() {

        getLocation();

        mTitles = new ArrayList<>();
        for (int i = 0; i < tabTitle.length; i++) {
            mTitles.add(tabTitle[i]);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(AllOrdersFragment.newInstance(1));
        fragments.add(NotPickUpFragment.newInstance(1));
        fragments.add(AlreadyPickUpFragment.newInstance(1));
        mGetGoodsMgrAdapter = new GetGoodsMgrAdapter(this.getChildFragmentManager(), fragments, mTitles);
        //给ViewPager设置适配器
        mViewPager.setAdapter(mGetGoodsMgrAdapter);
        //将TabLayout和ViewPager关联
        mTableLayout.setupWithViewPager(mViewPager);
        //设置可以滑动
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private void getLocation() {
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
}
