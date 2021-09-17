package cn.appoa.afmvvm.activity.upload;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.ArrayList;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.loader.LoadingBitmapListener;
import cn.appoa.afeditor.AfEditor;
import cn.appoa.afeditor.IMGEditActivity;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseImageActivity;
import cn.appoa.afmvvm.databinding.ActivityUploadImageEditBinding;
import cn.appoa.afui.activity.ShowBigImageListActivity;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 图片上传编辑
 */
public class UploadImageEditActivity extends BaseImageActivity<ActivityUploadImageEditBinding, TitleBarViewModel>
        implements View.OnClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_upload_image_edit;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "图片上传编辑", true);
    }

    @Override
    public void initData() {
        binding.btnChooseImage.setOnClickListener(this);
        binding.btnEditImage.setOnClickListener(this);
        binding.ivImage1.setOnClickListener(this);
        binding.ivImage2.setOnClickListener(this);
    }

    private String imagePath1;
    private String imagePath2;
    private static final int REQUEST_CODE_IMAGE_EDIT = 3;

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            //选择图片
            case R.id.btn_choose_image:
                showUploadImgDialog();
                break;
            //编辑图片
            case R.id.btn_edit_image:
                if (TextUtils.isEmpty(imagePath1)) {
                    ToastUtils.showShort(mActivity, "请先选择图片", false);
                } else {
                    AfEditor.getInstance().editImage(mActivity, imagePath1, REQUEST_CODE_IMAGE_EDIT);
                }
                break;
            case R.id.iv_image1:
                previewImage(imagePath1);
                break;
            case R.id.iv_image2:
                previewImage(imagePath2);
                break;
            default:
                break;
        }
    }

    /**
     * 预览图片
     *
     * @param image
     */
    private void previewImage(String image) {
        if (TextUtils.isEmpty(image)) {
            return;
        }
        ArrayList<String> images = new ArrayList<>();
        images.add(image);
        startActivity(new Intent(mActivity, ShowBigImageListActivity.class)
                .putExtra("page", 0)
                .putStringArrayListExtra("images", images));
    }

    @Override
    protected void getImageBitmap(Uri imageUri, String imagePath, Bitmap imageBitmap) {
        imagePath1 = imagePath;
        binding.ivImage1.setImageBitmap(imageBitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_EDIT && resultCode == Activity.RESULT_OK && data != null) {
            imagePath2 = data.getStringExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH);
            AfApplication.getImageLoader().downloadImage(imagePath2, new LoadingBitmapListener() {
                @Override
                public void loadingBitmapSuccess(Bitmap bitmap) {
                    if (bitmap != null) {
                        binding.ivImage2.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void loadingBitmapFailed() {

                }
            });
        }
    }
}
