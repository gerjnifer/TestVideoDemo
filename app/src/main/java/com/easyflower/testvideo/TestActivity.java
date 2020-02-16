package com.easyflower.testvideo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.easyflower.testvideo.adapter.TestRecycleViewAdapter;
import com.easyflower.testvideo.dialog.VideoBuyListPopup;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.testdata.VideoData;
import com.easyflower.testvideo.utils.DataUtils;
import com.easyflower.testvideo.utils.PlayerManager;
import com.easyflower.testvideo.view.OnViewPagerListener;
import com.easyflower.testvideo.view.ViewPagerLayoutManager;

import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout title_back;

    private RecyclerView recycle_view;
    private TestRecycleViewAdapter adapter;
    private ViewPagerLayoutManager layoutManager;

    private IjkVideoView videoView;

    private PlayerManager playerManager;

    private List<VideoData> data;

    private RelativeLayout popup_;
    private VideoBuyListPopup buyPop;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        initView();

        initRes();

        initData();
    }

    /**
     * 初始化播放资源
     */
    private void initRes() {
        data = DataUtils.createData();
        playerManager = new PlayerManager(this);
    }


    /**
     * 着色模式
     */
    private void stateLineBar() {
        Window window = getWindow();
//取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏颜色
//        window.setStatusBarColor(statusColor);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    private void initView() {

        popup_ = findViewById(R.id.parent_layout);

        title_back = findViewById(R.id.title_back);
        title_back.setOnClickListener(this);

        recycle_view = findViewById(R.id.recycle_view);

    }


    private void initData() {

        layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        adapter = new TestRecycleViewAdapter(this, data);
        recycle_view.setLayoutManager(layoutManager);
        layoutManager.onAttachedToWindow(recycle_view);
        recycle_view.setAdapter(adapter);

        setAdapterListener();
    }


    private int playLastPosition = 0;
    private int releaseLastPosition = 1;
    private boolean isComplete = true;

    private void setAdapterListener() {

        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                LogUtil.show("----------------------- onInitComplete");

                if (isComplete) {
                    LogUtil.show("----------------------- 完成初始化 开始 播放 ");
                    playVideo(0, view);
                    isComplete = false;
                } else {
                    LogUtil.show("----------------------- 完成初始化 直播放一次就行 ");
                }

            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }

                LogUtil.show("----------------------- 选中位置:" + "释放位置:" + position + " 下一页:" + isNext + "  " + index);

                if (releaseLastPosition == position) {
                    LogUtil.show("----------------------- 已经释放 不用再次释放 :" + releaseLastPosition + "  " + position);

                } else {
                    LogUtil.show("----------------------- 还没有释放 释放资源 :" + releaseLastPosition + "  " + position);
                }

                releaseLastPosition = position;
                releaseVideo(position, view);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                LogUtil.show("----------------------- 选中位置:" + position + "  是否是滑动到底部:" + isBottom);

                if (playLastPosition == position) {
                    LogUtil.show("----------------------- 播放视频位置没有发生变化:   " + playLastPosition + "  " + position);

                } else {
                    LogUtil.show("----------------------- 播放视频位置发生了变化需要切换播放的页面 :" + playLastPosition + "  " + position);

                    playVideo(position, view);
                }
                playLastPosition = position;
            }


            public void onLayoutComplete() {
//                playVideo(0);
            }

        });


        // adapter 的点击事件

        adapter.setOnItemFunClickListener(new TestRecycleViewAdapter.onItemFunClickListener() {
            @Override
            public void onPopBuyList(int position) {
                // 弹出商品列表
                popBuyList(position);
            }

            @Override
            public void onInToProductDetails(int position) {
                // 进入商品详情
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemIntoShopPage(int positino) {
                // 进入商家
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFollowShop(int position) {
                // 关注店铺
                followShop();

            }

        });
    }


    /**
     * 关注店铺
     */
    private void followShop() {

    }


    /**
     * 弹出 购买列表
     *
     * @param position
     */
    private void popBuyList(int position) {
        buyPop = new VideoBuyListPopup(TestActivity.this);
        buyPop.showAtLocation(popup_, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 释放资源
     *
     * @param index
     * @param view
     */
    private void releaseVideo(int index, View view) {
//        TestRecycleViewAdapter.ViewHolder holder = adapter.getHolder();
//        if (holder == null) {
//            return;
//        }
//
//        playerManager.setReleaseHolder(holder, data.get(index));

        if (view != null) {
            IjkVideoView videoView = view.findViewById(R.id.video_view);

//            videoView.pause();

            videoView.stopPlayback();
            videoView.release(true);
            videoView.stopBackgroundPlay();
        }
    }

    /**
     * 播放
     *
     * @param position
     * @param view
     */
    private void playVideo(int position, View view) {
        LogUtil.show(" -------------- view = " + view.toString());

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;

            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View subView = viewGroup.getChildAt(i);
                LogUtil.show(" --------------------  subView   " + "  " + subView.getId() + "  " + subView.toString());

                if (subView instanceof IjkVideoView) {
                    LogUtil.show(" --------------------  subView  == IjkVideoView ");
                }
            }
        }


        printAllView(view);

//        View parentView = recycle_view.getChildAt(i);
//
//        TestRecycleViewAdapter.ViewHolder holder = adapter.getHolder();
//        if (holder == null) {
//            return;
//        }
//
//        playerManager.setPlayerHolder(holder, data.get(i));


//        if (view != null) {
//            videoView = view.findViewById(R.id.video_view);
//            videoView.setVideoURI(Uri.parse(data.get(position).getUrl()));
//            videoView.start();
//        }

    }

    private void printAllView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {

                View childAt = viewGroup.getChildAt(i);

                LogUtil.show(" ---------------- childAt = " + childAt.toString());


                if (childAt instanceof IjkVideoView) {
                    LogUtil.show(" ----------------找到VideoView childAt = " + childAt.toString());

                    break;
                }

                if (childAt instanceof ViewGroup) {
                    printAllView(childAt);
                }


            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }

    }
}
