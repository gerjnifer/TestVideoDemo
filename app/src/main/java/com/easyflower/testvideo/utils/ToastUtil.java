package com.easyflower.testvideo.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.easyflower.testvideo.LogUtil;

/**
 * 项目名：EasyFlowersAS
 * 作者： 郑伟
 * 时间：2017/1/4 16:11
 * 作用：吐司工具类，防止重复展示，默认短展示，特殊需求请自己填写
 */

public class ToastUtil {
    private static Toast mToast = null;

    /**
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }

        mToast.show();
    }

    /**
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }

        mToast.show();
    }


    /**
     * 专门用于处理访问失败时用的方法
     *
     * @param act
     * @param TAG 标记
     * @param e   错误信息
     */
    public static void showFailureToast(String TAG, Activity act, Exception e) {
        LogUtil.show(TAG + "访问失败：" + e);
        if (mToast == null) {
            mToast = Toast.makeText(act, "访问错误：" + e, Toast.LENGTH_SHORT);
        } else {
            mToast.setText("访问错误：" + e);
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.show();
            }
        });

    }


    /**
     * 专门用于处理访问失败时用的方法
     * @param act
     * @param e
     */
    public static void showFailureToast(Activity act, Exception e) {
        LogUtil.show("访问失败：" + e);
        if (mToast == null) {
            mToast = Toast.makeText(act, "访问错误：" + e, Toast.LENGTH_SHORT);
        } else {
            mToast.setText("访问错误：" + e);
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.show();
            }
        });

    }
}
