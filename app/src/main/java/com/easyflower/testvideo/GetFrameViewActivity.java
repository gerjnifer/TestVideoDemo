package com.easyflower.testvideo;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import wseemann.media.FFmpegMediaMetadataRetriever;

import static android.media.MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Flexible;

public class GetFrameViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        init();

        getFrame1();

        getFrame2();
    }


    private void init() {

        List<Bitmap> bitmapList = new ArrayList<>();

//new出对象
        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();

//设置数据源
        mmr.setDataSource("/storage/emulated/0/DCIM/Camera/VID_20191028_124338.mp4");

//获取媒体文件的专辑标题
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);

//获取媒体文件的专辑艺术家
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);

        String duration = mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_DURATION);
        LogUtil.show(" ----------------------- duration = " + duration);

        long lDruation = Long.parseLong(duration);

        for (int i = 0; i < lDruation; i += 1000) {
            LogUtil.show(" --------------- i= " + i + "    i*1000 = " + i * 1000);
            Bitmap bitmap = mmr.getFrameAtTime(i * 1000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
            bitmapList.add(bitmap);
        }

//获取2秒处的一帧图片（这里的2000000是微秒！！！）
//        Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
//释放资源
        mmr.release();

        for (int i = 0; i < bitmapList.size(); i++) {
            LogUtil.show("---------------- bitmapList= " + bitmapList.get(i).toString());
        }
    }


    private void getFrame1() {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource("/storage/emulated/0/DCIM/Camera/VID_20191028_124338.mp4");

        String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        LogUtil.show("------------------------------ duration = " + duration);
        int durationMs = Integer.parseInt(duration);

        //每秒取一次
        for (int i = 0; i < durationMs; i += 1000) {
            long start = System.nanoTime();
            LogUtil.show("-----------------------------  getFrameAtTime time = " + i);
            //这里传入的是ms
            Bitmap frameAtIndex = metadataRetriever.getFrameAtTime(i * 1000);
            Bitmap frame = Bitmap.createScaledBitmap(frameAtIndex, frameAtIndex.getWidth() / 8, frameAtIndex.getHeight() / 8, false);
            frameAtIndex.recycle();
            long end = System.nanoTime();
            long cost = end - start;
            LogUtil.show("---------------------------------  cost time in millis = " + (cost * 1f / 1000000));

            LogUtil.show(" ------------------  show bitmap = " + frame);
        }
        metadataRetriever.release();
    }


    private void getFrame2() {
    }
}
