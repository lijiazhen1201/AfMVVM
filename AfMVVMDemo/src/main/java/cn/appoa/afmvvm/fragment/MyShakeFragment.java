package cn.appoa.afmvvm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.mvvm.BaseModel;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.databinding.FragmentMyShakeBinding;
import cn.appoa.afui.BR;
import cn.appoa.afutils.file.ThreadUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 摇一摇实现类
 */
public class MyShakeFragment extends ZmShakeFragment<FragmentMyShakeBinding, BaseViewModel> {

    @Override
    public int initContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_my_shake;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel(AfApplication.getApplication(), new BaseModel());
    }

    @Override
    public void initViewObservable() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onShakeStart() {

    }

    /**
     * 动画时间
     */
    private final int DURATION_TIME = 600;

    @Override
    protected void initAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(DURATION_TIME);
        mytranslateanimup1.setStartOffset(DURATION_TIME);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        binding.shakeImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(DURATION_TIME);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(DURATION_TIME);
        mytranslateanimdn1.setStartOffset(DURATION_TIME);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        binding.shakeImgDown.startAnimation(animdn);

        // 动画监听，开始时显示加载状态，
        mytranslateanimdn0.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                animStart();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });

        mytranslateanimdn1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animEnd();
            }
        });
    }

    @Override
    protected void animStart() {
        super.animStart();
        // 隐藏线出现
        binding.shakeLineUp.setVisibility(View.VISIBLE);
        binding.shakeLineDown.setVisibility(View.VISIBLE);
    }

    @Override
    protected void animEnd() {
        super.animEnd();
        // 再次隐藏线
        binding.shakeLineUp.setVisibility(View.GONE);
        binding.shakeLineDown.setVisibility(View.GONE);
    }

    @Override
    protected void beforeLoading(CharSequence text) {
        super.beforeLoading(text);
        // 显示进度
        binding.shakeLoading.setVisibility(View.VISIBLE);
        binding.shakeText.setText(text);
    }

    @Override
    protected void afterLoading() {
        super.afterLoading();
        // 隐藏进度
        binding.shakeLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onShakeFinish() {
        //TODO 模拟掉接口
        beforeLoading("正在搜寻同一时刻摇晃手机的人");
        ThreadUtils.runInBack(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadUtils.runInMain(new Runnable() {//切换主线程
                    @Override
                    public void run() {
                        afterLoading();
                        ToastUtils.showShort(mActivity, "同一时刻没有用户摇晃手机", false);
                    }
                });
            }
        });
    }
}
