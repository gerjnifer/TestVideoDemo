package com.easyflower.testvideo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easyflower.testvideo.demo.GestureDetectorController;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.utils.NetworkUtils;
import com.easyflower.testvideo.utils.WindowUtils;

import java.text.NumberFormat;
import java.util.Formatter;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoProductPagerActivity extends AppCompatActivity implements View.OnClickListener, GestureDetectorController.IGestureListener {
    /**
     * 同步进度
     */
    private static final int MESSAGE_SHOW_PROGRESS = 1;
    /**
     * 设置新位置
     */
    private static final int MESSAGE_SEEK_NEW_POSITION = 3;
    /**
     * 重新播放
     */
    private static final int MESSAGE_RESTART_PLAY = 5;
    /**
     * 当前播放位置
     */
    private int currentPosition;
    /**
     * 滑动进度条得到的新位置，和当前播放位置是有区别的,newPosition =0也会调用设置的，故初始化值为-1
     */
    private long newPosition = -1;
    /**
     * 播放的时候是否需要网络提示，默认显示网络提示，true为显示网络提示，false不显示网络提示
     */

    private VideoProductPagerActivity mContext;
    private boolean isGNetWork = true;
    /**
     * 第三方so是否支持，默认不支持，true为支持
     */
    private boolean playerSupport = true;


    private static final int CHECK_TIME = 1;
    private static final int CHECK_BATTERY = 2;
    private static final int CHECK_PROGRESS = 3;
    private static final int AUTO_HIDE_TIME = 10000;
    private static final int AFTER_DRAGGLE_HIDE_TIME = 3000;

    private RelativeLayout title_back;

    private FrameLayout video_view_layout; // 视频全部布局
    private IjkVideoView videoView;
    private LinearLayout control_layout; // 控制布局
    private CheckBox cb_play_pause; // 暂停
    private ImageView iv_next_video; // 下一步
    private TextView tv_current_video_time; // 播放时长
    private SeekBar sb_player_seekbar; // 进度条
    private TextView tv_total_video_time; // 全部时长
    private TextView tv_bitstream; // 模式切换
    private ImageView iv_player_center_pause; // 屏幕暂停 开始
    private ImageView tv_scren_size;

    private Formatter mFormatter;
    private StringBuilder mFormatterBuilder;

    private TextView mDragHorizontalView;
    private TextView mDragVerticalView;
    private long mScrollProgress;
    private boolean mIsHorizontalScroll;
    private boolean mIsVerticalScroll;


    // 加载框
    private RelativeLayout mLoadingLayout;
    private TextView mLoadingText;

    // 声音 跟 亮度
    private int mCurrentLight;
    private int mMaxLight = 255;
    private int mCurrentVolume;
    private int mMaxVolume = 10;
    private AudioManager mAudioManager;

    // 手势控制
    private GestureDetectorController mGestureController;

    /**
     * 是否显示控制面板，默认为隐藏，true为显示false为隐藏
     */
    private boolean isShowControlPanl = false;

    private boolean mIsDragging; // 是否可以拖动
    private boolean mIsPanelShowing; // 是否显示控制面板
    private boolean mIsMove = false;//是否在屏幕上滑动

    private EventHandler mEventHandler;

    private String videoPath;
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPath = getIntent().getStringExtra("path");
        setContentView(R.layout.activity_video_product_pager);

        mContext = this;

        initRes();

        initView();

        initCenterView();

        // 初始化音量
        initAudio();

        // 初始化手势
        initGesture();

        initPrepareVideo();
    }


    /**
     * 准备视频
     */
    private void initPrepareVideo() {
//init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mLoadingText.setText("正在加载中...");

//        String url = "https://www.apple.com/105/media/us/iphone-x/2017/01df5b43-28e4-4848-bf20-490c34a926a7/films/feature/iphone-x-feature-tpl-cc-us-20170912_1920x1080h.mp4";

        String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        videoView.setVideoURI(Uri.parse(url));

//        videoView.setVideoPath(videoPath);

        videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {


                startPlayer();


//                int width = video_view_layout.getWidth();
//                int height = video_view_layout.getHeight();
//                LogUtil.show(" --------------- width = " + width + "   " + videoView.getWidth());
//                LogUtil.show(" --------------- height = " + height + "   " + videoView.getHeight());

            }
        });

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLoadingLayout.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mLoadingLayout.setVisibility(View.GONE);
                        break;
                }


