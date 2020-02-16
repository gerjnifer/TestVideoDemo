package com.easyflower.testvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.easyflower.testvideo.testdata.VideoData;
import com.easyflower.testvideo.utils.DataUtils;

import java.util.List;

public class VideoProductListActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView video_listview;
    private VideoProductListAdapter adapter;
    private List<VideoData> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_product_pager);

        createTestData();

        initView();
    }

    private void createTestData() {
        data = DataUtils.createData();
    }

    private void initView() {
        video_listview = findViewById(R.id.video_listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        video_listview.setLayoutManager(linearLayoutManager);

        adapter = new VideoProductListAdapter(this, data);
        video_listview.setAdapter(adapter);

        setAdapterListener();
    }

    private void setAdapterListener() {
        video_listview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(currentPlayView!=null){
//                    boolean playRange = isPlayRange(currentPlayView, recyclerView);
//                    if(!playRange){
//                        mMediaPlayerTool.reset();
//                    }
//                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //检测播放视频
//                    checkPlayVideo();
//                    if(currentPlayView == null){
//                        playVideoByPosition(-1);
//                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
}
