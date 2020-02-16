package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.easyflower.testvideo.R;
import com.easyflower.testvideo.utils.DensityUtil;

import java.util.List;

public class TestRecycleViewdataAdapter extends RecyclerView.Adapter<TestRecycleViewdataAdapter.ViewHolder> {
    private Activity act;
    private List<String> list;

    private RelativeLayout.LayoutParams params;

    public TestRecycleViewdataAdapter(Activity act, List<String> list) {
        this.act = act;
        this.list = list;

        int w = DensityUtil.getWidth(act);
        int h = DensityUtil.getHeight(act);
        params = new RelativeLayout.LayoutParams(w, h);
    }

    @NonNull
    @Override
    public TestRecycleViewdataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(act, R.layout.item_frame_vidio_image_view, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestRecycleViewdataAdapter.ViewHolder holder, int position) {
//        holder.video_view.setLayoutParams(params);
//
//        holder.video_item_shop_name.setText("" + list.get(position));


    }

    private void itemFunClickView(ImageView item_video_buy_list, RelativeLayout video_product_info_layout, RelativeLayout item_shop_info_layout, final int position) {

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

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    ViewHolder holder;

    public class ViewHolder extends RecyclerView.ViewHolder {



        public ViewHolder(@NonNull View itemView) {
            super(itemView);


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
    }
}
