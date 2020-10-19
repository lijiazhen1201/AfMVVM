package cn.appoa.afrecorder.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.FileUtil;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.appoa.afrecorder.R;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 视频录制界面
 */
public class JCameraViewActivity extends AppCompatActivity
        implements ErrorListener, JCameraListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent(getIntent());
        getPermissions();
    }

    protected int recorderState;//状态值(默认两种都可)
    protected int recorderDuration;//时长(单位秒)
    protected int recorderQuality;//质量(默认高质量)
    protected String recorderPath;//保存路径(默认相册)

    protected void initIntent(Intent intent) {
        recorderState = intent.getIntExtra("recorderState", JCameraView.BUTTON_STATE_BOTH);
        recorderDuration = intent.getIntExtra("recorderDuration", 0);
        recorderQuality = intent.getIntExtra("recorderQuality", JCameraView.MEDIA_QUALITY_HIGH);
        recorderPath = intent.getStringExtra("recorderPath");
    }

    private final int GET_PERMISSION_REQUEST = 100; //权限申请自定义码

    /**
     * 权限申请
     */
    protected void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager
                    .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager
                            .PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager
                            .PERMISSION_GRANTED) {
                initContent();
            } else {
                //不具有获取权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA}, GET_PERMISSION_REQUEST);
            }
        } else {
            initContent();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == GET_PERMISSION_REQUEST) {
            int size = 0;
            if (grantResults.length >= 1) {
                int writeResult = grantResults[0];
                //读写内存权限
                boolean writeGranted = writeResult == PackageManager.PERMISSION_GRANTED;//读写内存权限
                if (!writeGranted) {
                    size++;
                }
                //录音权限
                int recordPermissionResult = grantResults[1];
                boolean recordPermissionGranted = recordPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!recordPermissionGranted) {
                    size++;
                }
                //相机权限
                int cameraPermissionResult = grantResults[2];
                boolean cameraPermissionGranted = cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
                if (!cameraPermissionGranted) {
                    size++;
                }
                if (size == 0) {
                    initContent();
                } else {
                    ToastUtils.showShort(this, R.string.setting_open_permission, false);
                    finish();
                }
            }
        }
    }

    protected JCameraView videoRecorder;

    @SuppressLint("SourceLockedOrientationActivity")
    protected void initContent() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        videoRecorder = new JCameraView(this);
        setContentView(videoRecorder);

        initData();
    }

    protected void initData() {
        // 设置只能录像或只能拍照或两种都可以（默认两种都可以）
        videoRecorder.setFeatures(recorderState);
        //设置时长
        if (recorderDuration == 0) {
            recorderDuration = 10;
        }
        videoRecorder.setDuration(recorderDuration * 1000 + 1000);
        // 设置视频质量
        videoRecorder.setMediaQuality(recorderQuality);
        // 设置视频保存路径(默认保存到相册)
        if (TextUtils.isEmpty(recorderPath)) {
            recorderPath = "DCIM/Camera";
        }
        videoRecorder.setSaveVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + recorderPath);
        //事件监听
        videoRecorder.setErrorLisenter(this);
        videoRecorder.setJCameraLisenter(this);
        videoRecorder.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void onError() {
        ToastUtils.showShort(this, R.string.open_camera_failed, false);
        finish();
    }

    @Override
    public void AudioPermissionError() {
        ToastUtils.showShort(this, R.string.no_recording_permission, false);
        finish();
    }

    @Override
    public void captureSuccess(Bitmap bitmap) {
        setRecorderResult(1, FileUtil.saveBitmap(recorderPath, bitmap), "");
    }

    @Override
    public void recordSuccess(String url, Bitmap firstFrame) {
        setRecorderResult(2, FileUtil.saveBitmap(recorderPath, firstFrame), url);
    }

    /**
     * 设置录制结果
     *
     * @param type 1拍照成功2录像成功
     * @param path 图片地址
     * @param url  视频地址
     */
    private void setRecorderResult(int type, String path, String url) {
        Intent intent = new Intent();
        intent.putExtra("recorderType", type);
        intent.putExtra("recorderPath", path);
        intent.putExtra("recorderUrl", url);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoRecorder.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoRecorder.onPause();
    }
}
