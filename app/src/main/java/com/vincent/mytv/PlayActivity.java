package com.vincent.mytv;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.victor.loading.newton.NewtonCradleLoading;
import com.vincent.mytv.utils.MsgUtil;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

public class PlayActivity extends AppCompatActivity implements IMediaPlayer.OnCompletionListener {
    private static final String TAG = "PlayActivity";
    private NewtonCradleLoading newtonCradleLoading;
    IjkVideoView videoView;
    AndroidMediaController controller;
    String url;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
    }

    private void initView() {
        getData();
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        videoView = (IjkVideoView) findViewById(R.id.ijkPlayer);
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();
        controller = new AndroidMediaController(this, false);
        videoView.setMediaController(controller);
        videoView.setOnCompletionListener(this);
        videoView.setLoadingView(newtonCradleLoading);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    private void getData() {
        url = getIntent().getStringExtra(Constant.TV_URL);
        name = getIntent().getStringExtra(Constant.TV_NAME);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        MsgUtil.i(TAG, "onCompletion");
    }
}
