package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easyflower.testvideo.R;
import com.easyflower.testvideo.utils.DensityUtil;
import com.easyflower.testvideo.view.OnRecycleViewItemClickListener;

import java.util.List;

public class VideoBuyListAdapter extends RecyclerView.Adapter<VideoBuyListAdapter.ViewHolder> {

    private Activity act;
    private List<String> list;
    private OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    private RelativeLayout.LayoutParams paramsImg;

    public VideoBuyListAdapter(Activity act, List<String> list) {
        this.act = act;
        this.list = list;

        int w = DensityUtil.getWidth(act) / 5;
        paramsImg = new RelativeLayout.LayoutParams(w, w);
    }


    @NonNull
    @Override
    public VideoBuyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(act, R.layout.item_pop_video_buy_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoBuyListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.buy_list_img.setLayoutParams(paramsImg);

        viewHolder.item_buy_list_name.setText("");

        viewHolder.item_buy_list_info.setText("" + list.get(i));

        setOnItemClickListener(viewHolder.item_parent_view, i);
    }

    private void setOnItemClickListener(RelativeLayout item_parent_view, final int position) {


        /**
         * 进入商品详情
         */
        item_parent_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleViewItemClickListener != null) {
                    onRecycleViewItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView buy_list_img;
        private TextView item_buy_list_name;
        private TextView item_buy_list_info;

        private RelativeLayout item_parent_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_parent_view = itemView.findViewById(R.id.item_parent_view);

            buy_list_img = itemView.findViewById(R.id.buy_list_img);
            item_buy_list_name = itemView.findViewById(R.id.item_buy_list_name);
            item_buy_list_info = itemView.findViewById(R.id.item_buy_list_info);

        }
    }

    public void setOnRecycleViewItemClickListener(OnRecycleViewItemClickListener l) {
        this.onRecycleViewItemClickListener = l;
    }
}
