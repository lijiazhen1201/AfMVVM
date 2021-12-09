package cn.appoa.afmvvm.activity.ui;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityIndexBinding;
import cn.appoa.afmvvm.fragment.IndexFragment;
import cn.appoa.afmvvm.router.RouterActivityPath;
import cn.appoa.afmvvm.viewmodel.IndexViewModel;

/**
 * 主页Activity
 */
@Route(path = RouterActivityPath.ACTIVITY_INDEX)
public class IndexActivity extends BaseActivity<ActivityIndexBinding, IndexViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        onGetInstanceState(savedInstanceState);
        return R.layout.activity_index;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        if (binding != null) {
            binding.mIndexView.initManager(mFragmentManager)
                    .addIndexItem("首页", R.drawable.index_tab1_btn_image,
                            IndexFragment.class.getCanonicalName(), getTestBundle("Home"))
                    .addIndexItem("分类", R.drawable.index_tab2_btn_image,
                            IndexFragment.class.getCanonicalName(), getTestBundle("Category"))
                    .addIndexItem("搜索", R.drawable.index_tab3_btn_image,
                            IndexFragment.class.getCanonicalName(), getTestBundle("Search"))
                    .addIndexItem("我的", R.drawable.index_tab4_btn_image,
                            IndexFragment.class.getCanonicalName(), getTestBundle("Mine"));
        }
    }

    /**
     * 测试用传递数据
     *
     * @param pageName
     * @return
     */
    private Bundle getTestBundle(String pageName) {
        Bundle bundle = new Bundle();
        bundle.putString("pageName", pageName);
        return bundle;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int index = intent.getIntExtra("index", 0);
        if (binding != null) {
            binding.mIndexView.setCheckedIndex(index);
        }
    }

    @Override
    public void initData() {
        if (binding != null) {
            binding.mIndexView.startIndex();
        }
    }

    /**
     * 获取保存的Fragment
     *
     * @param savedInstanceState
     */
    private void onGetInstanceState(Bundle savedInstanceState) {
        if (binding != null) {
            binding.mIndexView.onGetInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //保存当前Fragment
        if (binding != null) {
            binding.mIndexView.onSaveInstanceState(outState);
        }
    }
}
