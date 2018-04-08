package com.example.okhttp30_demo;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * 爱生活，爱代码
 * 创建于：2018/4/3 15:33
 * 作 者：T
 * 微信：704003376
 */

public class MyApp extends Application {
    public static OkHttpClient mClient;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mClient == null) {
            mClient = new OkHttpClient();
        }
    }
}
