package cn.appoa.afmvvm.activity.upload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cjt2325.cameralibrary.JCameraView;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmedia.activity.AfVideoPlayerActivity;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseImageActivity;
import cn.appoa.afmvvm.databinding.ActivityUploadVideoBinding;
import cn.appoa.afrecorder.activity.JCameraViewActivity;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 视频上传
 */
public class UploadVideoActivity extends BaseImageActivity<ActivityUploadVideoBinding, TitleBarViewModel>
        implements View.OnClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_upload_video;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "视频上传", true);
    }

    @Override
    public void initData() {
        binding.tvVideoUpload.setOnClickListener(this);
        binding.tvVideoPlay1.setOnClickListener(this);
        binding.tvVideoPlay2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tv_video_upload://上传视频
                showUploadVideoDialog();
                break;
            case R.id.tv_video_play1://本地播放
                if (ViewUtils.isTextEmpty(binding.tvVideoPath)) {
                    ToastUtils.showShort(mActivity, "请先上传视频", false);
                } else {
                    startActivity(new Intent(mActivity, AfVideoPlayerActivity.class)
                            .putExtra("videoUrl", recorderUrl)
                            .putExtra("videoImage", recorderPath)
                    );
                }
                break;
            case R.id.tv_video_play2://网络播放
                startActivity(new Intent(mActivity, AfVideoPlayerActivity.class)
                        .putExtra("videoUrl", "http://200024424.vod.myqcloud.com/200024424_670222d0bdf811e6ad39991f76a4df69.f30.mp4")
                        .putExtra("videoImage", "https://mc.qcloudimg.com/static/img/c635908bebbdf8fb747388a83902886e/mlvb_basic_function+(2).jpeg")
                        .putExtra("videoTitle", "基础功能")
                );
                break;
            default:
                break;
        }
    }

    @Override
    public void selectVideoFromCamera() {
        //super.selectVideoFromCamera();
        startActivityForResult(new Intent(mActivity, JCameraViewActivity.class)
                        .putExtra("recorderState", JCameraView.BUTTON_STATE_ONLY_RECORDER)
                        .putExtra("recorderDuration", 15)
                , REQUEST_CODE_RECORD);
    }

    @Override
    public void selectVideoFromAlbum() {
        super.selectVideoFromAlbum();
    }

    private int recorderType;
    private String recorderPath;
    private String recorderUrl;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RECORD && resultCode == Activity.RESULT_OK) {
            //拍摄成功
            recorderType = data.getIntExtra("recorderType", 1);
            recorderUrl = data.getStringExtra("recorderUrl");
            recorderPath = data.getStringExtra("recorderPath");
            getVideoPath(1, recorderUrl, recorderPath);
        }
    }

    @Override
    protected void getVideoPath(int type, String videoPath, String videoImage) {
        recorderUrl = videoPath;
        recorderPath = videoImage;
        binding.tvVideoPath.setText(videoPath);
    }
}
