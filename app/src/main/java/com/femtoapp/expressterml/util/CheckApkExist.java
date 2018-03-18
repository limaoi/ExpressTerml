package com.femtoapp.expressterml.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Autism on 2018/2/8.
 * I am a SuperMan
 * Email:liaoweihai14@s.nuit.edu.cn
 */

public class CheckApkExist {

    private static String GaoDe = "com.autonavi.minimap";

    public static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.i("TAG", info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TAG", e.toString());
            return false;
        }
    }

    public static boolean checkGaoDEExist(Context context) {
        return checkApkExist(context, GaoDe);
    }
    // 剩余的可以自行扩展，下边会给出一些常用的包名
}
