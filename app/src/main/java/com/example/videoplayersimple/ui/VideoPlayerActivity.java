package com.example.videoplayersimple.ui;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.VideoView;

import com.example.videoplayersimple.R;
import com.example.videoplayersimple.bean.VideoItem;

/*
 *  @项目名：  VideoPlayerSimple 
 *  @包名：    com.example.videoplayersimple
 *  @文件名:   VideoPlayerActivity
 *  @创建者:   ZYONG
 *  @创建时间:  2017/1/25 0025 21:14
 *  @描述：    TODO
 */
public class VideoPlayerActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {
    private static final String TAG = "VideoPlayerActivity";
    private VideoView videoView;

    @Override
    protected void initData() {
        VideoItem videoItem = (VideoItem) getIntent().getSerializableExtra("videoItem");
        videoView.setVideoPath(videoItem.getPath());
    }

    @Override
    protected void initListener() {
        videoView.setOnPreparedListener(this);
    }

    @Override
    protected void initView() {
        videoView = (VideoView) findViewById(R.id.videoView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void processClick(View v) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //开启播放
        videoView.start();
    }
}
