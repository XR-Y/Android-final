package com.example.finalproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.VideoView;


public class VideoActivity extends AppCompatActivity {

    public String TAG = "OnClickListener_Search_btn";
    public String KEY_TYPE_FROM = "type_from";

    public VideoView mVideoView;
    public ImageButton mHomeBtn;
    public ImageButton mSearchBtn;
    public ImageButton mAddBtn;
    public ImageButton mStoreBtn;
    public ImageButton mSelfBtn;

    public Bundle tmpBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_video);

        mVideoView = findViewById(R.id.video_view);

        mHomeBtn = findViewById(R.id.btn_home);
        mSearchBtn = findViewById(R.id.btn_search);
        mAddBtn = findViewById(R.id.btn_add);
        mStoreBtn = findViewById(R.id.btn_store);
        mSelfBtn = findViewById(R.id.btn_me);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager2);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        //获取Intent 检查是主页的还是搜索得来的 0是主页 1是搜索
        Intent intent = getIntent();
        if (intent.getIntExtra(KEY_TYPE_FROM, 0) == 1) {
            // 搜索得来的
            Log.d(TAG, String.valueOf(FetchVideos.curVideoId));
            viewPager2.setCurrentItem(FetchVideos.curVideoId);
        }

        tmpBundle = new Bundle();


        mSearchBtn.setOnClickListener(v -> {
            Log.d(TAG, "In OnClick");
            startActivity(new Intent(VideoActivity.this, SearchActivity.class));
            // 去除进场动画
            overridePendingTransition(0, 0);
            finish();
        });

        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Log.d(TAG, "In OnClick");
                Intent intent = new Intent(VideoActivity.this, VideoActivity.class);
                intent.putExtra(KEY_TYPE_FROM, 0);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoActivity.this, MyVideoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        mStoreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Intent intent = new Intent(VideoActivity.this, MessageActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mSelfBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                mVideoView.pause();
                Intent intent = new Intent(VideoActivity.this, SelfActivity.class);
                startActivity(intent);
                // 去除进场动画
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }


    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}