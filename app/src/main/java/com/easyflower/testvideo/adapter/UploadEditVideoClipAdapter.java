package com.easyflower.testvideo.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.easyflower.testvideo.LogUtil;
import com.easyflower.testvideo.R;
import com.easyflower.testvideo.rangebar.RotateTransformation;
import com.easyflower.testvideo.testdata.EditVideoData;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by ZBK on 2017/10/13.
 * Describe:
 */

public class UploadEditVideoClipAdapter extends RecyclerView.Adapter<UploadEditVideoClipAdapter.Holder> {
    private Activity act;
    private List<EditVideoData> dataList;
    private int imagWidth = -1;
    private String parentPath;
    /**
     * 视频的旋转角度,手机拍摄的视频有时候会有一个旋转角度，因此解析出的图片也有一个角度
     */
    private float rotation = 0;


    public UploadEditVideoClipAdapter(Activity act, List<EditVideoData> dataList) {
        this.act = act;
        this.dataList = dataList;

    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(act).inflate(R.layout.item_per_image, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {


        if (imagWidth > 0) {
            ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
            layoutParams.width = imagWidth;
            holder.imageView.setLayoutParams(layoutParams);
        }


        if (dataList.get(position).getBitmap() != null && dataList.get(position).getBytes() != null) {
//            Glide.with(act).load(dataList.get(position).getBitmap()).transform(new RotateTransformation(act, rotation))
//                    .into(holder.imageView);

            byte[] bytes = dataList.get(position).getBytes();

            Glide.with(act).load(bytes).transform(new RotateTransformation(act, rotation)).into(holder.imageView);

            LogUtil.show("--------------   有图 " + imagWidth);

        } else {

            LogUtil.show("--------------  没图 " + imagWidth);

            Glide.with(act).load(R.mipmap.ic_launcher)
                    .into(holder.imageView);

        }

    }

    public List<EditVideoData> getDataList() {
        return dataList;
    }

    public void setDataList(List<EditVideoData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }


    public int getImagWidth() {
        return imagWidth;
    }

    public void setImagWidth(int imagWidth) {
        this.imagWidth = imagWidth;
    }

    public void setNewData(List<EditVideoData> adapterDataList) {
        this.dataList = adapterDataList;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }
    }

}
