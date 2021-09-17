package cn.appoa.afmvvm.activity.widget;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.GoodsCategoryList;
import cn.appoa.afmvvm.bean.GoodsTypeList;
import cn.appoa.afmvvm.databinding.ActivityWheelViewBinding;
import cn.appoa.afui.dialog.StringWheelDialog;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 滚轮控件
 */
public class WheelViewActivity extends BaseActivity<ActivityWheelViewBinding, TitleBarViewModel>
        implements View.OnClickListener, OnCallbackListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_wheel_view;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "WheelView", true);
    }

    @Override
    public void initData() {
        binding.tvGoodsType.setOnClickListener(this);
        binding.tvGoodsCategory.setOnClickListener(this);
    }

    private StringWheelDialog dialogType;
    private StringWheelDialog dialogCategory;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_type:
                if (dialogType == null) {
                    getGoodsTypeList();
                } else {
                    dialogType.showDialog();
                }
                break;
            case R.id.tv_goods_category:
                if (dialogCategory == null) {
                    getGoodsCategoryList();
                } else {
                    dialogCategory.showDialog();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取商品类型
     */
    private void getGoodsTypeList() {
        //TODO 模拟数据
        List<GoodsTypeList> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new GoodsTypeList(i + 1, "商品类型" + (i + 1)));
        }
        setGoodsTypeList(datas);
    }

    private List<GoodsTypeList> datasType;

    /**
     * 设置商品类型
     *
     * @param datas
     */
    private void setGoodsTypeList(List<GoodsTypeList> datas) {
        datasType = datas;
        if (datasType != null && datasType.size() > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < datasType.size(); i++) {
                list.add(datasType.get(i).Name);
            }
            dialogType = new StringWheelDialog(mActivity, this, 1);
            dialogType.showStringWheelDialog("商品类型", list);
        }
    }

    /**
     * 获取商品分类
     */
    private void getGoodsCategoryList() {
        //TODO 模拟数据
        List<GoodsCategoryList> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new GoodsCategoryList(i + 1, "商品分类" + (i + 1)));
        }
        setGoodsCategoryList(datas);
    }

    private List<GoodsCategoryList> datasCategory;

    private void setGoodsCategoryList(List<GoodsCategoryList> datas) {
        datasCategory = datas;
        if (datasCategory != null && datasCategory.size() > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < datasCategory.size(); i++) {
                list.add(datasCategory.get(i).Name);
            }
            dialogCategory = new StringWheelDialog(mActivity, this, 2);
            dialogCategory.showStringWheelDialog("商品分类", list);
        }
    }

    /**
     * 类型id
     */
    private int typeId;

    /**
     * 分类id
     */
    private int categoryId;

    @Override
    public void onCallback(int type, Object... obj) {
        int position = (int) obj[0];
        String name = (String) obj[1];
        switch (type) {
            case 1:
                typeId = datasType.get(position).ID;
                binding.tvGoodsType.setText(name);
                break;
            case 2:
                categoryId = datasCategory.get(position).ID;
                binding.tvGoodsCategory.setText(name);
                break;
            default:
                break;
        }
    }
}
