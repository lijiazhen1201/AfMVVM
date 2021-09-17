package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityCenterSquareImageViewBinding;
import cn.appoa.afmvvm.net.API;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

/**
 * 图片截取正中间的正方形部分的ImageView
 */
public class CenterSquareImageViewActivity extends BaseActivity<ActivityCenterSquareImageViewBinding, TitleBarViewModel>
        implements CompoundButton.OnCheckedChangeListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_center_square_image_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "CenterSquareImageView", true);
    }

    @Override
    public void initData() {
        //事件监听
        binding.btnHorizontal.setOnCheckedChangeListener(this);
        binding.btnVertical.setOnCheckedChangeListener(this);

        binding.btnHorizontal.setChecked(true);
    }

    private String url1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569038400883&di=50efc288ac56cd3a6a3e58231a4f471e&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2F201504%2F04%2F185242kumuqezkckyykc4z.jpg";
    private String url2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1569038435876&di=1a4debee39980e615f20105f4e207d36&imgtype=0&src=http%3A%2F%2Fim6.leaderhero.com%2Fwallpaper%2F20150210%2Faa10f8d39a.jpg";

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            int type = 0;
            switch (buttonView.getId()) {
                case R.id.btn_horizontal:
                    type = 1;
                    break;
                case R.id.btn_vertical:
                    type = 2;
                    break;
                default:
                    break;
            }
            if (type == 1) {
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url1, binding.ivImageNormal);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url1, binding.ivImageCenter);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url1, binding.ivImageHorizontal);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url1, binding.ivImageVertical);
                binding.ivImageHorizontal.setVisibility(View.VISIBLE);
                binding.ivImageVertical.setVisibility(View.GONE);
            } else if (type == 2) {
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url2, binding.ivImageNormal);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url2, binding.ivImageCenter);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url2, binding.ivImageHorizontal);
                AfApplication.getImageLoader().loadImage(API.IMAGE_URL + url2, binding.ivImageVertical);
                binding.ivImageHorizontal.setVisibility(View.GONE);
                binding.ivImageVertical.setVisibility(View.VISIBLE);
            }
        }
    }
}
