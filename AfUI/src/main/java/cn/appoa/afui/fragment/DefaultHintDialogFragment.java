package cn.appoa.afui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import androidx.lifecycle.Observer;
import cn.appoa.afbase.fragment.AfDialogFragment;
import cn.appoa.afui.BR;
import cn.appoa.afui.R;
import cn.appoa.afui.databinding.DialogFragmentDefaultHintBinding;
import cn.appoa.afui.viewmodel.DefaultHintViewModel;

/**
 * 默认提示框
 */
public class DefaultHintDialogFragment extends AfDialogFragment
        <DialogFragmentDefaultHintBinding, DefaultHintViewModel> {

    /**
     * 1一个按钮（确定）2两个按钮（取消/确定）
     */
    private int type;
    private String cancel;
    private String confirm;
    private String title;
    private String message;

    private static DefaultHintDialogFragment getInstance(int type, String cancel, String confirm,
                                                         String title, String message) {
        DefaultHintDialogFragment fragment = new DefaultHintDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putString("cancel", cancel);
        bundle.putString("confirm", confirm);
        bundle.putString("title", title);
        bundle.putString("message", message);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static DefaultHintDialogFragment getInstance1(String message) {
        return getInstance(1, null, null, null, message);
    }

    public static DefaultHintDialogFragment getInstance1(String title, String message) {
        return getInstance(1, null, null, title, message);
    }

    public static DefaultHintDialogFragment getInstance1(String confirm, String title, String message) {
        return getInstance(1, null, confirm, title, message);
    }

    public static DefaultHintDialogFragment getInstance2(String message) {
        return getInstance(2, null, null, null, message);
    }

    public static DefaultHintDialogFragment getInstance2(String title, String message) {
        return getInstance(2, null, null, title, message);
    }

    public static DefaultHintDialogFragment getInstance2(String confirm, String title, String message) {
        return getInstance(2, null, confirm, title, message);
    }

    public static DefaultHintDialogFragment getInstance2(String cancel, String confirm, String title, String message) {
        return getInstance(2, cancel, confirm, title, message);
    }

    @Override
    public void initArguments(Bundle arguments) {
        type = arguments.getInt("type", 2);
        cancel = arguments.getString("cancel");
        confirm = arguments.getString("confirm");
        title = arguments.getString("title");
        message = arguments.getString("message");
    }

    @Override
    public int initContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.dialog_fragment_default_hint;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    protected Map<String, Object> initDialogParams() {
        return initCenterToastDialog();
    }

    @Override
    public void initViewObservable() {
        viewModel.onClickEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (onCallbackListener != null) {
                    onCallbackListener.onCallback(integer);
                }
                dismiss();
            }
        });
    }

    @Override
    public void initData() {
        if (viewModel == null || viewModel.getDefaultHintModel() == null) {
            return;
        }
        if (type == 1) {
            viewModel.getDefaultHintModel().cancelVisible.set(View.GONE);
            viewModel.getDefaultHintModel().lineVisible.set(View.GONE);
        } else if (type == 2) {
            viewModel.getDefaultHintModel().cancelVisible.set(View.VISIBLE);
            viewModel.getDefaultHintModel().lineVisible.set(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(cancel)) {
            viewModel.getDefaultHintModel().cancelText.set(cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {
            viewModel.getDefaultHintModel().confirmText.set(confirm);
        }
        if (!TextUtils.isEmpty(title)) {
            viewModel.getDefaultHintModel().titleText.set(title);
        }
        if (!TextUtils.isEmpty(message)) {
            viewModel.getDefaultHintModel().contentText.set(message);
        }
    }

}
