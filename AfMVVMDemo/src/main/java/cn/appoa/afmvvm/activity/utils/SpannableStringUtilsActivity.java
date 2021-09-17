package cn.appoa.afmvvm.activity.utils;

import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivitySpannableStringUtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.res.SpannableStringUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * SpannableString工具类
 */
public class SpannableStringUtilsActivity extends BaseActivity<ActivitySpannableStringUtilsBinding, TitleBarViewModel> {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_spannable_string_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "SpannableStringUtils", true);
    }

    private int colorAccent;

    @Override
    public void initData() {
        //此行必须加上，不然点击事件无效
        binding.tvResult.setMovementMethod(LinkMovementMethod.getInstance());
        colorAccent = ContextCompat.getColor(mActivity, R.color.colorAccent);
        binding.tvResult.setText(SpannableStringUtils
                .getBuilder("SpannableStringUtils相关方法\n")//获取建造者
                .append("设置标识\n").setFlag(Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                .append("设置前景色\n").setForegroundColor(colorAccent)
                .append("设置背景色\n").setBackgroundColor(colorAccent)
                .append("设置引用线的颜色\n").setQuoteColor(colorAccent)
                .append("设置缩进\n").setLeadingMargin(100, 300)
                .append("设置列表标记\n").setBullet(100, colorAccent)
                .append("设置字体比例2倍\n").setProportion(2f)
                .append("设置字体比例0.5倍\n").setProportion(0.5f)
                .append("设置字体横向比例2倍\n").setXProportion(2f)
                .append("设置字体横向比例0.5倍\n").setXProportion(0.5f)
                .append("设置删除线\n").setStrikethrough()
                .append("设置下划线\n").setUnderline()
                .append("设置").append("上标\n").setSuperscript()
                .append("设置").append("下标\n").setSubscript()
                .append("设置粗体\n").setBold()
                .append("设置斜体\n").setItalic()
                .append("设置粗斜体\n").setBoldItalic()
                .append("设置字体monospace\n").setFontFamily("monospace")
                .append("设置字体serif\n").setFontFamily("serif")
                .append("设置字体sans-serif\n").setFontFamily("sans-serif")
                .append("设置对齐正常\n").setAlign(Layout.Alignment.ALIGN_NORMAL)
                .append("设置对齐相反\n").setAlign(Layout.Alignment.ALIGN_OPPOSITE)
                .append("设置对齐居中\n").setAlign(Layout.Alignment.ALIGN_CENTER)
                .append("设置图片位图\n").setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .append("设置图片资源\n").setDrawable(ContextCompat.getDrawable(mActivity, R.mipmap.ic_launcher))
                //.append("设置图片uri\n").setUri()
                .append("设置图片资源id\n").setResourceId(R.mipmap.ic_launcher)
                .append("设置点击事件\n").setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        ToastUtils.showShort(mActivity, "点击事件", false);
                    }
                })
                .append("设置超链接\n").setUrl("https://www.baidu.com")
                .append("设置模糊NORMAL\n").setBlur(3, BlurMaskFilter.Blur.NORMAL)
                .append("设置模糊SOLID\n").setBlur(3, BlurMaskFilter.Blur.SOLID)
                .append("设置模糊OUTER\n").setBlur(3, BlurMaskFilter.Blur.OUTER)
                .append("设置模糊INNER\n").setBlur(3, BlurMaskFilter.Blur.INNER)
                .create());//创建样式字符串
    }
}
