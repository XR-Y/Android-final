package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    public String TAG = "MainActivity";
    public String KEY_TYPE_FROM = "type_from";
    private LottieAnimationView animationView;
    private LoadVideoThread loadVideoThread = new LoadVideoThread(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FetchVideos.getVideos();
        animationView = findViewById(R.id.animation_view);
        animationView.playAnimation();
        loadVideoThread.start();
//        initNetVideo();
    }
    @SuppressLint("HandlerLeak")
    Handler videoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 777:
                    initNetVideo();
            }
        }
    };

    class LoadVideoThread extends Thread {

        private Context curActivity;

        public LoadVideoThread(Context context) {
            super();
            curActivity = context;
        }

        @Override
        public void run() {
            super.run();
//            Videos.getVideos();
            while (FetchVideos.videos == null) {
                try {
                    sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            for(VideoData videoData: FetchVideos.videos){
                FetchVideos.getHttpBitmap(videoData.getFeedUrl());
            }
            Message message = new Message();
            message.what = 777;
            videoHandler.sendMessage(message);
        }
    }

    private void initNetVideo() {

        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
        intent.putExtra(KEY_TYPE_FROM, 0);
        startActivity(intent);
        finish();
        // ??????????????????
        overridePendingTransition(0, 0);
    }

}