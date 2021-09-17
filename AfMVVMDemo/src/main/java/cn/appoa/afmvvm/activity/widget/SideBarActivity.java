package cn.appoa.afmvvm.activity.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.core.content.ContextCompat;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.mvvm.BaseModel;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.app.MyApplication;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivitySideBarBinding;
import cn.appoa.afui.bean.BMapApi;
import cn.appoa.afui.bean.BMapCityList;
import cn.appoa.afui.bean.BMapProvinceList;
import cn.appoa.afui.widget.side.PinyinComparator;
import cn.appoa.afui.widget.side.SideBar;
import cn.appoa.afui.widget.side.Sort;
import cn.appoa.afui.widget.side.SortAdapter;
import cn.appoa.afui.widget.side.SortBaseAdapter;
import cn.appoa.afutils.app.KeyboardUtils;
import cn.appoa.afutils.res.ScreenUtils;
import cn.appoa.afutils.res.SpannableStringUtils;
import cn.appoa.afutils.res.ViewUtils;

/**
 * 字母表导航，城市选择
 */
public class SideBarActivity extends BaseActivity<ActivitySideBarBinding, BaseViewModel>
        implements SideBar.OnPressDownLetterListener, TextView.OnEditorActionListener,
        View.OnClickListener, SortBaseAdapter.OnSortClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_side_bar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public BaseViewModel initViewModel() {
        return new BaseViewModel(AfApplication.getApplication(), new BaseModel());
    }

    @Override
    public void initViewObservable() {
        ScreenUtils.setPaddingTop(binding.llSearch);
        //当前城市
        binding.tvCity.setText(SpannableStringUtils.getBuilder("当前城市：")
                .append(MyApplication.local_city_name)
                .setForegroundColor(ContextCompat.getColor(mActivity, R.color.colorText))
                .create());
        //设置字母表
        binding.mSideBar.setDialogView(binding.mTextView);
        binding.mSideBar.setLetterColor(ContextCompat.getColor(mActivity, R.color.colorTheme),
                ContextCompat.getColor(mActivity, R.color.colorWhite));
        binding.mSideBar.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.colorTransparent),
                ContextCompat.getColor(mActivity, R.color.colorTranslucence));
        binding.mSideBar.setOnPressDownLetterListener(this);
        //事件监听
        binding.etSearch.setOnEditorActionListener(this);
        binding.tvSearch.setOnClickListener(this);
        binding.mListView.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardUtils.closeSoftInput(mActivity);
                return false;
            }
        });
    }

    @Override
    public void initData() {
        //TODO 模拟数据
        List<BMapCityList> list = new ArrayList<>();
        List<BMapProvinceList> provinceList = BMapApi.getProvinceList(mActivity);
        for (int i = 0; i < provinceList.size(); i++) {
            BMapProvinceList province = provinceList.get(i);
            String provinceName = province.area_name;
            if (provinceName.startsWith("北京") || provinceName.startsWith("重庆") ||
                    provinceName.startsWith("上海") || provinceName.startsWith("天津") ||
                    provinceName.startsWith("香港") || provinceName.startsWith("澳门")) {
                BMapCityList city = new BMapCityList();
                city.area_code = province.area_code;
                city.area_name = provinceName;
                city.area_type = province.area_type;
                city.geo = province.geo;
                list.add(city);
            } else {
                list.addAll(province.sub);
            }
        }
        setCityList(list);
    }

    private List<Sort> sortList;
    private SortBaseAdapter adapter;

    /**
     * 设置城市
     *
     * @param cityList
     */
    private void setCityList(List<BMapCityList> cityList) {
        sortList = new ArrayList<>();
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                BMapCityList c = cityList.get(i);
                Sort s = new Sort(c.area_name);
                s.setCustomInfo("id", c.area_code);
                if (!TextUtils.isEmpty(s.name) && s.name.startsWith("重庆")) {
                    s.setInitialLetter("C");
                }
                sortList.add(s);
            }
        }
        Collections.sort(sortList, new PinyinComparator());
        adapter = new SortAdapter(mActivity, sortList);
        adapter.setOnSortClickListener(this);
        binding.mListView.setAdapter(adapter);
    }

    @Override
    public void onPressDownLetter(int index, String letter) {
        // 该字母首次出现的位置
        if (TextUtils.equals(letter, "↑")) {
            // 置顶
            if (binding.mListView != null) {
                binding.mListView.setSelection(0);
            }
        } else {
            int position = -1;
            if (adapter != null) {
                position = adapter.getPositionForSection(letter.charAt(0));
            }
            if (position != -1 && binding.mListView != null) {
                binding.mListView.setSelection(position + binding.mListView.getHeaderViewsCount());
            }
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.et_search && actionId == EditorInfo.IME_ACTION_SEARCH) {
            onClick(binding.tvSearch);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search) {
            //发起搜索
            String key = ViewUtils.getText(binding.etSearch);
            KeyboardUtils.closeSoftInput(mActivity);
            if (adapter != null) {
                adapter.searchData(key);
            }
        }
    }

    @Override
    public void onSortClick(int position, Sort sort) {
        //点击事件
        MyApplication.local_city_id = (String) sort.getCustomInfo("id");
        MyApplication.local_city_name = sort.name;
        setResult(RESULT_OK, new Intent()
                .putExtra("city", sort.name));
        finish();
    }
}
