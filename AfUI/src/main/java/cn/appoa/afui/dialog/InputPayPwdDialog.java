package cn.appoa.afui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import cn.appoa.afbase.dialog.BaseDialog;
import cn.appoa.afui.R;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afui.widget.gridpassword.GridPasswordView;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 支付密码输入弹窗
 */
public class InputPayPwdDialog extends BaseDialog implements GridPasswordView.OnPasswordChangedListener {

    public InputPayPwdDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    private GridPasswordView mGridPasswordView;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_input_pay_pwd, null);
        mGridPasswordView = (GridPasswordView) view.findViewById(R.id.mGridPasswordView);
        mGridPasswordView.setOnPasswordChangedListener(this);
        view.findViewById(R.id.iv_close_dialog).setOnClickListener(this);
        view.findViewById(R.id.tv_forget_pwd).setOnClickListener(this);
        return initBottomInputMethodDialog(view, context);
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        if (v.getId() == R.id.tv_forget_pwd) {
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_FORGET_PAY_PWD);
            }
        }
        if (v.getId() == R.id.iv_close_dialog) {
            dismissDialog();
        }
    }

    @Override
    public void onTextChanged(String psw) {

    }

    @Override
    public void onInputFinish(final String psw) {
        // 验证支付密码
        if (onCallbackListener != null) {
            onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_INPUT_PAY_PWD, psw);
        }
        dismissDialog();
    }

    /**
     * 输入密码（自动弹出键盘）
     */
    public void showInputPayPwdDialog() {
        showDialog();
        mGridPasswordView.setForceInputViewGetFocus();
        mGridPasswordView.postDelayed(new Runnable() {

            @Override
            public void run() {

            }
        }, 500);
    }

}
