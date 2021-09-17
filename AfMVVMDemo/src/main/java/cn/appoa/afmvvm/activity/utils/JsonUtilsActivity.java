package cn.appoa.afmvvm.activity.utils;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.bean.GoodsCategoryList;
import cn.appoa.afmvvm.bean.GoodsDetails;
import cn.appoa.afmvvm.bean.GoodsSpecItemList;
import cn.appoa.afmvvm.bean.GoodsTypeList;
import cn.appoa.afmvvm.databinding.ActivityJsonUtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.net.JsonUtils;

/**
 * Json解析工具类
 */
public class JsonUtilsActivity extends BaseActivity<ActivityJsonUtilsBinding, TitleBarViewModel>
        implements View.OnClickListener{

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_json_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "JsonUtils", true);
    }

    private String json1;
    private String json2;

    @Override
    public void initData() {
        json1 = JsonUtils.getJsonString(mActivity, "goods.json");
        json2 = JsonUtils.getJsonString(mActivity, "spec.json");
        binding.btnJsonToBean.setOnClickListener(this);
        binding.btnBeanToJson.setOnClickListener(this);
        binding.btnJsonToList.setOnClickListener(this);
        binding.btnListToJson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String result = "";
        switch (v.getId()) {
            case R.id.btn_json_to_bean:
                GoodsDetails data = JSON.parseObject(json1, GoodsDetails.class);
                if (data != null) {
                    result = data.Name;
                }
                break;
            case R.id.btn_bean_to_json:
                GoodsCategoryList category = new GoodsCategoryList();
                category.ID = 1;
                category.Name = "商品分类";
                result = JSON.toJSONString(category);
                break;
            case R.id.btn_json_to_list:
                List<GoodsSpecItemList> datas = JSON.parseArray(json2, GoodsSpecItemList.class);
                if (datas != null && datas.size() > 0) {
                    for (int i = 0; i < datas.size(); i++) {
                        result = result + datas.get(i).Name + "\n";
                    }
                }
                break;
            case R.id.btn_list_to_json:
                List<GoodsTypeList> typeList = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    GoodsTypeList type = new GoodsTypeList();
                    type.ID = i + 1;
                    type.Name = "商品类型" + (i + 1);
                    typeList.add(type);
                }
                result = JSON.toJSONString(typeList);
                break;
            default:
                break;
        }
        binding.tvResult.setText(result);
    }
}
