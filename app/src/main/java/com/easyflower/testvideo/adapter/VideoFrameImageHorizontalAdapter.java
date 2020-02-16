package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.EditVideoActivity;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.utils.DensityUtil;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class VideoFrameImageHorizontalAdapter extends RecyclerView.Adapter<VideoFrameImageHorizontalAdapter.ViewHolder> {

    private Activity act;
    private List<Bitmap> bitmapList;

    private RelativeLayout.LayoutParams params;

    public VideoFrameImageHorizontalAdapter(Activity act, List<Bitmap> bitmapList) {
        this.act = act;
        this.bitmapList = bitmapList;

        int w = DensityUtil.getWidth(act) / 6;
        params = new RelativeLayout.LayoutParams(w, w);
    }

    @NonNull
    @Override
    public VideoFrameImageHorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = View.inflate(act, R.layout.item_frame_vidio_image_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoFrameImageHorizontalAdapter.ViewHolder holder, int position) {

        holder.item_frame_image.setLayoutParams(params);

//        holder.item_frame_image.setImageBitmap(bitmapList.get(position));

        Bitmap bitmap = bitmapList.get(position);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] bytes=baos.toByteArray();

        Glide.with(act).load(bytes).into(holder.item_frame_image);

    }

    @Override
    public int getItemCount() {
        return bitmapList != null ? bitmapList.size() : 0;
    }

    public void setNewData(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_frame_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_frame_image = itemView.findViewById(R.id.item_frame_image);
        }
    }
}