//                LogUtil.show(" ---------------mp width = " + mp.getVideoWidth() + "   " + mp.getVideoHeight());
//                LogUtil.show(" ---------------mp height = " + mp.getMediaInfo().mMeta.mFormat );


                return false;
            }
        });

//        toggleTopAndBottomLayout();
        showTopAndBottomLayout();

    }

    private void startPlayer() {
        if (isGNetWork && (NetworkUtils.getNetworkType(mContext) == 4 || NetworkUtils.getNetworkType(mContext) == 5 || NetworkUtils.getNetworkType(mContext) == 6)) {
            // 当前是 移动网络
            LogUtil.show(" ----------------- 当前 移动网络 ");
        } else {
            LogUtil.show(" ----------------- 当前 无线网络 ");

            if (playerSupport) {   // 是否支持移动网络播放
//                query.id(R.id.app_video_loading).visible();
                videoView.start();
            } else {  // 不支持移动网络播放
//                showStatus(mActivity.getResources().getString(R.string.not_support));
            }
        }
    }


    private void initGesture() {
        mGestureController = new GestureDetectorController(this, this);
    }

    private void initAudio() {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 10;// 系统声音取值是0-10,*10为了和百分比相关
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 10;

    }

    private void initCenterView() {
        mDragHorizontalView = findViewById(R.id.tv_horiontal_gesture);
        mDragVerticalView = findViewById(R.id.tv_vertical_gesture);
    }

    private void initRes() {
        mEventHandler = new EventHandler(Looper.myLooper());

        mFormatterBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatterBuilder, Locale.getDefault());
    }


    private SeekBar sb_player_seekbar2;

    private void initView() {

        title_back = findViewById(R.id.title_back);
        title_back.setOnClickListener(this);

        tv_scren_size = findViewById(R.id.tv_scren_size);

        video_view_layout = findViewById(R.id.video_view_layout);
        videoView = findViewById(R.id.video_view);
        control_layout = findViewById(R.id.control_layout);
        iv_player_center_pause = findViewById(R.id.iv_player_center_pause);
        cb_play_pause = findViewById(R.id.cb_play_pause);
        iv_next_video = findViewById(R.id.iv_next_video);
        tv_current_video_time = findViewById(R.id.tv_current_video_time);
        sb_player_seekbar = findViewById(R.id.sb_player_seekbar);
        tv_total_video_time = findViewById(R.id.tv_total_video_time);
        tv_bitstream = findViewById(R.id.tv_bitstream);


        mLoadingLayout = findViewById(R.id.rl_loading_layout);
        mLoadingText = findViewById(R.id.tv_loading_info);

        tv_scren_size.setOnClickListener(this);

        iv_player_center_pause.setOnClickListener(this);
        cb_play_pause.setOnClickListener(this);
        iv_next_video.setOnClickListener(this);
        tv_bitstream.setOnClickListener(this);

        iv_next_video.setVisibility(View.GONE);

        sb_player_seekbar2 = findViewById(R.id.sb_player_seekbar2);

        setSeekBar();
    }

    private int mCurrentTime;
    private boolean fromuser = false;

    private void setSeekBar() {
        sb_player_seekbar.setMax(1000);
        sb_player_seekbar.setOnSeekBarChangeListener(mSeekBarChangeListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_player_center_pause: // 屏幕暂停 开始
                videoView.start();
                updatePlayPauseStatus(true);
                break;
            case R.id.cb_play_pause: // 暂停 开始
                handlePlayPause();
                break;
            case R.id.iv_next_video: // 一下首
                break;
            case R.id.tv_bitstream: // 模式切换
//                swapPlayMode();
                break;
            case R.id.tv_scren_size:

                setFullScreen(true);
                break;

        }
    }

    private int mediaType = 0;

    /**
     * 切换播放模式
     */
    private void swapPlayMode() {

        if (mediaType == IjkVideoView.RENDER_SURFACE_VIEW - 1) {

            mediaType = IjkVideoView.RENDER_TEXTURE_VIEW - 1;

        } else {
            mediaType = IjkVideoView.RENDER_SURFACE_VIEW - 1;
        }


        int render = videoView.toggleRender(mediaType);
        String renderText = IjkVideoView.getRenderText(this, render);
        Toast.makeText(VideoProductPagerActivity.this, "" + renderText, Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否是全屏
     *
     * @param isFull
     */
    private void setFullScreen(boolean isFull) {
        if (WindowUtils.getScreenOrientation(VideoProductPagerActivity.this) == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            VideoProductPagerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            VideoProductPagerActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * seekBar 控制
     */
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        // seekbar进度发生变化时回调
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!fromUser) {
                return;
            }
            long duration = videoView.getDuration();//视频时长

            LogUtil.show(" -------------------------- druation = " + duration);

            long nowPosition = (duration * progress) / 1000L;
            tv_current_video_time.setText(stringForTime((int) nowPosition));
        }

        // seekbar开始拖动时回调
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mIsDragging = true;
            mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
        }

        // seekbar拖动完成后回调
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mIsDragging = false;
            int progress = seekBar.getProgress();//最后拖动停止的进度
            long duration = videoView.getDuration();//视频时长
            long newPosition = (duration * progress) / 1000L;//当前的进度
            videoView.seekTo((int) newPosition);
//            mEventHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    hideTopAndBottomLayout();
//                }
//            }, AFTER_DRAGGLE_HIDE_TIME);
        }
    };

    /**
     * 消息处理
     */
    @SuppressWarnings("HandlerLeak")
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                /**滑动完成，隐藏滑动提示的box*/
//                case MESSAGE_HIDE_CENTER_BOX:
//                    query.id(R.id.app_video_volume_box).gone();
//                    query.id(R.id.app_video_brightness_box).gone();
//                    query.id(R.id.app_video_fastForward_box).gone();
//                    break;
                /**滑动完成，设置播放进度*/
                case MESSAGE_SEEK_NEW_POSITION:
                    if (newPosition >= 0) {
                        videoView.seekTo((int) newPosition);
                        newPosition = -1;
                    }
                    break;
                /**滑动中，同步播放进度*/
                case MESSAGE_SHOW_PROGRESS:
                    long pos = syncProgress();
                    if (!mIsDragging && isShowControlPanl) {
                        msg = obtainMessage(MESSAGE_SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                        updatePausePlay();
                    }
                    break;
                /**重新去播放*/
                case MESSAGE_RESTART_PLAY:
//                    status = PlayStateParams.STATE_ERROR;
                    startPlayer();
                    updatePausePlay();
                    break;
            }
        }
    };

    /**
     * 更新播放、暂停和停止按钮
     */
    private void updatePausePlay() {
        if (videoView.isPlaying()) {
            iv_player_center_pause.setImageResource(R.drawable.simple_player_icon_media_pause);
        } else {
            iv_player_center_pause.setImageResource(R.drawable.simple_player_arrow_white_24dp);
        }
    }

    /**
     * 同步进度
     */
    private long syncProgress() {
        if (mIsDragging) {
            return 0;
        }
        long position = videoView.getCurrentPosition();
        long duration = videoView.getDuration();
        if (sb_player_seekbar != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                sb_player_seekbar.setProgress((int) pos);
            }
            int percent = videoView.getBufferPercentage();
            sb_player_seekbar.setSecondaryProgress(percent * 10);
        }


