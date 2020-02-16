package com.easyflower.testvideo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.easyflower.testvideo.media.content.RecentMediaStorage;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;


public class MainActivity2 extends AppCompatActivity {

    private String url5 = "http://stream1.grtn.cn/tvs2/sd/live.m3u8?_ts&time=1518428696629";
    private String url6 = "http://218.207.213.137//PLTV/88888888/224/3221225879/index.m3u8";
//    private String url7 = "http://183.251.61.207/PLTV/88888888/224/3221225829/index.m3u8";

    //    private String url7 = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
    private String url7 = "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1920x1080h.mp4";

    private IjkVideoView ijk_video_view;
    private TableLayout hud_view;

    private Toolbar toolbar;
    private TextView toast_text_view;

    private DrawerLayout mDrawerLayout;
    private FrameLayout mRightDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ijk_video_view = findViewById(R.id.ijk_video_view);
        hud_view = findViewById(R.id.hud_view);

        mRightDrawer = findViewById(R.id.right_drawer);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        toast_text_view = (TextView) findViewById(R.id.toast_text_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toPlay();
    }

    private void toPlay() {

//        play1();

        play2();

    }

    private void play2() {
//        Intent intent = getIntent();
//        String intentAction = intent.getAction();
//        if (!TextUtils.isEmpty(intentAction)) {
//            if (intentAction.equals(Intent.ACTION_VIEW)) {
//                mVideoPath = intent.getDataString();
//            } else if (intentAction.equals(Intent.ACTION_SEND)) {
//                mVideoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
//                    String scheme = mVideoUri.getScheme();
//                    if (TextUtils.isEmpty(scheme)) {
//                        Log.e(TAG, "Null unknown scheme\n");
//                        finish();
//                        return;
//                    }
//                    if (scheme.equals(ContentResolver.SCHEME_ANDROID_RESOURCE)) {
//                        mVideoPath = mVideoUri.getPath();
//                    } else if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
//                        Log.e(TAG, "Can not resolve content below Android-ICS\n");
//                        finish();
//                        return;
//                    } else {
//                        Log.e(TAG, "Unknown scheme " + scheme + "\n");
//                        finish();
//                        return;
//                    }
//                }
//            }
//        }

        if (!TextUtils.isEmpty(url7)) {
            new RecentMediaStorage(this).saveUrlAsync(url7);
        }

        // init UI
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        // init player
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        ijk_video_view.setHudView(hud_view);
        // prefer mVideoPath
        if (url7 != null)
            ijk_video_view.setVideoPath(url7);
//        else if (url7 != null)
//            ijk_video_view.setVideoURI(url7);
        else {
//            Log.e(TAG, "Null Data Source\n");
            System.out.println("------------- Null Data Source\n");
            finish();
            return;
        }
        ijk_video_view.start();

    }

    private void play1() {

        ActionBar actionBar = getSupportActionBar();


        /** 普通播放 start **/
        ijk_video_view.setHudView(hud_view);
        // prefer mVideoPath
        if (url7 != null)
            ijk_video_view.setVideoPath(url7);
//        else if (mVideoUri != null)
//            ijk_video_view.setVideoURI(mVideoUri);
        else {
            System.out.println(" -------------- Null Data Source\n ");
            finish();
            return;
        }
        ijk_video_view.start();

    }
}
