package com.easyflower.testvideo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;


/**
 * 项目名：FindFlowers
 * 时间：2017/4/11 17:51
 * 作用：
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public static MyApplication softApplication;
    private static Context mContext;
    private String versionName;
    private boolean isShowError;


    /**
     * 返回全局上下文对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        instance = this;
        softApplication = this;

        //获取版本号
        try {
            versionName = getPackageManager().getPackageInfo(
                    "com.easyflower.testvideo", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取版本号
     *
     * @return 版本号
     */
    public String getVersionName() {
        if (versionName != null) {
            return versionName;
        }
        return "";
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }


    /**
     * 获取是否展示错误信息
     *
     * @return
     */
    public boolean getIsShowError() {
        return isShowError;
    }


}
