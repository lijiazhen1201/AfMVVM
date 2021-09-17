package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityNumberAnimTextViewBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 数字增加和减小动画TextView
 */
public class NumberAnimTextViewActivity extends BaseActivity<ActivityNumberAnimTextViewBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_number_anim_text_view;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TitleBarViewModel initViewModel() {
        return new TitleBarViewModel(AfApplication.getApplication(), new TitleBarModel());
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "NumberAnimTextView", true);
    }

    @Override
    public void initData() {
        // 设置前缀
        binding.tvNumberAnim1.setPrefixString("¥ ");
        binding.tvNumberAnim1.setNumberString("98765432.75");
        // 设置后缀
        binding.tvNumberAnim2.setPostfixString("%");
        binding.tvNumberAnim2.setNumberString("86.39");
        // 设置动画时长
        binding.tvNumberAnim3.setDuration(2000);
        // 设置数字增加范围
        //binding.tvNumberAnim3.setNumberString("19.75", "99.75");
        binding.tvNumberAnim3.setNumberString("99.75", "19.75");
    }
}
