package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;


public class MyVideoActivity extends AppCompatActivity {
    private VideoView videoView;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_VIDEO = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Intent intent1 = new Intent(MyVideoActivity.this, VideoActivity.class);
            Toast.makeText(MyVideoActivity.this, "视频已保存至默认Movies目录", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        verifyVideoPermissions(this);
        dispatchTakeVideoIntent();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        System.out.println("检测权限有没有"+permission);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            //动态获取权限
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    public static void verifyVideoPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        System.out.println("检测录像权限有没有"+permission);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            //动态获取权限
            ActivityCompat.requestPermissions(activity,PERMISSIONS_VIDEO ,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

}
