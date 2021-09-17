package cn.appoa.afmvvm.activity.upload;

import android.os.Bundle;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityUploadVoiceBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 音频上传
 */
public class UploadVoiceActivity extends BaseActivity<ActivityUploadVoiceBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_upload_voice;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "音频上传", true);
    }

    @Override
    public void initData() {

    }
}
