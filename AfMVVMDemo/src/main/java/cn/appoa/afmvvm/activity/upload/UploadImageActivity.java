package cn.appoa.afmvvm.activity.upload;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cjt2325.cameralibrary.JCameraView;

import java.util.ArrayList;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityUploadImageBinding;
import cn.appoa.afmvvm.dialog.UploadImgVideoDialog;
import cn.appoa.afrecorder.activity.JCameraViewActivity;
import cn.appoa.afselector.view.PhotoPickerGridView;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 图片上传
 */
public class UploadImageActivity extends BaseActivity<ActivityUploadImageBinding, TitleBarViewModel>
        implements UploadImgVideoDialog.OnUploadImgVideoListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_upload_image;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "图片上传", true);
        binding.tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    /**
     * 拍摄请求码
     */
    private static final int REQUEST_CODE_VIDEO_PICKER = 1;

    /**
     * 选择请求码
     */
    private static final int REQUEST_CODE_PHOTO_PICKER = 2;

    /**
     * 上传弹窗
     */
    private UploadImgVideoDialog dialogUpload;

    @Override
    public void initData() {
        //设置是否可拍照
        //mPhotoPickerGridView.setCamera(true);
        //设置是否可选视频
        //mPhotoPickerGridView.setVideo(true);
        //设置视频最大时长
        binding.mPhotoPickerGridView.setVideoDuration(10);
        //设置最大数量（默认9张）
        binding.mPhotoPickerGridView.setMax(3);
        //设置自定义上传图片
        //mPhotoPickerGridView.setDefaultAddRes(R.drawable.icon_camera);
        //设置是否转换base64（上传文件时不用转换base64）
        //mPhotoPickerGridView.setToBase64(false);
        //设置监听（必须写）
        binding.mPhotoPickerGridView.setImageLoader(new PhotoPickerGridView.DefaultPhotoPickerImageLoader() {

            @Override
            public void loadImage(String path, ImageView iv) {
                //加载图片
                AfApplication.getImageLoader().loadImage(path, iv);
            }

            @Override
            public int getRequestCode() {
                //请求码
                return REQUEST_CODE_PHOTO_PICKER;
            }

            @Override
            public boolean isUploadSelf() {
                //自定义点击事件
                return true;
            }

            @Override
            public void onClickAddPic() {
                //点击添加按钮
                if (dialogUpload == null) {
                    dialogUpload = new UploadImgVideoDialog(mActivity);
                    dialogUpload.setOnUploadImgVideoListener(UploadImageActivity.this);
                }
                dialogUpload.showDialog();
            }
        });
        //添加网络图片（编辑图片用到）
        //ArrayList<String> photos = new ArrayList<>();
        //photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556273439607&di=6a21f28036c66f7c6a76b2b32c04f7e9&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F71b2cbb3gw1f5i55vwmjxj21kw1b8the.jpg");
        //photos.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556273447435&di=755672b5fb5454c7ec487232fc7136bc&imgtype=0&src=http%3A%2F%2Fgss0.bdstatic.com%2F6ONWsjip0QIZ8tyhnq%2Fit%2Fu%3D2952176766%2C2579102747%26fm%3D173%26s%3DD0B11DD74C0256D4001CD9FC0300C03B%26w%3D640%26h%3D601%26img.JPEG");
        //mPhotoPickerGridView.addPhotos(photos);
        //添加网络视频（编辑视频用到）
        //mPhotoPickerGridView.addVideo("http://200024424.vod.myqcloud.com/200024424_670222d0bdf811e6ad39991f76a4df69.f30.mp4");
    }

    @Override
    public void onUploadImgVideo(int type) {
        if (type == 1) {
            //拍摄
            startActivityForResult(new Intent(mActivity, JCameraViewActivity.class)
                            .putExtra("recorderState", JCameraView.BUTTON_STATE_BOTH)
                            .putExtra("recorderDuration", 15)
                    , REQUEST_CODE_VIDEO_PICKER);
        } else if (type == 2) {
            //从相册选择
            binding.mPhotoPickerGridView.uploadPics();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_VIDEO_PICKER:
                int recorderType = data.getIntExtra("recorderType", 1);
                String recorderPath = data.getStringExtra("recorderPath");
                String recorderUrl = data.getStringExtra("recorderUrl");
                if (recorderType == 1) {
                    //拍照成功
                    ArrayList<String> photos = new ArrayList<>();
                    photos.add(recorderPath);
                    //添加本地图片
                    binding.mPhotoPickerGridView.addPhotos(photos);
                } else if (recorderType == 2) {
                    //录像成功,添加本地视频
                    binding.mPhotoPickerGridView.addVideo(recorderUrl);
                }
                break;
            case REQUEST_CODE_PHOTO_PICKER:
                //添加Intent传递的视频或图片
                binding.mPhotoPickerGridView.addData(data);
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        if (binding.mPhotoPickerGridView.getVideo() == null && binding.mPhotoPickerGridView.getPhotoSize() == 0) {
            ToastUtils.showShort(mActivity, "请添加图片或视频", false);
            return;
        }
        if (binding.mPhotoPickerGridView.getVideo() == null) {
            showLoading("正在上传图片...");
            binding.mPhotoPickerGridView.getBase64Photos(mActivity, ",", new PhotoPickerGridView.GetBase64PhotosListener() {
                @Override
                public void getBase64Photos(String base64) {
                    //掉接口上传
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dismissLoading();
                    ToastUtils.showShort(mActivity, "上传图片成功", false);
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            showLoading("正在上传视频...");
            binding.mPhotoPickerGridView.getBase64Video(mActivity, new PhotoPickerGridView.GetBase64VideoListener() {
                @Override
                public void getBase64Video(String base64) {
                    //掉接口上传
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    dismissLoading();
                    ToastUtils.showShort(mActivity, "上传视频成功", false);
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }
}
