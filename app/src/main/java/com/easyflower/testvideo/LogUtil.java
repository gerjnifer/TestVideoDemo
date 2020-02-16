package com.easyflower.testvideo;

import android.util.Log;

/**
 * 时间：2016/12/22 19:10
 */

public class LogUtil {

    private static boolean isPrint = true;

    private static boolean isTo = true;

    public static void show(String msg) {
        if (isPrint) {
            Log.i("------>", msg);
        }
    }

    public static void show(String act, String msg) {
        if (isPrint) {
            Log.i("-->>", act + "   " + msg);
        }
    }

    public static void i(String msg) {
        if (isTo) {
            if (msg.length() > 3000) {
                for (int i = 0; i < msg.length(); i += 3000) {
                    //当前截取的长度<总长度则继续截取最大的长度来打印
                    if (i + 3000 < msg.length()) {
                        Log.i("--------------" + i, msg.substring(i, i + 3000));
                    } else {
                        //当前截取的长度已经超过了总长度，则打印出剩下的全部信息
                        Log.i("--------------" + i, msg.substring(i, msg.length()));
                    }
                }
            } else {
                //直接打印
                Log.i("--------------", msg);
            }
        }



    }

    /*public static void show(String msg, String tag) {
        if (isPrint) {
            Log.i(tag, msg);
        }
    }*/

}
