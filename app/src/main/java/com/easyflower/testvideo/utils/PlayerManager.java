package com.easyflower.testvideo.utils;

import android.app.Activity;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.easyflower.testvideo.LogUtil;
import com.easyflower.testvideo.adapter.TestRecycleViewAdapter;
import com.easyflower.testvideo.testdata.VideoData;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static android.content.Context.AUDIO_SERVICE;

public class PlayerManager {

    private Activity act;
    private boolean playerSupport;

    // 声音 跟 亮度
    private int mCurrentLight;
    private int mMaxLight = 255;
    private int mCurrentVolume;
    private int mMaxVolume = 10;
    private AudioManager mAudioManager;


    public PlayerManager(Activity act) {
        this.act = act;

        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
            playerSupport = true;
        } catch (Throwable e) {
            Log.e("GiraffePlayer", "loadLibraries error", e);
        }

        initAudio();
    }

    private void initAudio() {
        mAudioManager = (AudioManager) act.getSystemService(AUDIO_SERVICE);
        act.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * 10;// 系统声音取值是0-10,*10为了和百分比相关
        mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 10;

    }


    public void setPlayerHolder(TestRecycleViewAdapter.ViewHolder holder, VideoData videoData) {

        LogUtil.show(" -----------------开始播放 holder.video_view= " + holder.video_view.toString());

        initVideo(holder, videoData);
    }

    private void initVideo(final TestRecycleViewAdapter.ViewHolder holder, VideoData videoData) {
        holder.video_view.setVideoURI(Uri.parse(videoData.getUrl()));

        holder.video_view.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                holder.video_view.start();

//                int width = video_view_layout.getWidth();
//                int height = video_view_layout.getHeight();
//                LogUtil.show(" --------------- width = " + width + "   " + videoView.getWidth());
//                LogUtil.show(" --------------- height = " + height + "   " + videoView.getHeight());

            }
        });

        holder.video_view.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        holder.rl_loading_layout.setVisibility(View.VISIBLE);
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        holder.rl_loading_layout.setVisibility(View.GONE);
                        break;
                }


//                LogUtil.show(" ---------------mp width = " + mp.getVideoWidth() + "   " + mp.getVideoHeight());
//                LogUtil.show(" ---------------mp height = " + mp.getMediaInfo().mMeta.mFormat );


                return false;
            }
        });

        showTopAndBottomLayout();
    }

    /**
     * 显示控制面板
     */
    private void showTopAndBottomLayout() {
//        mIsPanelShowing = true;
//        mTopLayout.setVisibility(View.VISIBLE);
//        control_layout.setVisibility(View.VISIBLE);
//        updateProgress();
//        if (mEventHandler != null) {
//            mEventHandler.removeMessages(CHECK_TIME);
//            Message msg = mEventHandler.obtainMessage(CHECK_TIME);
//            mEventHandler.sendMessage(msg);
//
//            mEventHandler.removeMessages(CHECK_BATTERY);
//            Message batterymsg = mEventHandler.obtainMessage(CHECK_BATTERY);
//            mEventHandler.sendMessage(batterymsg);
//
//            mEventHandler.removeMessages(CHECK_PROGRESS);
//            Message progressmsg = mEventHandler.obtainMessage(CHECK_PROGRESS);
//            mEventHandler.sendMessage(progressmsg);
//        }


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

    private EventHandler mEventHandler;
    private static final int CHECK_TIME = 1;
    private static final int CHECK_BATTERY = 2;
    private static final int CHECK_PROGRESS = 3;
    private static final int AUTO_HIDE_TIME = 10000;
    private static final int AFTER_DRAGGLE_HIDE_TIME = 3000;


    class EventHandler extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECK_TIME:
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mSysTimeView.setText(DateUtils.getCurrentTime());
                        }
                    });
                    break;
                case CHECK_BATTERY:
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            setCurrentBattery();
                        }
                    });
                    break;
                case CHECK_PROGRESS:
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            long duration = videoView.getDuration();
//                            long nowduration = (sb_player_seekbar.getProgress() * duration) / 1000L;
//                            tv_current_video_time.setText(stringForTime((int) nowduration));
                        }
                    });
                    break;
            }
        }
    }


    public void setReleaseHolder(TestRecycleViewAdapter.ViewHolder holder, VideoData videoData) {

        LogUtil.show(" -----------------结束 holder.video_view= " + holder.video_view.toString());

        holder.video_view.stopPlayback();
        holder.video_view.release(true);
        holder.video_view.stopBackgroundPlay();

        IjkMediaPlayer.native_profileEnd();

    }

}
