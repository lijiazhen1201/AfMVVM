package cn.appoa.afmvvm.activity.upload;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.util.Calendar;

import androidx.lifecycle.Observer;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseImageActivity;
import cn.appoa.afmvvm.databinding.ActivityUploadAvatarBinding;
import cn.appoa.afmvvm.model.MainMenuModel;
import cn.appoa.afmvvm.viewmodel.MainMenuViewModel;
import cn.appoa.afui.dialog.AreaWheelDialog;
import cn.appoa.afui.dialog.DatePickerDialog;
import cn.appoa.afutils.file.ImageUtils;
import cn.appoa.afutils.listener.OnCallbackListener;
import cn.appoa.afutils.toast.SnackbarUtils;

/**
 * 头像上传
 */
public class UploadAvatarActivity extends BaseImageActivity<ActivityUploadAvatarBinding, MainMenuViewModel>
        implements View.OnClickListener, OnCallbackListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_upload_avatar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public MainMenuViewModel initViewModel() {
        return new MainMenuViewModel(AfApplication.getApplication(), new MainMenuModel());
    }

    @Override
    public void initViewObservable() {
        viewModel.setDefaultTitleBar(R.drawable.back_black, "头像上传", true);
        viewModel.setRightText("保存");
        viewModel.menuOnClickEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(Void v) {
                //保存用户资料
            }
        });
    }

    @Override
    public void initData() {
        //点击事件
        binding.ivAvatar.setOnClickListener(this);
        binding.tvBirthday.setOnClickListener(this);
        binding.tvConstellation.setOnClickListener(this);
        binding.tvArea.setOnClickListener(this);
    }

    /**
     * 时间选择器
     */
    private DatePickerDialog dialogDate;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 省市区三级区域
     */
    private AreaWheelDialog dialogArea;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_avatar://头像
                showUploadImgDialog();
                break;
            case R.id.tv_birthday://生日
                if (dialogDate == null) {
                    dialogDate = new DatePickerDialog(mActivity, this, 1);
                    //生日从1900年1月1日到今天
                    dialogDate.initData(DatePickerDialog.str2Long("1900-01-01 00:00", false)
                            , System.currentTimeMillis());
                    //相关设置
                    //dialogDate.setCanShowPreciseTime(false);
                    //dialogDate.setCanShowMinuteTime(true);
                }
                if (TextUtils.isEmpty(birthday)) {
                    dialogDate.showDatePickerDialog("请选择日期", System.currentTimeMillis());
                } else {
                    dialogDate.showDatePickerDialog("请选择日期", birthday);
                }
                break;
            case R.id.tv_constellation://星座
                SnackbarUtils.Long(v, "选择生日后将自动设置星座")
                        .setAction("选择生日", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UploadAvatarActivity.this.onClick(binding.tvBirthday);
                            }
                        }).show();
                break;
            case R.id.tv_area://区域
                if (dialogArea == null) {
                    dialogArea = new AreaWheelDialog(mActivity, this);
                    dialogArea.showAreaDialog(binding.tvArea.getHint());
                } else {
                    dialogArea.showDialog();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCallback(int type, Object... obj) {
        if (type == 1) {
            birthday = (String) obj[0];
            binding.tvBirthday.setText(birthday);
            Calendar calendar = (Calendar) obj[1];
            binding.tvConstellation.setText(DatePickerDialog.getConstellation(calendar));
        }
        if (type < 0) {
            String provinceId = (String) obj[0];
            String cityId = (String) obj[1];
            String districtId = (String) obj[2];
            String provinceName = (String) obj[3];
            String cityName = (String) obj[4];
            String districtName = (String) obj[5];
            if (type == -1) {
                binding.tvArea.setText(provinceName + cityName + districtName);
            }
        }
    }

    /**
     * 是否裁剪图片
     */
    private boolean isCropImage = true;

    @Override
    public void getImageBitmap(Uri imageUri, String imagePath, Bitmap imageBitmap) {
        //1.拍照结束或选择相册里的图片结束会首先回调此方法
        if (isCropImage) {
            //如果需要裁剪，请添加以下代码
            cropImage(imageUri);
        } else {
            //不需要裁剪则直接处理显示图片的逻辑
            getImageBitmap(imageBitmap);
        }
    }

    /**
     * 图片base64
     */
    private String avatarBase64 = "";

    /**
     * 图片文件
     */
    private File avatarFile = null;

    @Override
    public void getImageBitmap(Bitmap imageBitmap) {
        //3.裁剪结束时回调此方法，处理显示图片的逻辑
        if (imageBitmap != null) {
            binding.ivAvatar.setImageBitmap(imageBitmap);
            //4.根据后台上传需要转base64或文件
            avatarBase64 = ImageUtils.bitmapToBase64(imageBitmap);
            avatarFile = ImageUtils.bitmapToFile(imageBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isCropImage && avatarFile != null) {
            avatarFile.delete();
            avatarFile = null;
        }
    }
}
