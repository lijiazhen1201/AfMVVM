package cn.appoa.afmvvm.activity.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afmvvm.BR;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.app.MyApplication;
import cn.appoa.afmvvm.base.BaseActivity;
import cn.appoa.afmvvm.databinding.ActivityAesUtilsBinding;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;
import cn.appoa.afutils.app.KeyboardUtils;
import cn.appoa.afutils.encrypt.AESUtils;
import cn.appoa.afutils.file.ACache;
import cn.appoa.afutils.net.LogUtils;
import cn.appoa.afutils.res.ViewUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * AES加密工具类
 */
public class AESUtilsActivity extends BaseActivity<ActivityAesUtilsBinding, TitleBarViewModel>
        implements View.OnClickListener {

    @Override
    public int initContent(Bundle savedInstanceState) {
        return R.layout.activity_aes_utils;
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
        viewModel.setDefaultTitleBar(R.drawable.back_black, "AES加密工具类", true);
    }

    @Override
    public void initData() {
        binding.etDefaultKey.setText(MyApplication.aes_key);
        String aes_value = ACache.get(this).getAsString("aes_value");
        if (aes_value == null) {
            aes_value = "xuexiguofang";
        }
        binding.etDefaultValue.setText(aes_value);
        //点击事件
        binding.btnDefaultKey.setOnClickListener(this);
        binding.btnDefaultValue.setOnClickListener(this);
        binding.btnAes1.setOnClickListener(this);
        binding.btnAes2.setOnClickListener(this);
        binding.tvResult.setOnClickListener(this);
    }

    private ClipboardManager clipboard;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_aes_1 || v.getId() == R.id.btn_aes_2) {
            if (ViewUtils.isTextEmpty(binding.etDefaultKey)) {
                ToastUtils.showShort(mActivity, binding.etDefaultKey.getHint(), false);
                return;
            }
            Map<String, String> params = null;
            switch (v.getId()) {
                case R.id.btn_aes_1:
                    if (ViewUtils.isTextEmpty(binding.etDefaultValue)) {
                        ToastUtils.showShort(mActivity, binding.etDefaultValue.getHint(), false);
                        return;
                    }
                    params = getParams(ViewUtils.getText(binding.etDefaultValue));
                    break;
                case R.id.btn_aes_2:
                    if (ViewUtils.isTextEmpty(binding.etResultKey)) {
                        ToastUtils.showShort(mActivity, binding.etResultKey.getHint(), false);
                        return;
                    }
                    if (ViewUtils.isTextEmpty(binding.etResultValue)) {
                        ToastUtils.showShort(mActivity, binding.etResultValue.getHint(), false);
                        return;
                    }
                    params = getParams(ViewUtils.getText(binding.etResultKey), ViewUtils.getText(binding.etResultValue));
                    break;
                default:
                    break;
            }
            if (params != null) {
                String text = "";
                for (String key : params.keySet()) {
                    text = text + key + "<<————>>" + params.get(key) + "\n";
                }
                binding.tvResult.setText(text);
            }
            KeyboardUtils.closeSoftInput(mActivity);
        } else {
            switch (v.getId()) {
                case R.id.btn_default_key:
                    if (ViewUtils.isTextEmpty(binding.etDefaultKey)) {
                        ToastUtils.showShort(mActivity, binding.etDefaultKey.getHint(), false);
                    } else {
                        ACache.get(this).put("aes_key", ViewUtils.getText(binding.etDefaultKey));
                        ToastUtils.showShort(mActivity, "保存成功", false);
                    }
                    break;
                case R.id.btn_default_value:
                    if (ViewUtils.isTextEmpty(binding.etDefaultValue)) {
                        ToastUtils.showShort(mActivity, binding.etDefaultValue.getHint(), false);
                    } else {
                        ACache.get(this).put("aes_value", ViewUtils.getText(binding.etDefaultValue));
                        ToastUtils.showShort(mActivity, "保存成功", false);
                    }
                    break;
                case R.id.tv_result:
                    String text = binding.tvResult.getText().toString().trim();
                    if (!TextUtils.isEmpty(text)) {
                        if (clipboard == null) {
                            clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        }
                        clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
                        ToastUtils.showShort(mActivity, "复制成功", false);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 获取参数
     *
     * @return
     */
    public static Map<String, String> getParams(String value) {
        return getParams(null, value);
    }

    /**
     * 获取参数
     *
     * @return
     */
    public static Map<String, String> getParams(String key, String value) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(value)) {
            params.put("encrypt", AESUtils.encode(value));
            if (!TextUtils.isEmpty(key)) {
                params.put(key, value);
            }
        }
        LogUtils.i("params", params.toString());
        return params;
    }
}
