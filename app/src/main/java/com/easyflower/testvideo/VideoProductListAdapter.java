package com.easyflower.testvideo;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.easyflower.testvideo.media.widget.media.IjkVideoView;
import com.easyflower.testvideo.testdata.VideoData;

import java.util.List;

public class VideoProductListAdapter extends RecyclerView.Adapter<VideoProductListAdapter.ViewHolder> {

    private Activity act;
    private List<VideoData> list;

    public VideoProductListAdapter(Activity act, List<VideoData> list) {
        this.act = act;
        this.list = list;
    }

    @NonNull
    @Override
    public VideoProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(act, R.layout.item_video_list_view, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoProductListAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl_loading_layout;

        private IjkVideoView video_view;
        private ImageView iv_player_center_pause;
        private SeekBar sb_player_seekbar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            video_view = itemView.findViewById(R.id.video_view);
            iv_player_center_pause = itemView.findViewById(R.id.iv_player_center_pause);
            sb_player_seekbar = itemView.findViewById(R.id.sb_player_seekbar);

        }
    }
}
