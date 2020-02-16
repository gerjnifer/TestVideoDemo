package com.easyflower.testvideo;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.coremedia.iso.boxes.Container;
import com.easyflower.testvideo.utils.Mp4ParserUtils;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditClipVideoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_clip_video);


        String path = "/storage/emulated/0/DCIM/Camera/VID_20191028_124338.mp4";
        String outPath = "/storage/emulated/0/Download/music.mp4";
        String outPath2 = "/storage/emulated/0/testEditVideo/clipVideozhaoxianhua_out.mp4";


//        createFile();
//        try {
//            cropMp4(path, 1, 5, outPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//            LogUtil.show("-------------------  e. " + e.getMessage());
//        }

        clipVideo();
    }

    final double [] times = {0.5d,6.9d};  //剪切1~3秒
    final String srcVideoPath = "/storage/emulated/0/DCIM/Camera/VID_20191028_124338.mp4";
    final String resVideoPath = "/storage/emulated/0/Download/ClipNewVideo.mp4";

    private void clipVideo() {
        try {
            Mp4ParserUtils.cutVideo(srcVideoPath, resVideoPath, times);
            Toast.makeText(EditClipVideoActivity.this, "剪切成功", Toast.LENGTH_LONG).show();
        } catch (IOException e) {

        }
    }

    private void createFile() {
//        File root = new File(getSDPath()
//                + SAVE_VIDEO_PATH);
//        if (root.exists()) {
//            LogUtil.i(" --------------- files.size " );
//
//
//        } else {
//            File root1 = new File(getSDPath()
//                    + SAVE_VIDEO_PATH);
//            if (root1.mkdirs()) {
//
//            }
//        }
    }


    /**
     * 将 MP4 切割
     *
     * @param mp4Path    .mp4
     * @param fromSample 起始位置
     * @param toSample   结束位置
     * @param outPath    .mp4
     */
    public static void cropMp4(String mp4Path, long fromSample, long toSample, String outPath) throws IOException {
        Movie mp4Movie = MovieCreator.build(mp4Path);
        Track videoTracks = null;// 获取视频的单纯视频部分
        for (Track videoMovieTrack : mp4Movie.getTracks()) {
            if ("vide".equals(videoMovieTrack.getHandler())) {
                videoTracks = videoMovieTrack;
            }
        }
        Track audioTracks = null;// 获取视频的单纯音频部分
        for (Track audioMovieTrack : mp4Movie.getTracks()) {
            if ("soun".equals(audioMovieTrack.getHandler())) {
                audioTracks = audioMovieTrack;
            }
        }

        Movie resultMovie = new Movie();
        resultMovie.addTrack(new AppendTrack(new CroppedTrack(videoTracks, fromSample, toSample)));// 视频部分
        resultMovie.addTrack(new AppendTrack(new CroppedTrack(audioTracks, fromSample, toSample)));// 音频部分

        Container out = new DefaultMp4Builder().build(resultMovie);
        FileOutputStream fos = new FileOutputStream(new File(outPath));
        out.writeContainer(fos.getChannel());
        fos.close();
    }
}
