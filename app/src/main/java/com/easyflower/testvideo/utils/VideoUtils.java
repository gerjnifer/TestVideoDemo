package com.easyflower.testvideo.utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Formatter;
import java.util.Locale;

public class VideoUtils {

    public static String stringForTime(int timeMs) {

    StringBuilder mFormatterBuilder = new StringBuilder();
    Formatter mFormatter = new Formatter(mFormatterBuilder, Locale.getDefault());

        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60; //换成秒
        int minutes = (totalSeconds / 60) % 60;
        int hours = (totalSeconds / 3600);
        mFormatterBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    // 去出图片 这是之前写好的 不是自己的。
    public static String getPicPath(Activity act, Uri uri) {
        String[] proj = {MediaStore.Video.Media.DATA};
        // 好像是android多媒体数据库的封装接口，具体的看Android文档
        Cursor cursor =act.managedQuery(uri, proj, null, null, null);
        // 按我个人理解 这个是获得用户选择的图片的索引值
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);

        // 将光标移至开头 ，这个很重要，不小心很容易引起越界

        cursor.moveToFirst();

        // 最后根据索引值获取图片路径

        String path = cursor.getString(column_index);

        return path;
    }

}
