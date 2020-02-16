package com.easyflower.testvideo;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.easyflower.testvideo.demo.GestureDetectorController;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.media.widget.media.PlayStateParams;
import com.easyflower.testvideo.utils.NetworkUtils;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoDouYinPagerActivity extends AppCompatActivity implements View.OnClickListener {
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

    private VideoDouYinPagerActivity mContext;
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

    private IjkVideoView videoView;
    private LinearLayout control_layout; // 控制布局

    private TextView tv_current_video_time; // 播放时长
    private SeekBar sb_player_seekbar; // 进度条
    private TextView tv_total_video_time; // 全部时长

    private TextView video_speed_net;

    private ImageView iv_player_center_pause; // 屏幕暂停 开始


    // 加载框
    private RelativeLayout mLoadingLayout;
    private TextView mLoadingText;


    // 手势控制
    private GestureDetectorController mGestureController;


    private boolean mIsDragging; // 是否可以拖动


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_product_douyin_pager);

        mContext = this;

        initView();

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

//                startPlayer();
                String s = generateTime(videoView.getDuration());
                tv_total_video_time.setText(s);

            }
        });

        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {

                LogUtil.show(" --------------- what = " + what);

                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mLoadingLayout.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mLoadingLayout.setVisibility(View.GONE);
                        break;
                    case PlayStateParams.MEDIA_INFO_NETWORK_BANDWIDTH: //503 网速方面
                    case PlayStateParams.MEDIA_INFO_BUFFERING_BYTES_UPDATE://703 网络带宽，网速方面
                        if (video_speed_net != null) {
                            video_speed_net.setText(getFormatSize(extra));
                        }
                        break;
                }

