package com.easyflower.testvideo.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.MainActivity;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.adapter.VideoBuyListAdapter;
import com.easyflower.testvideo.utils.DensityUtil;
import com.easyflower.testvideo.view.OnRecycleViewItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class VideoBuyListPopup extends PopupWindow implements OnClickListener {

    private View mMenuView;
    private Context context;
    private Activity act;

    private RelativeLayout pop_video_buy_layout;
    private RecyclerView pop_recycle_view;
    private VideoBuyListAdapter adapter;

    private RelativeLayout mRlLoading;

    public VideoBuyListPopup(Context context) {
        super(context);
        this.context = context;
        Activity mAct = (Activity) context;
        this.act = mAct;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initView(inflater);

        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);

        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // 设置SelectPicPopupWindow弹出窗体可点�?
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popupAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x01000000);
        // 设置SelectPicPopupWindow弹出窗体的背�?
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在�?择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });

        // 获取数据 网络
        initData();

    }


    public void initData() {
        mRlLoading.setVisibility(View.GONE);

        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        list.add("H");
        list.add("I");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");


        adapter = new VideoBuyListAdapter(act, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(act);
        pop_recycle_view.setLayoutManager(linearLayoutManager);
        pop_recycle_view.setAdapter(adapter);

        setAdapterListener();
    }

    private void setAdapterListener() {
        adapter.setOnRecycleViewItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 进入商品详情
                Intent intent = new Intent(act, MainActivity.class);
                act.startActivity(intent);
            }
        });
    }


    private void initView(LayoutInflater inflater) {
        mMenuView = inflater.inflate(R.layout.popup_video_buy_list_view, null);

        pop_video_buy_layout = mMenuView.findViewById(R.id.pop_video_buy_layout);

        mRlLoading = (RelativeLayout) mMenuView.findViewById(R.id.rl_loading);

        pop_recycle_view = mMenuView.findViewById(R.id.pop_recycle_view);

        setViewSize();
    }

    private void setViewSize() {
        int h = DensityUtil.getHeight(act) / 2;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, h);

        pop_recycle_view.setLayoutParams(params);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


}