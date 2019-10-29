package com.duoyi.smallvideocompress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.duoyi.smallvideolib.SiliCompressor;
import com.duoyi.smallvideolib.hardcompression.MediaController;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button compress;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        compress = findViewById(R.id.compress);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            comPressVideo("/storage/emulated/0/DCIM/Camera/VID_20191029_194624.mp4", "/sdcard/aaaaa/"+System.currentTimeMillis()+".mp4");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
    }

    /**
     * 压缩视频文件
     *
     * @param srcPath 压缩源文件
     * @param outPath 压缩目标文件
     */
    private void comPressVideo(String srcPath, String outPath) throws URISyntaxException {
        String compressedFilePath = SiliCompressor.with(this)
                .compressVideo(srcPath, outPath, 960, 540, 500000, new MediaController.CompressListener() {
                    @Override
                    public void start() {
                        Log.d("Mainctivity","start");
                    }

                    @Override
                    public void complete(long costTime) {
                        Log.d("Mainctivity","complete = " + costTime);
                    }

                    @Override
                    public void error() {
                        Log.d("Mainctivity","error");
                    }
                });
        Log.d(TAG, "compressedFilePath = " + compressedFilePath);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }
}
