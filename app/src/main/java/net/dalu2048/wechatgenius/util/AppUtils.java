/*
 * ************************************************************
 * 文件：AppUtils.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月20日 11:37:49
 * 上次修改时间：2018年08月20日 11:37:49
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package net.dalu2048.wechatgenius.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public final class AppUtils {
    //安装包
    public static final String PACKAGE_NAME_WECHAT = "com.tencent.mm";
    public static final String PACKAGE_NAME_XPOSED = "de.robv.android.xposed.installer";

    //获取当前APP的版本
    public static String getVersionName(Context context) {
        //PackageManager 可以获取清单文件中的所有信息
        PackageManager manager = context.getPackageManager();
        try {
            //getPackageName()获取到当前程序的包名
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取当前APP的versionCode
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            return manager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //获取APP的安装版本号。未安装，返回空字符串。
    public static String getAppVersionName(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageInfo.packageName.equals(packageName)) {
                return packageInfo.versionName;
            }
        }

        return "";
    }

    //Xposed模块是否激活。默认返回false；激活后hook将其值改为true
    public static boolean isModuleActive() {
        return false;
    }
}
