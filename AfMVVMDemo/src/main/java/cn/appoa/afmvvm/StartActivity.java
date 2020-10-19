package cn.appoa.afmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.Arrays;

import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityStartBinding;
import cn.appoa.afmvvm.dialog.RequestPermissionsDialog;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afpermission.utils.PermissionUtils;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 启动页
 */
@Route(path = RouterActivityPath.ACTIVITY_START)
public class StartActivity extends BaseActivity<ActivityStartBinding, BaseViewModel>
        implements Animation.AnimationListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_start;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {
        startAnim();
    }

    /**
     * 渐变展示启动屏
     */
    protected void startAnim() {
        AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
        aa.setDuration(3000);
        binding.ivStart.startAnimation(aa);
        aa.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {
        binding.ivStart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        String[] permissions = new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
        };
        if (PermissionUtils.hasPermission(StartActivity.this, Arrays.asList(permissions))) {
            endAnim();
        } else {
            new RequestPermissionsDialog(StartActivity.this, new OnCallbackListener() {
                @Override
                public void onCallback(int type, Object... obj) {
                    endAnim();
                }
            }).showRequestPermissionsDialog(StartActivity.this, permissions);
        }
    }

    /**
     * 动画结束
     */
    protected void endAnim() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.start_alpha, R.anim.end_alpha);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //有极光推送添加以下代码
        //JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //有极光推送添加以下代码
        //JPushInterface.onPause(this);
    }

    @Override
    public boolean enableSliding() {
        //禁止侧滑返回
        return false;
    }
}