//        query.id(R.id.app_video_currentTime).text(generateTime(position));
//        query.id(R.id.app_video_currentTime_full).text(generateTime(position));
//        query.id(R.id.app_video_currentTime_left).text(generateTime(position));
//        query.id(R.id.app_video_endTime).text(generateTime(duration));
//        query.id(R.id.app_video_endTime_full).text(generateTime(duration));
//        query.id(R.id.app_video_endTime_left).text(generateTime(duration));

        return position;
    }

    /**
     * 隐藏控制面板
     */
    private void hideTopAndBottomLayout() {
        if (mIsDragging == true) {
            return;
        }
        mIsPanelShowing = false;
//        mTopLayout.setVisibility(View.GONE);
        control_layout.setVisibility(View.GONE);
    }

    /**
     * 显示控制面板
     */
    private void showTopAndBottomLayout() {
        mIsPanelShowing = true;
//        mTopLayout.setVisibility(View.VISIBLE);
        control_layout.setVisibility(View.VISIBLE);
        updateProgress();
        if (mEventHandler != null) {
            mEventHandler.removeMessages(CHECK_TIME);
            Message msg = mEventHandler.obtainMessage(CHECK_TIME);
            mEventHandler.sendMessage(msg);

            mEventHandler.removeMessages(CHECK_BATTERY);
            Message batterymsg = mEventHandler.obtainMessage(CHECK_BATTERY);
            mEventHandler.sendMessage(batterymsg);

            mEventHandler.removeMessages(CHECK_PROGRESS);
            Message progressmsg = mEventHandler.obtainMessage(CHECK_PROGRESS);
            mEventHandler.sendMessage(progressmsg);
        }
//        switch (mStreamType) {
//            case AlbumDetailActivity.StreamType.SUPER:
//                mBitStreamView.setText(getResources().getString(R.string.stream_super));
//                break;
//            case AlbumDetailActivity.StreamType.NORMAL:
//                mBitStreamView.setText(getResources().getString(R.string.stream_normal));
//                break;
//            case AlbumDetailActivity.StreamType.HIGH:
//                mBitStreamView.setText(getResources().getString(R.string.stream_high));
//                break;
//            default:
//                break;
//        }
    }


    /**
     * 更新进度条
     */
    private void updateProgress() {
        int currentPosition = videoView.getCurrentPosition();//当前的视频位置
        int duration = videoView.getDuration();//视频时长
        if (sb_player_seekbar != null) {
            if (duration > 0) {
                //转成long型,避免溢出
                long pos = currentPosition * 1000L / duration;
                sb_player_seekbar.setProgress((int) pos);
            }
            int perent = videoView.getBufferPercentage();//已经缓冲的进度
            sb_player_seekbar.setSecondaryProgress(perent);//设置缓冲进度
            tv_current_video_time.setText(stringForTime(currentPosition));
            tv_total_video_time.setText(stringForTime(duration));
        }
    }

    /**
     * 屏幕中间 暂停 播放
     *
     * @param isPlaying
     */
    private void updatePlayPauseStatus(boolean isPlaying) {
        iv_player_center_pause.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
        cb_play_pause.invalidate();
        cb_play_pause.setChecked(isPlaying);
        cb_play_pause.refreshDrawableState();
    }

    /**
     * 底部暂停
     */
    private void handlePlayPause() {
        //TODO
        if (videoView.isPlaying()) {//视频正在播放
            videoView.pause();
            updatePlayPauseStatus(false);
        } else {
            videoView.start();
            updatePlayPauseStatus(true);
        }
    }


    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_TIME:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mSysTimeView.setText(DateUtils.getCurrentTime());
                        }
                    });
                    break;
                case CHECK_BATTERY:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            setCurrentBattery();
                        }
                    });
                    break;
                case CHECK_PROGRESS:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long duration = videoView.getDuration();
                            long nowduration = (sb_player_seekbar.getProgress() * duration) / 1000L;
                            tv_current_video_time.setText(stringForTime((int) nowduration));
                        }
                    });
                    break;
            }
        }
    }

    /**
     * 隐藏和显示 控制布局
     */
    private void toggleTopAndBottomLayout() {
        if (mIsPanelShowing) {
            hideTopAndBottomLayout();
        } else {
            showTopAndBottomLayout();
            //先显示,没有任何操作,就5s后隐藏
            mEventHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideTopAndBottomLayout();
                }
            }, AUTO_HIDE_TIME);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mIsMove == false) {
//                toggleTopAndBottomLayout();
            } else {
                mIsMove = false;
            }
            //水平方向,up时,seek到对应位置播放
            if (mIsHorizontalScroll) {
                mIsHorizontalScroll = false;
                videoView.seekTo((int) mScrollProgress);
                //一次down,up结束后mDragHorizontalView隐藏
                mDragHorizontalView.setVisibility(View.GONE);
            }
            if (mIsVerticalScroll) {
                mDragVerticalView.setVisibility(View.GONE);
                mIsVerticalScroll = false;
            }
        }
        return mGestureController.onTouchEvent(event);
    }

    //*********************************************************************************************
    //*********************************************************************************************
    //*********************************************************************************************
    //*****************手势************************************************************************
    //*********************************************************************************************
    //*********************************************************************************************

    @Override
    public void onScrollStart(GestureDetectorController.ScrollType type) {
        mIsMove = true;
        switch (type) {
            case HORIZONTAL:
                mDragHorizontalView.setVisibility(View.VISIBLE);
                mScrollProgress = -1;
                mIsHorizontalScroll = true;//水平滑动标识
                break;
            case VERTICAL_LEFT:
                setComposeDrawableAndText(mDragVerticalView, R.drawable.ic_light, this);
                mDragVerticalView.setVisibility(View.VISIBLE);
                updateVerticalText(mCurrentLight, mMaxLight);
                mIsVerticalScroll = true;
                break;
            case VERTICAL_RIGH:
                if (mCurrentVolume > 0) {
                    setComposeDrawableAndText(mDragVerticalView, R.drawable.volume_normal, this);
                } else {
                    setComposeDrawableAndText(mDragVerticalView, R.drawable.volume_no, this);
                }
                mDragVerticalView.setVisibility(View.VISIBLE);
                updateVerticalText(mCurrentVolume, mMaxVolume);
                mIsVerticalScroll = true;
                break;
        }
    }


    // 更新进度
    @Override
    public void onScrollHorizontal(float x1, float x2) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int MAX_SEEK_STEP = 300000;//最大滑动5分钟
        int offset = (int) (x2 / width * MAX_SEEK_STEP) + videoView.getCurrentPosition();
        long progress = Math.max(0, Math.min(videoView.getDuration(), offset));
        mScrollProgress = progress;
        updateHorizontalText(progress);
    }


    // 左侧上下滑
    @Override
    public void onScrollVerticalLeft(float y1, float y2) {
        int height = getResources().getDisplayMetrics().heightPixels;
        int offset = (int) (mMaxLight * y1) / height;
        if (Math.abs(offset) > 0) {
            mCurrentLight += offset;//得到变化后的亮度
            mCurrentLight = Math.max(0, Math.min(mMaxLight, mCurrentLight));
            // 更新系统亮度
//            SysUtils.setBrightness(this, mCurrentLight);
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt("shared_preferences_light", mCurrentLight);
            editor.commit();
            updateVerticalText(mCurrentLight, mMaxLight);
        }
    }


    // 右侧上下滑
    @Override
    public void onScrollVerticalRight(float y1, float y2) {
        int height = getResources().getDisplayMetrics().heightPixels;
        int offset = (int) (mMaxVolume * y1) / height;
        if (Math.abs(offset) > 0) {
            mCurrentVolume += offset;//得到变化后的声音
            mCurrentVolume = Math.max(0, Math.min(mMaxVolume, mCurrentVolume));
            // 更新系统声音
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume / 10, 0);
            updateVerticalText(mCurrentVolume, mMaxVolume);
        }
    }


    //用于组合图片及文字
    private void setComposeDrawableAndText(TextView textView, int drawableId, Context context) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        //这四个参数表示把drawable绘制在矩形区域
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //设置图片在文字的上方
        //The Drawables must already have had drawable.setBounds called.
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    //更新垂直方向上滑动时的百分比
    private void updateVerticalText(int current, int total) {
        NumberFormat formater = NumberFormat.getPercentInstance();
        formater.setMaximumFractionDigits(0);//设置整数部分允许最大小数位 66.5%->66%
        String percent = formater.format((double) (current) / (double) total);
        mDragVerticalView.setText(percent);
    }

    //更新水平方向seek的进度, duration表示变化后的duration
    private void updateHorizontalText(long duration) {
        String text = stringForTime((int) duration) + "/" + stringForTime(videoView.getDuration());
        mDragHorizontalView.setText(text);
    }


    //*********************************************************************************************
    //*********************************************************************************************
    //*********************************************************************************************
    //*****************手势************************************************************************
    //*********************************************************************************************
    //*********************************************************************************************
    private String stringForTime(int timeMs) {
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
}