//                if (onInfoListener != null) {
//                    onInfoListener.onInfo(mp, what, extra);
//                }

                statusChange(what);
                return true;
            }
        });

        // 播放
        startPlayer();

    }

    /**
     * 当前状态
     */
    private int status = PlayStateParams.STATE_IDLE;

    /**
     * 状态改变同步UI
     */
    private void statusChange(int newStatus) {
        if (newStatus == PlayStateParams.STATE_COMPLETED) {
            status = PlayStateParams.STATE_COMPLETED;
            currentPosition = 0;
//            hideAll();
//            showStatus("播放结束");
        } else if (newStatus == PlayStateParams.STATE_PREPARING
                || newStatus == PlayStateParams.MEDIA_INFO_BUFFERING_START) {
            status = PlayStateParams.STATE_PREPARING;
            /**视频缓冲*/
//            hideStatusUI();
            mLoadingLayout.setVisibility(View.VISIBLE);
        } else if (newStatus == PlayStateParams.MEDIA_INFO_VIDEO_RENDERING_START
                || newStatus == PlayStateParams.STATE_PLAYING
                || newStatus == PlayStateParams.STATE_PREPARED
                || newStatus == PlayStateParams.MEDIA_INFO_BUFFERING_END
                || newStatus == PlayStateParams.STATE_PAUSED) {
            if (status == PlayStateParams.STATE_PAUSED) {
                status = PlayStateParams.STATE_PAUSED;
            } else {
                status = PlayStateParams.STATE_PLAYING;
            }
            /**视频缓冲结束后隐藏缩列图*/
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    hideStatusUI();
                    /**显示控制bar*/

//                    if (!isForbidTouch) {
                    operatorPanl();
//                    }
                    /**延迟0.5秒隐藏视频封面隐藏*/
//                    query.id(R.id.ll_bg).gone();
                    mLoadingLayout.setVisibility(View.GONE);
                }
            }, 1500);
        } else if (newStatus == PlayStateParams.MEDIA_INFO_VIDEO_INTERRUPT) {
            /**直播停止推流*/
            status = PlayStateParams.STATE_ERROR;
            if (!(isGNetWork &&
                    (NetworkUtils.getNetworkType(mContext) == 4
                            || NetworkUtils.getNetworkType(mContext) == 5
                            || NetworkUtils.getNetworkType(mContext) == 6))) {
//                if (isCharge && maxPlaytime < getCurrentPosition()) {
//                    query.id(R.id.app_video_freeTie).visible();
//                } else {
//                hideAll();
//                    showStatus(mActivity.getResources().getString(R.string.small_problem));

                /**5秒尝试重连*/
//                if (!isErrorStop && isAutoReConnect) {
//                    mHandler.sendEmptyMessageDelayed(MESSAGE_RESTART_PLAY, autoConnectTime);
//                }

//                }
            } else {
//                您正在使用移动网络播放视频\n可能产生较高流量费用"
//                query.id(R.id.app_video_netTie).visible();
            }

        } else if (newStatus == PlayStateParams.STATE_ERROR
                || newStatus == PlayStateParams.MEDIA_INFO_UNKNOWN
                || newStatus == PlayStateParams.MEDIA_ERROR_IO
                || newStatus == PlayStateParams.MEDIA_ERROR_MALFORMED
                || newStatus == PlayStateParams.MEDIA_ERROR_UNSUPPORTED
                || newStatus == PlayStateParams.MEDIA_ERROR_TIMED_OUT
                || newStatus == PlayStateParams.MEDIA_ERROR_SERVER_DIED) {
            status = PlayStateParams.STATE_ERROR;
            if (!(isGNetWork && (NetworkUtils.getNetworkType(mContext) == 4 || NetworkUtils.getNetworkType(mContext) == 5 || NetworkUtils.getNetworkType(mContext) == 6))) {
//                if (isCharge && maxPlaytime < getCurrentPosition()) {
//                    query.id(R.id.app_video_freeTie).visible();
//                } else {
//                hideStatusUI();
//                    showStatus(mActivity.getResources().getString(R.string.small_problem));

                /**5秒尝试重连*/
//                if (!isErrorStop && isAutoReConnect) {
//                    mHandler.sendEmptyMessageDelayed(MESSAGE_RESTART_PLAY, autoConnectTime);
//                }
//                }
            } else {
//                您正在使用移动网络播放视频\n可能产生较高流量费用"
//                query.id(R.id.app_video_netTie).visible();
            }
        }
    }

    private void operatorPanl() {

        /**显示面板的时候再根据状态显示播放按钮*/
        if (status == PlayStateParams.STATE_PLAYING
                || status == PlayStateParams.STATE_PREPARED
                || status == PlayStateParams.STATE_PREPARING
                || status == PlayStateParams.STATE_PAUSED) {
//            if (isHideCenterPlayer) {
//                iv_player.setVisibility(View.GONE);
//            } else {
//                iv_player.setVisibility( View.VISIBLE);
//            }
        } else {
//            iv_player.setVisibility(View.GONE);
        }
        updatePausePlay();
        mHandler.sendEmptyMessage(MESSAGE_SHOW_PROGRESS);
//        mAutoPlayRunnable.start();

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


    private void initView() {

        title_back = findViewById(R.id.title_back);
        title_back.setOnClickListener(this);

        videoView = findViewById(R.id.video_view);
        control_layout = findViewById(R.id.control_layout);
        iv_player_center_pause = findViewById(R.id.iv_player_center_pause);
        tv_current_video_time = findViewById(R.id.tv_current_video_time);
        sb_player_seekbar = findViewById(R.id.sb_player_seekbar);
        tv_total_video_time = findViewById(R.id.tv_total_video_time);

        video_speed_net = findViewById(R.id.video_speed_net);

        mLoadingLayout = findViewById(R.id.rl_loading_layout);
        mLoadingText = findViewById(R.id.tv_loading_info);

        iv_player_center_pause.setOnClickListener(this);

        setSeekBar();
    }


    private void setSeekBar() {
        sb_player_seekbar.setMax(1000);
        sb_player_seekbar.setOnSeekBarChangeListener(mSeekBarChangeListener);

    }

    /**
     * 下载速度格式化显示
     */
    private String getFormatSize(int size) {
        long fileSize = (long) size;
        String showSize = "";
        if (fileSize >= 0 && fileSize < 1024) {
            showSize = fileSize + "Kb/s";
        } else if (fileSize >= 1024 && fileSize < (1024 * 1024)) {
            showSize = Long.toString(fileSize / 1024) + "KB/s";
        } else if (fileSize >= (1024 * 1024) && fileSize < (1024 * 1024 * 1024)) {
            showSize = Long.toString(fileSize / (1024 * 1024)) + "MB/s";
        }
        return showSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_player_center_pause: // 屏幕暂停 开始
//                videoView.start();
//                updatePlayPauseStatus(true);
                break;

        }
    }

    /**
     * seekBar 控制
     */
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        // seekbar进度发生变化时回调
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            LogUtil.show("--------- seekTo= " + progress);

            if (!fromUser) {
                /**不是用户拖动的，自动播放滑动的情况*/
                return;
            } else {
                long duration = videoView.getDuration();
                int position = (int) ((duration * progress * 1.0) / 1000);
                String time = generateTime(position);
//                query.id(R.id.app_video_currentTime).text(time);
//                query.id(R.id.app_video_currentTime_full).text(time);
//                query.id(R.id.app_video_currentTime_left).text(time);
                tv_current_video_time.setText(time);
            }
//
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
            long duration = videoView.getDuration();
            videoView.seekTo((int) ((duration * seekBar.getProgress() * 1.0) / 1000));
            mHandler.removeMessages(MESSAGE_SHOW_PROGRESS);
            mIsDragging = false;
            mHandler.sendEmptyMessageDelayed(MESSAGE_SHOW_PROGRESS, 1000);
        }
    };

    /**
     * 时长格式化显示
     */
    private String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }


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
                    if (!mIsDragging) {
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

        LogUtil.show(" -------------syncProgress  " + generateTime(position));

        tv_current_video_time.setText(generateTime(position));
        tv_total_video_time.setText(generateTime(duration));

//        query.id(R.id.app_video_currentTime).text(generateTime(position));
//        query.id(R.id.app_video_currentTime_full).text(generateTime(position));
//        query.id(R.id.app_video_currentTime_left).text(generateTime(position));
//        query.id(R.id.app_video_endTime).text(generateTime(duration));
//        query.id(R.id.app_video_endTime_full).text(generateTime(duration));
//        query.id(R.id.app_video_endTime_left).text(generateTime(duration));

        return position;
    }


    /**
     * 屏幕中间 暂停 播放
     *
     * @param isPlaying
     */
    private void updatePlayPauseStatus(boolean isPlaying) {
        iv_player_center_pause.setVisibility(isPlaying ? View.GONE : View.VISIBLE);

    }


}
