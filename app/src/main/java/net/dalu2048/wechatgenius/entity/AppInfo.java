/*
 * ************************************************************
 * 文件：AppInfo.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月20日 11:38:43
 * 上次修改时间：2018年08月20日 11:38:42
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package net.dalu2048.wechatgenius.entity;

import android.content.Context;

import net.dalu2048.wechatgenius.util.AppUtils;
import net.dalu2048.wechatgenius.util.StringUtils;

public final class AppInfo {
    //Xposed是否安装
    private boolean mIsXposedInstall;
    private String mXposedVersionName;

    //region 单例
    //单例
    private static volatile AppInfo mInstance = null;

    private AppInfo() {
    }

    public static AppInfo getInstance() {
        if (mInstance == null) {
            synchronized (AppInfo.class) {
                if (mInstance == null) {
                    mInstance = new AppInfo();
                }
            }
        }
        return mInstance;
    }

    //endregion
    //检测设备环境
    public void ValidateEnvironment(Context context) {
        //判断Xposed是否安装
        mXposedVersionName = AppUtils.getAppVersionName(context, AppUtils.PACKAGE_NAME_XPOSED);
        mIsXposedInstall = !StringUtils.isEmpty(getXposedVersionName());
    }

    //获取Xposed模块是否激活
    public boolean isXposedActive() {
        return AppUtils.isModuleActive();
    }

    public boolean isXposedInstall() {
        return mIsXposedInstall;
    }

    public void setXposedInstall(boolean xposedInstall) {
        mIsXposedInstall = xposedInstall;
    }

    public String getXposedVersionName() {
        return mXposedVersionName;
    }

    public void setXposedVersionName(String xposedVersionName) {
        mXposedVersionName = xposedVersionName;
    }
}
