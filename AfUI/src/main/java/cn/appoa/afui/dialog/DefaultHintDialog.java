package cn.appoa.afui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.appoa.afbase.dialog.BaseDialog;
import cn.appoa.afui.R;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 默认提示框
 */
public class DefaultHintDialog extends BaseDialog {

    public DefaultHintDialog(Context context) {
        super(context);
    }

    public TextView tv_hint_title;
    public TextView tv_hint_message;
    public TextView tv_hint_cancel;
    public TextView tv_hint_confirm;
    public View lint_hint_middle;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_default_hint, null);
        tv_hint_title = (TextView) view.findViewById(R.id.tv_hint_title);
        tv_hint_message = (TextView) view.findViewById(R.id.tv_hint_message);
        tv_hint_cancel = (TextView) view.findViewById(R.id.tv_hint_cancel);
        tv_hint_confirm = (TextView) view.findViewById(R.id.tv_hint_confirm);
        lint_hint_middle = view.findViewById(R.id.lint_hint_middle);
        tv_hint_cancel.setOnClickListener(this);
        tv_hint_confirm.setOnClickListener(this);
        return initCenterToastDialog(view, context);
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        if (onCallbackListener != null) {
            if (v.getId() == R.id.tv_hint_cancel) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_CANCEL);
            }
            if (v.getId() == R.id.tv_hint_confirm) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_CONFIRM);
            }
        }
        dismissDialog();
    }

    /**
     * 按钮数量
     */
    private void showButtonCount(int type) {
        tv_hint_title.setText(context.getResources().getString(R.string.dialog_hint));
        tv_hint_message.setText(null);
        tv_hint_cancel.setText(context.getResources().getString(R.string.dialog_cancel));
        tv_hint_confirm.setText(context.getResources().getString(R.string.dialog_confirm));
        if (type == 1) {
            tv_hint_cancel.setVisibility(View.GONE);
            tv_hint_confirm.setVisibility(View.VISIBLE);
            lint_hint_middle.setVisibility(View.GONE);
        } else if (type == 2) {
            tv_hint_cancel.setVisibility(View.VISIBLE);
            tv_hint_confirm.setVisibility(View.VISIBLE);
            lint_hint_middle.setVisibility(View.VISIBLE);
        }
    }

    public void showHintDialog1(CharSequence message, OnCallbackListener onCallbackListener) {
        showButtonCount(1);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

    public void showHintDialog1(CharSequence title, CharSequence message, OnCallbackListener onCallbackListener) {
        showButtonCount(1);
        tv_hint_title.setText(title);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

    public void showHintDialog1(CharSequence confirm, CharSequence title, CharSequence message,
                                OnCallbackListener onCallbackListener) {
        showButtonCount(1);
        tv_hint_confirm.setText(confirm);
        tv_hint_title.setText(title);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

    public void showHintDialog2(CharSequence message, OnCallbackListener onCallbackListener) {
        showButtonCount(2);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

    public void showHintDialog2(CharSequence title, CharSequence message, OnCallbackListener onCallbackListener) {
        showButtonCount(2);
        tv_hint_title.setText(title);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

    public void showHintDialog2(CharSequence cancel, CharSequence confirm, CharSequence title, CharSequence message,
                                OnCallbackListener onCallbackListener) {
        showButtonCount(2);
        tv_hint_cancel.setText(cancel);
        tv_hint_confirm.setText(confirm);
        tv_hint_title.setText(title);
        tv_hint_message.setText(message);
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

}
