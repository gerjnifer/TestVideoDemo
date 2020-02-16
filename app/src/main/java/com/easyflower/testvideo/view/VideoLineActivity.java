package com.easyflower.testvideo.view;

import android.app.Activity;
import android.os.Bundle;

import com.easyflower.testvideo.R;

import java.util.ArrayList;
import java.util.List;

public class VideoLineActivity extends Activity {

    PlaybackView paly_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_line);

        paly_back = findViewById(R.id.paly_back);

        init();
    }

    private void init() {
        List<PlaybackView.PlaybackVo> videos = new ArrayList<>();
        PlaybackView.PlaybackVo vo = new PlaybackView.PlaybackVo(1l, 60l, 1l, (byte) 1);
        videos.add(vo);
        paly_back.setVideos(videos);

    }


}
