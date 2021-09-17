package cn.appoa.afmvvm.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.wangzhen.statusbar.DarkStatusBar;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.mvvm.BaseModel;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afimage.zxing.camera.CameraManager;
import cn.appoa.afimage.zxing.ui.ZmQRCodeFragment;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseImageActivity;
import cn.appoa.afmvvm.databinding.ActivityZmQrCodeBinding;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afui.BR;
import cn.appoa.afutils.net.LogUtils;
import cn.appoa.afutils.res.ScreenUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 微信扫一扫
 */
public class ZmQrCodeActivity extends BaseImageActivity<ActivityZmQrCodeBinding, BaseViewModel>
        implements CompoundButton.OnCheckedChangeListener, ZmQRCodeFragment.OnQRCodeResultListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_zm_qr_code;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel(AfApplication.getApplication(), new BaseModel());
    }

    private ZmQRCodeFragment fragment;

    @Override
    public void initViewObservable() {
        DarkStatusBar.get().fitLight(this);
        ScreenUtils.setPaddingTop(findViewById(R.id.mView));
        binding.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //扫描本地二维码（暂时有bug，页面重启后SurfaceView会黑屏）
                //selectPicFromAlbum();
            }
        });
        binding.cbQrcodeFlash.setOnCheckedChangeListener(this);
        fragment = new ZmQRCodeFragment();
        fragment.setOnQRCodeResultListener(this);
        mFragmentManager.beginTransaction().replace(R.id.rl_fragment, fragment).commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
        String[] permissions = {"android.permission.FLASHLIGHT"};
        requestPermissions(permissions, new PermissionsResultAction() {

            @Override
            public void onGranted() {
                if (isChecked) {
                    // 开启闪光灯
                    CameraManager.get().openLight();
                } else {
                    // 关闭闪光灯
                    CameraManager.get().offLight();
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtils.showShort(mActivity, "请开启闪光灯权限", false);
            }
        });
    }

    @Override
    public void initData() {
        //根据光线自动开启闪光灯（暂时有bug，检测有问题）
        //openSensorManager();
    }

    /**
     * 是否开启过
     */
    private boolean isOpenSensorManager;

    /**
     * 自动开启闪光灯
     */
    private void openSensorManager() {
        isOpenSensorManager = true;
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        sm.registerListener(new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (isOpenSensorManager) {
                    float lux = event.values[0];// 获取光线强度
                    int retval = Float.compare(lux, 10.0f);
                    if (retval > 0) {// 光线强度>10.0
                        CameraManager.get().offLight();
                    } else {
                        CameraManager.get().openLight();
                    }
                    isOpenSensorManager = false;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onQRCodeResult(String result, Bitmap barcode) {
        // 扫码结果
        LogUtils.i("扫码结果", result);
        ToastUtils.showShort(mActivity, result, false);
        setResult(RESULT_OK, new Intent()
                .putExtra("result", result));
        finish();
    }

    @Override
    public void getImageBitmap(Uri imageUri, String imagePath, Bitmap imageBitmap) {
        //1.裁剪图片
        cropImage(imageUri);
    }

    @Override
    public void getImageBitmap(Bitmap imageBitmap) {
        //3.裁剪结束时回调此方法，处理显示图片的逻辑
        if (imageBitmap != null) {
            if (fragment != null) {
                fragment.decodeBitmap(imageBitmap);
            }
        }
    }
}
