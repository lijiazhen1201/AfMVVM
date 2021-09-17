package cn.appoa.afmvvm.activity.utils;

import android.os.Bundle;
import android.view.View;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityMapUtilsBinding;
import cn.appoa.afui.dialog.MapNavigationDialog;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * 地图导航工具类
 */
public class MapUtilsActivity extends BaseActivity<ActivityMapUtilsBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_map_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "MapUtils", true);
    }

    @Override
    public void initData() {
        binding.btnMapNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMap();
            }
        });
    }

    private MapNavigationDialog dialogNavigation;

    /**
     * 显示地图选择
     */
    private void showMap() {
        if (ViewUtils.isTextEmpty(binding.etLat)) {
            ToastUtils.showShort(mActivity, binding.etLat.getHint(), false);
            return;
        }
        if (ViewUtils.isTextEmpty(binding.etLng)) {
            ToastUtils.showShort(mActivity, binding.etLng.getHint(), false);
            return;
        }
        if (ViewUtils.isTextEmpty(binding.etAddress)) {
            ToastUtils.showShort(mActivity, binding.etAddress.getHint(), false);
            return;
        }
        double dlat = Double.parseDouble(ViewUtils.getText(binding.etLat));
        double dlon = Double.parseDouble(ViewUtils.getText(binding.etLng));
        String dname = ViewUtils.getText(binding.etAddress);
        if (dialogNavigation == null) {
            dialogNavigation = new MapNavigationDialog(mActivity);
        }
        dialogNavigation.showMapNavigationDialog(
                0, 0, null,//选填
                dlat, dlon, dname//必填
        );
    }
}
