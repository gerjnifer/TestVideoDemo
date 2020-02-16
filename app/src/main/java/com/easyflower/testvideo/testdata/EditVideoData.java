package com.easyflower.testvideo.testdata;

import android.graphics.Bitmap;

/**
 * Created by ZBK on 2017/10/13.
 * Describe:保存解码视频图片帧的信息
 */

public class EditVideoData {

    public EditVideoData(int seconds, Bitmap bitmap) {
        this.seconds = seconds;
        this.bitmap = bitmap;
    }

    private int seconds;//当前帧的图片在视频中的时间
    private Bitmap bitmap;//当前帧图片保存到本地的名字 格式为temp+seconds+.jpg

    private byte[] bytes;

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
