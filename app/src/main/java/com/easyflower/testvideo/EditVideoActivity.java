package com.easyflower.testvideo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import com.easyflower.testvideo.adapter.UploadEditVideoClipAdapter;
import com.easyflower.testvideo.constants.Constants;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.rangebar.RangeBar;
import com.easyflower.testvideo.testdata.EditVideoData;
import com.easyflower.testvideo.utils.UIUtil;
import com.easyflower.testvideo.utils.VideoUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditVideoActivity extends AppCompatActivity implements View.OnClickListener, RangeBar.OnRangeBarChangeListener {

    private TextView open_location_vedio;


    private RangeBar rangeBar;

    private RecyclerView video_img_horizontal;
    private UploadEditVideoClipAdapter imageAdapter;

    private String videoPath;


    private final int IMAGE_NUM = 10;//每一屏图片的数量宽度
    private final int IMAGE_COUNT_NUM = 180;//每一屏秒数


    private long videoTime;
    private int imagCount = 0;//整个视频要解码图片的总数量
    private LinearLayoutManager linearLayoutManager;
    private int firstItem = 0;//recycleView当前显示的第一项
    private int lastItem = 0;//recycleView当前显示的最后一项

    private int leftThumbIndex = 0;//滑动条的左端
    private int rightThumbIndex = IMAGE_COUNT_NUM;//滑动条的右端

    private int startTime, endTime = IMAGE_COUNT_NUM;//裁剪的开始、结束时间

    private MediaMetadataRetriever retriever;

    private VideoView uVideoView;

    private List<EditVideoData> adapterDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_video);

        retriever = new MediaMetadataRetriever();

        initView();

        initData();
    }


    private void initView() {

        uVideoView = findViewById(R.id.uVideoView);
        open_location_vedio = findViewById(R.id.open_location_vedio);

        rangeBar = findViewById(R.id.rangeBar);


        video_img_horizontal = findViewById(R.id.video_img_horizontal);

        open_location_vedio.setOnClickListener(this);
    }


    private void initData() {
        rangeBar.setmTickCount(IMAGE_COUNT_NUM + 1);
        rangeBar.setOnRangeBarChangeListener(this);//设置滑动条的监听

        video_img_horizontal.addOnScrollListener(onScrollListener);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_location_vedio:
                open();
                break;
        }
    }

    /**
     * 打开视频相册
     */
    private void open() {
        if (Build.VERSION.SDK_INT < 23) {
            turnToChooseVideo();
        } else {
            //6.0
            if (ContextCompat.checkSelfPermission(EditVideoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //该权限已经有了
                System.out.println("权限已经有了");
                turnToChooseVideo();
            } else {
                //申请该权限
                ActivityCompat.requestPermissions(EditVideoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x2222);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case 0x2222:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    turnToChooseVideo();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;


        }
    }

    private void turnToChooseVideo() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("vedio/*");
        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, Constants.CHOOLE_IMAGE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtil.i(" ------------- requestCode = " + requestCode);
        LogUtil.i(" ------------- resultCode = " + resultCode);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case Constants.CHOOLE_IMAGE: // 相册
                Uri uri = data.getData();
                LogUtil.i(" ------------- uri = " + uri);
                videoPath = VideoUtils.getPicPath(EditVideoActivity.this, uri);
                LogUtil.i(" ------------- videoPath = " + videoPath);

                updateUI(videoPath);

                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {//获取到图片总的显示范围的大小后，设置每一个图片所占有的宽度
            if (imageAdapter != null) {
                imageAdapter.setImagWidth(rangeBar.getMeasuredWidth() / IMAGE_NUM);
            }
        }
    }

    /**
     * 是否支持移动网络播放
     */
    private boolean supportMobileNet = false;

    /**
     * 更新UI
     *
     * @param videoPath
     */
    private void updateUI(String videoPath) {

//        videoTime = UIUtil.getVideoDuration(videoPath);



        retriever.setDataSource(videoPath);
        String s = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        videoTime = UIUtil.stringToLong(s);


        adapterDataList = getDataList(videoTime);

        LogUtil.show("------------- adapterDataList = " + adapterDataList.size());

        imageAdapter = new UploadEditVideoClipAdapter(this, adapterDataList);
        imageAdapter.setParentPath(videoPath);
        imageAdapter.setRotation(UIUtil.strToFloat(UIUtil.getVideoInf(videoPath)));

        imageAdapter.setImagWidth(rangeBar.getMeasuredWidth() / IMAGE_NUM);

        linearLayoutManager = new LinearLayoutManager(EditVideoActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 recyclerview 布局方式为
        video_img_horizontal.setLayoutManager(linearLayoutManager);

        video_img_horizontal.setAdapter(imageAdapter);

        uVideoView.setVideoPath(videoPath);
        uVideoView.start();

        new Thread(new MyThreadWithImplements(videoPath)).start();
    }

    private List<EditVideoData> getDataList(long videoTime) {
        List<EditVideoData> dataList = new ArrayList<>();
        int seconds = (int) (videoTime / Interval);
        for (imagCount = 0; imagCount < seconds; imagCount++) {
            dataList.add(new EditVideoData(imagCount, null));
        }
        return dataList;
    }


    private long Interval = 18 * 1000;




    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.i("onScrollStateChanged", "onScrollStateChanged :" + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                firstItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastItem = linearLayoutManager.findLastVisibleItemPosition();

                List<EditVideoData> dataList = imageAdapter.getDataList();
                // 滑动的距离过长的话  就加载相应位置的图片
                // 这里需要判断
//                runImagDecodTask(firstItem, lastItem - firstItem + 1);
            }
            calStartEndTime();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    /**
     * 运行一个图片的解码任务
     *
     * @param start 解码开始的视频时间 秒
     * @param count 一共解析多少张
     */
    private void runImagDecodTask(int start, int count) {

    }


    /**
     * 计算开始结束时间
     */
    private void calStartEndTime() {
        int duration = rightThumbIndex - leftThumbIndex;
        startTime = firstItem + leftThumbIndex;
        endTime = startTime + duration;

        //此时可能视频已经结束，若已结束重新start
        if (!uVideoView.isPlaying()) {
            uVideoView.start();
        }


        //把视频跳转到新选择的开始时间
        uVideoView.seekTo(startTime * 1000);
    }


    /**
     * rangeBar 的滑动监听
     *
     * @param rangeBar
     * @param leftThumbIndex
     * @param rightThumbIndex
     */
    @Override
    public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
        Log.i("onIndexChange", "leftThumbIndex:" + leftThumbIndex + "___rightThumbIndex:" + rightThumbIndex);
        this.leftThumbIndex = leftThumbIndex;
        this.rightThumbIndex = rightThumbIndex;
        calStartEndTime();
    }

    public class MyThreadWithImplements implements Runnable {

        private String videoPath;

        public MyThreadWithImplements(String videoPath) {
            this.videoPath = videoPath;
        }

        @Override
        public void run() {
            final List<EditVideoData> adapterDataList = toEditVideo(videoPath);

            for (int i = 0; i < adapterDataList.size(); i++) {
                LogUtil.show("------------------ bitmapList " + adapterDataList.size() + "  " + adapterDataList.get(i).getBitmap());
            }


        }

        /**
         * 拿到视频路径
         */
        private List<EditVideoData> toEditVideo(String url) {

            List<Bitmap> bitmapList = new ArrayList<>();

            Bitmap bitmap = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {

                retriever.setDataSource(url);
                String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                LogUtil.show(" ----------------------- duration = " + duration);

                long lDruation = Long.parseLong(duration);

                int size = adapterDataList.size();
                for (int i = 0; i < size; i++) {
                    LogUtil.show(" --------------- i= " + i + "    i*Interval = " + i * Interval);
                    bitmap = retriever.getFrameAtTime(i * Interval, MediaMetadataRetriever.OPTION_CLOSEST);

                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                    byte[] bytes = baos.toByteArray();

                    adapterDataList.get(i).setBitmap(bitmap);

                    adapterDataList.get(i).setBytes(bytes);

                    bitmapList.add(bitmap);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageAdapter.setNewData(adapterDataList);
                        }
                    });
                }

//获取2秒处的一帧图片（这里的2000000是微秒！！！）
//            Bitmap b = mmr.getFrameAtTime(2000000, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
            } catch (IllegalArgumentException ex) {

//            LogUtils.e(ex.toString());

//            LogUtils.d("nihao", "获取视频缩略图失败");

            } finally {

                try {

                    retriever.release();

                } catch (RuntimeException ex) {

//                LogUtils.e(ex.getMessage().toString());

//                LogUtils.d("nihao", "释放MediaMetadataRetriever资源失败");

                }
            }

            return adapterDataList;

        }
    }
}

