package com.easyflower.testvideo.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.easyflower.testvideo.LogUtil;
import com.easyflower.testvideo.MyApplication;
import com.easyflower.testvideo.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名：FindFlowers
 * 作者： 郑伟
 * 时间：2017/4/17 11:26
 * 作用：访问网络帮助类
 */

public class HttpUtil {
    private static String msg;

    /**
     * 网络访问失败需要调用的方法
     *
     * @param context  上下文对象
     * @param TAG      当前页面名字
     * @param errorMsg 错误信息
     * @param handler
     */
    public static void connectFailure(final Context context, String TAG, final String errorMsg, Handler handler) {
        LogUtil.i(" --------------网络访问失败，原因是：" + errorMsg);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getInstance().getIsShowError()) {
                    ToastUtil.showToast(context, "网络访问失败！原因是：" + errorMsg);
                } else {
                    ToastUtil.showToast(context, "网络访问失败！");
                }
            }
        });
    }


    /**
     * 网络访问成功执行的方法 做了如下处理
     * 1、解析不为空，并且success为true或者false的不同处理
     * 2、通知用户登陆超时和其他处理
     *
     * @param context
     * @param handler
     * @return
     */
    public static boolean isConnectResponse(final Context context, String jsonStr, Handler handler) {

        if (jsonStr != null) {
            //json = jsonStr;
            JSONObject jsonObject;
            boolean success = true;
            try {
                jsonObject = new JSONObject(jsonStr);
                success = jsonObject.getBoolean("success");
                if (!success) msg = jsonObject.getString("msg");
                //LogUtil.show("success-->" + success + "msg-->" + msg);
            } catch (final JSONException e) {
                final String finalJsonStr = jsonStr;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (MyApplication.getInstance().getIsShowError()) {
                            ToastUtil.showToast(context, "返回数据异常:" + finalJsonStr + "，异常为：" + e);
                        } else {
                            ToastUtil.showToast(context, "返回数据异常:" + finalJsonStr);
                        }
                    }
                });
            }

            if (success == false && msg != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(context, msg);
                    }
                });
                if (msg.equals("未登录")) {
                    //Intent intent = new Intent(context, LoginActivity.class);
                    //context.startActivity(intent);
                    return false;
                }
            }
            return success;
        } else {
            return false;
        }
    }


    public static int CurrentNetState(Activity act) {
        try {
            ConnectivityManager manager = (ConnectivityManager) act
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null) {// 网络关闭
                return Constants.NET_TYPE_OFF;
            } else {// 网络开启
// 判断网络是wifi还是mobile
                NetworkInfo wifi = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (wifi != null && wifi.isConnected()) {
                    return Constants.NET_TYPE_WIFI;
                }
                NetworkInfo mobile = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mobile != null && mobile.isConnected()) {
                        return Constants.NET_TYPE_MOBILE;
                }
            }
        } catch (Exception e) {

        }
        return 0;
    }
}
