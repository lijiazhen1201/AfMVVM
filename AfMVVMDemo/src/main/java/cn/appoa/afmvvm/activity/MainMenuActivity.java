package cn.appoa.afmvvm.activity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.adapter.MainMenuAdapter;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.databinding.ActivityMainMenuBinding;
import cn.appoa.afmvvm.model.MainMenuModel;
import cn.appoa.afmvvm.viewmodel.MainMenuViewModel;

/**
 * 单个列表Activity
 */
public abstract class MainMenuActivity extends BaseActivity<ActivityMainMenuBinding, MainMenuViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_main_menu;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, initTitle(), true);
    }

    @Override
    public void initData() {
        List<MainMenu> dataList = initMainMenuList();
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        MainMenuAdapter adapter = new MainMenuAdapter(dataList, viewModelId, viewModel);
        binding.setAdapter(adapter);
    }

    /**
     * 标题
     *
     * @return
     */
    protected abstract String initTitle();

    /**
     * 列表
     *
     * @return
     */
    protected abstract List<MainMenu> initMainMenuList();
}
