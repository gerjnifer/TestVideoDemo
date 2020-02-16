package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.testdata.VideoData;
import com.easyflower.testvideo.utils.DensityUtil;

import java.util.List;

public class TestRecycleViewAdapter extends RecyclerView.Adapter<TestRecycleViewAdapter.ViewHolder> {
    private Activity act;
    private List<VideoData> list;

    private RelativeLayout.LayoutParams params;

    public TestRecycleViewAdapter(Activity act, List<VideoData> list) {
        this.act = act;
        this.list = list;

        int w = DensityUtil.getWidth(act);
        int h = DensityUtil.getHeight(act);
        params = new RelativeLayout.LayoutParams(w, h);
    }

    @NonNull
    @Override
    public TestRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(act, R.layout.item_test_view, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecycleViewAdapter.ViewHolder holder, int position) {
        holder.video_view.setLayoutParams(params);

        holder.video_item_shop_name.setText("" + list.get(position).getShopName());

        // 头像
        if (!TextUtils.isEmpty(list.get(position).getHeadUrl())) {
            Glide.with(act).load(list.get(position).getHeadUrl()).into(holder.item_shop_img);
        }

        // 商品图片
        if (!TextUtils.isEmpty(list.get(position).getProductUrl())) {
            Glide.with(act).load(list.get(position).getProductUrl()).into(holder.video_product_image);
        }

        itemFunClickView(holder.item_video_buy_list, holder.video_product_info_layout, holder.item_shop_info_layout, holder.item_video_follow_layout, position);
    }

    private void itemFunClickView(ImageView item_video_buy_list, RelativeLayout video_product_info_layout, RelativeLayout item_shop_info_layout, LinearLayout item_video_follow_layout, final int position) {

        /**
         * 打开购买列表
         */
        item_video_buy_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFunClick != null) {
                    onItemFunClick.onPopBuyList(position);
                }
            }
        });

        // 进入商品详情
        video_product_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFunClick != null) {
                    onItemFunClick.onInToProductDetails(position);
                }
            }
        });

        // 进入商家页面
        item_shop_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFunClick != null) {
                    onItemFunClick.onItemIntoShopPage(position);
                }
            }
        });


        // 关注
        item_video_follow_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemFunClick != null) {
                    onItemFunClick.onFollowShop(position);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    ViewHolder holder;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public IjkVideoView video_view;
        public ImageView iv_player_center_pause;
        public RelativeLayout rl_loading_layout;
        public RelativeLayout control_layout;
        public SeekBar sb_player_seekbar;

        public RelativeLayout item_shop_info_layout; // 进入商家详情
        public ImageView item_shop_img; // 商家图片
        public TextView video_item_shop_name;  // 商家名字
        public TextView video_item_shop_follow; // 关注数量

        public LinearLayout item_video_follow_layout; // 关注商家
        public ImageView item_video_follow_img; // 关注图标
        public TextView item_video_follow_txt; // 关注文字

        public ImageView item_video_buy_list;


        public RelativeLayout video_product_info_layout;
        private ImageView video_product_image;
        private TextView video_product_name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            video_view = itemView.findViewById(R.id.video_view);
            iv_player_center_pause = itemView.findViewById(R.id.iv_player_center_pause);

            rl_loading_layout = itemView.findViewById(R.id.rl_loading_layout);
            control_layout = itemView.findViewById(R.id.control_layout);
            sb_player_seekbar = itemView.findViewById(R.id.sb_player_seekbar);

            item_video_buy_list = itemView.findViewById(R.id.item_video_buy_list);

            item_shop_info_layout = itemView.findViewById(R.id.item_shop_info_layout);
            item_shop_img = itemView.findViewById(R.id.item_shop_img);
            video_item_shop_name = itemView.findViewById(R.id.video_item_shop_name);
            video_item_shop_follow = itemView.findViewById(R.id.video_item_shop_follow);

            item_video_follow_layout = itemView.findViewById(R.id.item_video_follow_layout);
            item_video_follow_img = itemView.findViewById(R.id.item_video_follow_img);
            item_video_follow_txt = itemView.findViewById(R.id.item_video_follow_txt);

            video_product_info_layout = itemView.findViewById(R.id.video_product_info_layout);
            video_product_image = itemView.findViewById(R.id.video_product_image);
            video_product_name = itemView.findViewById(R.id.video_product_name);

        }
    }

    public ViewHolder getHolder() {
        return holder;
    }

    public onItemFunClickListener onItemFunClick;

    public void setOnItemFunClickListener(onItemFunClickListener l) {
        this.onItemFunClick = l;
    }

    public interface onItemFunClickListener {
        public void onPopBuyList(int position);

        public void onInToProductDetails(int position);

        public void onItemIntoShopPage(int positino);

        public void onFollowShop(int position);
    }
}
