/*
 * ************************************************************
 * 文件：OkHttpUtils.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月20日 16:05:59
 * 上次修改时间：2018年08月20日 16:05:59
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package net.dalu2048.wechatgenius.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public final class OkHttpUtils {
    private static final long DEFAULT_READ_TIMEOUT_MILLIS = 15 * 1000;
    private static final long DEFAULT_WRITE_TIMEOUT_MILLIS = 20 * 1000;
    private static final long DEFAULT_CONNECT_TIMEOUT_MILLIS = 20 * 1000;
    private static volatile OkHttpUtils sInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpUtils() {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkHttpUtils getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (sInstance == null) {
                    sInstance = new OkHttpUtils();
                }
            }
        }
        return sInstance;
    }

    //单例获取OkHttpClient实例
    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
