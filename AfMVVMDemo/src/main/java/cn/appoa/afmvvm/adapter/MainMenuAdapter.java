package cn.appoa.afmvvm.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.bean.MainMenu;
import cn.appoa.afmvvm.databinding.ItemMainMenuBinding;
import cn.appoa.afmvvm.viewmodel.MainViewModel;

public class MainMenuAdapter extends BaseQuickAdapter<MainMenu, BaseDataBindingHolder> {

    private int viewModelId;
    private MainViewModel viewModel;

    public MainMenuAdapter(@Nullable List<MainMenu> data) {
        this(data, 0, null);
    }

    public MainMenuAdapter(@Nullable List<MainMenu> data, int viewModelId, MainViewModel viewModel) {
        super(R.layout.item_main_menu, data);
        this.viewModelId = viewModelId;
        this.viewModel = viewModel;
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder helper, MainMenu item) {
        ItemMainMenuBinding binding = DataBindingUtil.getBinding(helper.itemView);
        if (binding == null || item == null) {
            return;
        }
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
        binding.setMainMenu(item);
        binding.executePendingBindings();
    }

}