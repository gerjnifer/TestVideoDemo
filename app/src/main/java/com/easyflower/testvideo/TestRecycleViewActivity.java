package com.easyflower.testvideo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.adapter.TestRecycleViewAdapter;
import com.easyflower.testvideo.adapter.TestRecycleViewdataAdapter;
import com.easyflower.testvideo.bannber.Banner;
import com.easyflower.testvideo.bannber.FunBanner;
import com.easyflower.testvideo.bannber.HomeFunPagerView;
import com.easyflower.testvideo.bannber.ImageLoader;
import com.easyflower.testvideo.bannber.OnBannerListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TestRecycleViewActivity extends AppCompatActivity {

    private RecyclerView recycle_view;

    private TestRecycleViewdataAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycle_view);

        initView();


        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");

        adapter = new TestRecycleViewdataAdapter(this, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        PagerSnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(recycle_view);
        recycle_view.setLayoutManager(linearLayoutManager);
        recycle_view.setAdapter(adapter);
    }

    List<String> picList;

    private void initView() {
        recycle_view = findViewById(R.id.recycle_view);
    }
}
