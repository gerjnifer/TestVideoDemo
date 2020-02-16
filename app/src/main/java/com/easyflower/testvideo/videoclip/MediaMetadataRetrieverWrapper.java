package com.easyflower.testvideo.videoclip;

public class MediaMetadataRetrieverWrapper {
    private RetrieverProcessThread retrieverProcessThread;

    public MediaMetadataRetrieverWrapper() {
        retrieverProcessThread = new RetrieverProcessThread();

    }

    public void setDataSource(String targetPath) {
        retrieverProcessThread.setDataSource(targetPath);
    }

    public void getFramesInterval(int interval, int scale, RetrieverProcessThread.BitmapCallBack callBack) {
        retrieverProcessThread.getFramesInterval(interval, scale, callBack);
    }
}
