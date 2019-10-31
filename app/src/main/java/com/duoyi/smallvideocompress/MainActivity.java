package com.duoyi.smallvideocompress;


import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.duoyi.smallvideolib.SiliCompressor;
import com.duoyi.smallvideolib.hardcompression.MediaController;

import java.net.URISyntaxException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private Button compress;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        compress = findViewById(R.id.compress);
        compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            comPressVideo("/sdcard/DCIM/Camera/VID_20191029_141818.mp4", "/sdcard/aaaaa/"+System.currentTimeMillis()+".mp4");
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                });
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

                    @Override
                    public void onFirstBitmap(Bitmap bitmap) {

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
