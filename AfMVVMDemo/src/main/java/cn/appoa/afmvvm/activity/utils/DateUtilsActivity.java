package cn.appoa.afmvvm.activity.utils;

import android.os.Bundle;

import java.util.Calendar;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityDateUtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.date.DateUtils;

/**
 * 时间格式化
 */
public class DateUtilsActivity extends BaseActivity<ActivityDateUtilsBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_date_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "DateUtils", true);
    }

    @Override
    public void initData() {
        Calendar calendar = Calendar.getInstance();
        //几秒前
        calendar.add(Calendar.SECOND, -10);
        binding.tvTime21.setText(DateUtils.getTimestampString(calendar.getTime()));
        //几分钟前
        calendar.add(Calendar.MINUTE, -5);
        binding.tvTime22.setText(DateUtils.getTimestampString(calendar.getTime()));
        //几小时前
        calendar.add(Calendar.HOUR_OF_DAY, -3);
        binding.tvTime23.setText(DateUtils.getTimestampString(calendar.getTime()));
        //昨天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        binding.tvTime24.setText(DateUtils.getTimestampString(calendar.getTime()));
        //前天
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        binding.tvTime25.setText(DateUtils.getTimestampString(calendar.getTime()));
        //更早
        calendar.add(Calendar.DAY_OF_MONTH, -10);
        binding.tvTime26.setText(DateUtils.getTimestampString(calendar.getTime()));
    }
}
