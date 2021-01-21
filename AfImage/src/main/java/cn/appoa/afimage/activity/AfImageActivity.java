package cn.appoa.afimage.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.ViewDataBinding;
import cn.appoa.afbase.activity.AfActivity;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afimage.R;
import cn.appoa.afimage.cropper.CropImage;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afui.dialog.DefaultUploadImgDialog;
import cn.appoa.afui.dialog.DefaultUploadVideoDialog;
import cn.appoa.afutils.file.FileCompatUtils;
import cn.appoa.afutils.file.ImageUtils;
import cn.appoa.afutils.listener.OnCallbackListener;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 带上传图片的Activity基类
 */
public abstract class AfImageActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends AfActivity<V, VM> {

    protected DefaultUploadImgDialog imgUpload;
    protected DefaultUploadVideoDialog videoUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUpload = new DefaultUploadImgDialog(this, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                if (type == CallbackType.CALLBACK_TYPE_UPLOAD_IMAGE_CAMERA) {
                    // 拍照
                    selectPicFromCamera();
                } else if (type == CallbackType.CALLBACK_TYPE_UPLOAD_IMAGE_ALBUM) {
                    // 相册
                    selectPicFromAlbum();
                }
            }
        });
        videoUpload = new DefaultUploadVideoDialog(this, new OnCallbackListener() {
            @Override
            public void onCallback(int type, Object... obj) {
                if (type == CallbackType.CALLBACK_TYPE_UPLOAD_VIDEO_CAMERA) {
                    // 拍摄
                    selectVideoFromCamera();
                } else if (type == CallbackType.CALLBACK_TYPE_UPLOAD_VIDEO_ALBUM) {
                    // 本地选择
                    selectVideoFromAlbum();
                }
            }
        });
    }

    /**
     * 显示上传图片弹窗
     */
    public void showUploadImgDialog() {
        imgUpload.showDialog();
    }

    /**
     * 显示上传视频弹窗
     */
    public void showUploadVideoDialog() {
        videoUpload.showDialog();
    }

    // 需要的权限
    // <uses-permission android:name="android.permission.CAMERA"/>
    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    // 需要的provider(authorities的路径每个app需不同)
    // <provider
    // android:name="cn.appoa.afimage.provider.AfFileProvider"
    // android:authorities="${applicationId}.fileprovider"
    // android:exported="false"
    // android:grantUriPermissions="true">
    // <meta-data
    // android:name="android.support.FILE_PROVIDER_PATHS"
    // android:resource="@xml/file_provider_paths" />
    // </provider>

    /**
     * 拍照的请求码
     */
    public static final int REQUEST_CODE_CAMERA = 1001;

    /**
     * 相册的请求码
     */
    public static final int REQUEST_CODE_ALBUM = 1002;
    /**
     * 拍摄的请求码
     */
    public static final int REQUEST_CODE_RECORD = 1003;

    /**
     * 选择的请求码
     */
    public static final int REQUEST_CODE_VIDEO = 1004;

    /**
     * 接收到的图片的uri
     */
    protected Uri imageUri = null;

    /**
     * 拍照的临时文件
     */
    protected File cameraFile;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isDeleteFile()) {
            if (cameraFile != null && cameraFile.exists()) {
                cameraFile.delete();
                cameraFile = null;
            }
        }
    }

    /**
     * 是否删除临时文件
     */
    protected boolean isDeleteFile() {
        return false;
    }

    /**
     * 获取临时文件
     */
    protected File getTempFile() {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/DCIM/Camera", System.currentTimeMillis() + ".jpeg");
        }
        return file;
    }

    /**
     * 拍照获取图片
     */
    public void selectPicFromCamera() {
        String[] permissions = {
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, new PermissionsResultAction() {

            @Override
            public void onGranted() {
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    ToastUtils.showShort(mActivity, R.string.not_found_sd_card, false);
                    return;
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    // 7.0系统
                    doTakePhotoNew();
                } else {
                    doTakePhotoOld();
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtils.showShort(mActivity, R.string.open_camera_permission, false);
            }
        });
    }

    /**
     * 老方法[Android7.0以及以上报错FileUriExposedException]
     */
    private void doTakePhotoOld() {
        cameraFile = getTempFile();
        if (cameraFile != null) {
            cameraFile.getParentFile().mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    /**
     * 解决三星手机拍照后无法获取数据，android 7.0手机拍照后获取数据适配。 报错FileUriExposedException(SamSung
     * SM-N9006 Android5.0也有这问题)
     */
    private void doTakePhotoNew() {
        cameraFile = getTempFile();
        if (cameraFile != null) {
            cameraFile.getParentFile().mkdirs();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", cameraFile);
            List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                grantUriPermission(packageName, imageUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        }
    }

    /**
     * 从图库获取图片
     */
    public void selectPicFromAlbum() {
        String[] permissions = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermissions(permissions, new PermissionsResultAction() {

            @Override
            public void onGranted() {
                Intent intent = null;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                if (intent != null) {
                    startActivityForResult(intent, REQUEST_CODE_ALBUM);
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtils.showShort(mActivity, R.string.open_album_permission, false);
            }
        });
    }

    /**
     * 拍摄视频
     */
    public void selectVideoFromCamera() {

    }

    /**
     * 选择视频
     */
    public void selectVideoFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*"); // 选择视频
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            // 拍照
            if (cameraFile != null && cameraFile.exists()) {
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", cameraFile);
                } else {
                    imageUri = Uri.fromFile(cameraFile);
                }
                String imagePath = cameraFile.getAbsolutePath();
                getImageBitmap(imageUri, imagePath, ImageUtils.getBitmap(imagePath));
            }
        }
        if (requestCode == REQUEST_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            // 图库
            if (data != null) {
                if (data.getData() != null) {
                    imageUri = data.getData();
                } else {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                }
                String imagePath = ImageUtils.getPhotoPathFromContentUri(this, imageUri);
                getImageBitmap(imageUri, imagePath, ImageUtils.getBitmap(imagePath));
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //裁剪
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (result != null && result.getUri() != null) {
                getImageBitmap(ImageUtils.uriToBitmap(this, result.getUri()));
            }
        }
        if (requestCode == REQUEST_CODE_VIDEO && resultCode == Activity.RESULT_OK) {
            //选择视频
            if (data != null) {
                Uri uri = data.getData();
                String videoPath = FileCompatUtils.getPath(mActivity, uri);
                String videoImage = FileCompatUtils.getVideoImage(mActivity, uri);
                getVideoPath(2, videoPath, videoImage);
            }
        }
    }

    /**
     * 裁剪图片（比例1:1）
     *
     * @param imageUri
     */
    protected void cropImage(Uri imageUri) {
        cropImage(imageUri, 1, 1);
    }

    /**
     * 裁剪图片（自定义比例）
     *
     * @param imageUri
     * @param aspectRatioX
     * @param aspectRatioY
     */
    protected void cropImage(Uri imageUri, int aspectRatioX, int aspectRatioY) {
        CropImage.activity(imageUri).setAspectRatio(aspectRatioX, aspectRatioY).start(this);
    }

    /**
     * 处理拍照或图库获取的图片
     *
     * @param imageUri    图片uri
     * @param imagePath   图片路径
     * @param imageBitmap 压缩后的bitmap
     */
    protected void getImageBitmap(Uri imageUri, String imagePath, Bitmap imageBitmap) {

    }

    /**
     * 处理裁剪后获得的bitmap
     *
     * @param imageBitmap 裁剪后的bitmap
     */
    protected void getImageBitmap(Bitmap imageBitmap) {

    }

    /**
     * 获取视频地址
     *
     * @param type       1拍摄2选择
     * @param videoPath  视频地址
     * @param videoImage 视频封面
     */
    protected void getVideoPath(int type, String videoPath, String videoImage) {

    }
}

