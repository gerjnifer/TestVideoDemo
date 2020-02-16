package com.easyflower.testvideo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.easyflower.testvideo.adapter.VideoFrameImageHorizontalAdapter;
import com.easyflower.testvideo.testA.OutputImageFormat;
import com.easyflower.testvideo.testA.VideoToFrames;
import com.easyflower.testvideo.videoclip.MediaMetadataRetrieverWrapper;
import com.easyflower.testvideo.videoclip.RetrieverProcessThread;

import java.util.ArrayList;
import java.util.List;

public class GetFrameViewActivity3 extends AppCompatActivity {

    TextView start;

    RecyclerView video_img_horizontal;
    VideoFrameImageHorizontalAdapter adapter;

    private List<Bitmap> bitmapList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_frame3);

        video_img_horizontal = findViewById(R.id.video_img_horizontal);
        start = findViewById(R.id.start);

        initRes();

        setClickListener();

    }


    private void initRes() {
        bitmapList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GetFrameViewActivity3.this);
//
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        video_img_horizontal.setLayoutManager(linearLayoutManager);

        adapter = new VideoFrameImageHorizontalAdapter(GetFrameViewActivity3.this, bitmapList);
        video_img_horizontal.setAdapter(adapter);

    }


    private void setClickListener() {

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String targetPath = "/storage/emulated/0/DCIM/Camera/VID_20191028_124338.mp4";

                MediaMetadataRetrieverWrapper metadataRetriever2 = new MediaMetadataRetrieverWrapper();
                metadataRetriever2.setDataSource(targetPath);
                metadataRetriever2.getFramesInterval(1000, 4, new RetrieverProcessThread.BitmapCallBack() {
                    @Override
                    public void onComplete(final Bitmap frame2) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                LogUtil.show(" --------------------- bitmapList= " + bitmapList.toString());
                                LogUtil.show(" --------------------- frame2= " + frame2);

                                bitmapList.add(frame2);
                                adapter.setNewData(bitmapList);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        });
    }


}
