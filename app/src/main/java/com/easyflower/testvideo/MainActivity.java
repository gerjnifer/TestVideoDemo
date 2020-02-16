package com.easyflower.testvideo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import com.easyflower.testvideo.manager.PlayerManager;
import com.easyflower.testvideo.media.application.Settings;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class MainActivity extends AppCompatActivity implements PlayerManager.PlayerStateListener {


    private String url7 = "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1920x1080h.mp4";

    private IjkVideoView videoView;
    private TextView txt;
    private TableLayout hudview;

    private Settings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2);

//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


//        int a = 1 /0;
//
//        LogUtil.show("------------------ a = " + a);
//
//        for (int i = 0; i < 10; i++) {
//            String time = generateTime(0 + i);
//            LogUtil.show("----------------------- time= " + time);
//
//        }

//        initView();

//        Intent intent = new Intent(MainActivity.this, EditVideoActivity.class);


        Intent intent = new Intent(MainActivity.this, VideoEditActivity.class);


//        Intent intent = new Intent(MainActivity.this, MainActivity2.class);

//         Intent intent = new Intent(MainActivity.this,EditClipVideoActivity.class);
//        Intent intent = new Intent(MainActivity.this,VideoLineActivity.class);

//        Intent intent = new Intent(MainActivity.this,GetFrameViewActivity.class);
//        Intent intent = new Intent(MainActivity.this,GetFrameViewActivity2.class);
//        Intent intent = new Intent(MainActivity.this,GetFrameViewActivity3.class);




//        Intent intent = new Intent(MainActivity.this,TestActivity.class);

//        Intent intent = new Intent(MainActivity.this,TestSeekBarActivity.class);
//        Intent intent = new Intent(MainActivity.this, TestBannerActivity.class);

//        Intent intent = new Intent(MainActivity.this, TestRecycleViewActivity.class);

//        Intent intent = new Intent(MainActivity.this, TestCoordinatorLayoutActivity.class);

        /**
         * 未完成视频播放 页面含有 上滑下滑页面
         */
//        Intent intent = new Intent(MainActivity.this, VideoProductPagerActivity.class);

        /**
         * 类抖音 播放页面 没有上滑下滑 音量
         */
//        Intent intent = new Intent(MainActivity.this, VideoDouYinPagerActivity.class);

//        Intent intent = new Intent(MainActivity.this, DemoPlayerActivity.class);


        startActivity(intent);

        initView();

//        play3();
    }

    /**
     * 时长格式化显示
     */
    private String generateTime(int time) {
        int totalSeconds = time;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }


    private void initView() {
        settings = new Settings(this);

        videoView = findViewById(R.id.videoView);

        videoView.setVideoURI(Uri.parse(url7));
        videoView.start();

//        videoView.setA

    }

    private void play3() {

    }


    @Override
    public void onComplete() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPlay() {

    }
}
