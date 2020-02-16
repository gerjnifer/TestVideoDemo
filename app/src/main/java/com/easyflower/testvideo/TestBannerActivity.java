package com.easyflower.testvideo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.bannber.Banner;
import com.easyflower.testvideo.bannber.FunBanner;
import com.easyflower.testvideo.bannber.HomeFunPagerView;
import com.easyflower.testvideo.bannber.ImageLoader;
import com.easyflower.testvideo.bannber.OnBannerListener;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class TestBannerActivity extends AppCompatActivity {

    private Banner mBanner;
    private FunBanner fun_banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycle_view);

        initView();
    }

    List<String> picList;

    private void initView() {
        mBanner = findViewById(R.id.banner);
        fun_banner = findViewById(R.id.fun_banner);

        picList = new ArrayList<>();

        picList.add("http://img3.duitang.com/uploads/item/201507/23/20150723115018_ma428.thumb.700_0.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201806/07/20180607185957_fjrFt.thumb.700_0.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201410/20/20141020224133_Ur54c.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201605/26/20160526175100_XT8we.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201803/24/20180324081023_8FVre.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201410/09/20141009224754_AswrQ.jpeg");
        picList.add("http://b-ssl.duitang.com/uploads/item/201504/25/201504250404_kTAQ8.jpeg");


        setBanner();

        setFunBanner();

    }

    private void setFunBanner() {


        List<HomeFunPagerView> pagerList = new ArrayList<>();

        List<String> testList = new ArrayList<>();
        testList.add("1");
        testList.add("2");
        testList.add("3");
        testList.add("4");

        HomeFunPagerView view = new HomeFunPagerView(this, testList);
        pagerList.add(view);

        List<String> testList2 = new ArrayList<>();
        testList2.add("1");
        testList2.add("2");
        testList2.add("3");

        HomeFunPagerView view2 = new HomeFunPagerView(this, testList2);
        pagerList.add(view2);

        View view11 = View.inflate(this, R.layout.item_frame_vidio_image_view, null);
        View view22 = View.inflate(this, R.layout.item_frame_vidio_image_view, null);
        View view33 = View.inflate(this, R.layout.item_pop_video_buy_view, null);
        View view44 = View.inflate(this, R.layout.item_frame_vidio_image_view, null);

        List<View> viewLists = new ArrayList<>();
        viewLists.add(view11);
        viewLists.add(view22);
        viewLists.add(view33);
        viewLists.add(view44);


        LogUtil.show(" ------------------------  setBanner ");
        //设置banner样式
        fun_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        fun_banner.setImageLoader(new GlideImageLoader());
        //设置图片集合

        if (viewLists != null && viewLists.size() > 0) {
            fun_banner.setImages(viewLists);
        }

        //设置banner动画效果
        fun_banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//            bannerHolder.banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        fun_banner.isAutoPlay(false);
        //设置轮播时间
//            bannerHolder.banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        fun_banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        fun_banner.start();

        fun_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {


            }
        });

    }


    private void setBanner() {


        LogUtil.show(" ------------------------  setBanner ");
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合

        if (picList != null && picList.size() > 0) {
            mBanner.setImages(picList);
        }

        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//            bannerHolder.banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
//            bannerHolder.banner.setDelayTime(3500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {


            }
        });

    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //Glide 加载图片简单用法
            Glide.with(context).load(path).error(R.mipmap.ic_launcher).into(imageView);
        }
    }
}
