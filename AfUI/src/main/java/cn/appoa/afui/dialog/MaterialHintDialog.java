package cn.appoa.afui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import cn.appoa.afui.R;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * MD风格提示框
 */
public class MaterialHintDialog extends DefaultHintDialog {

    public static MaterialHintDialog getInstance(Context context) {
        MaterialHintDialog dialog = new MaterialHintDialog(context);
        return dialog;
    }

    public MaterialHintDialog(Context context) {
        super(context);
    }

    private TextView tv_hint_title;
    private TextView tv_hint_message;
    private TextView tv_hint_cancel;
    private TextView tv_hint_confirm;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_material_hint, null);
        tv_hint_title = (TextView) view.findViewById(R.id.tv_hint_title);
        tv_hint_message = (TextView) view.findViewById(R.id.tv_hint_message);
        tv_hint_cancel = (TextView) view.findViewById(R.id.tv_hint_cancel);
        tv_hint_confirm = (TextView) view.findViewById(R.id.tv_hint_confirm);
        tv_hint_cancel.setOnClickListener(this);
        tv_hint_confirm.setOnClickListener(this);
        return initDialog(view, context, Gravity.CENTER, android.R.style.Animation_Toast,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 0.6f);
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

    public MaterialHintDialog setCancelable(boolean flag) {
        if (dialog != null) {
            dialog.setCancelable(flag);
        }
        return this;
    }

    public MaterialHintDialog setLinkMovementMethod() {
        return setMovementMethod(LinkMovementMethod.getInstance());
    }

    public MaterialHintDialog setMovementMethod(MovementMethod movement) {
        if (tv_hint_message != null) {
            tv_hint_message.setMovementMethod(movement);
        }
        return this;
    }

    public MaterialHintDialog setTextGravityCenter() {
        return setTextGravity(Gravity.CENTER);
    }

    public MaterialHintDialog setTextGravity(int gravity) {
        if (tv_hint_message != null) {
            tv_hint_message.setGravity(gravity);
        }
        return this;
    }

    @Override
    public void showHintDialog1(CharSequence message, OnCallbackListener onCallbackListener) {
        showHintDialog1(null, message, onCallbackListener);
    }

    @Override
    public void showHintDialog1(CharSequence title, CharSequence message, OnCallbackListener onCallbackListener) {
        showHintDialog1(null, title, message, onCallbackListener);
    }

    @Override
    public void showHintDialog1(CharSequence confirm, CharSequence title, CharSequence message,
                                OnCallbackListener onCallbackListener) {
        showHintDialog(false, null, confirm, title, message, onCallbackListener);
    }

    @Override
    public void showHintDialog2(CharSequence message, OnCallbackListener onCallbackListener) {
        showHintDialog2(null, message, onCallbackListener);
    }

    @Override
    public void showHintDialog2(CharSequence title, CharSequence message, OnCallbackListener onCallbackListener) {
        showHintDialog2(null, null, title, message, onCallbackListener);
    }

    @Override
    public void showHintDialog2(CharSequence cancel, CharSequence confirm, CharSequence title, CharSequence message,
                                OnCallbackListener onCallbackListener) {
        showHintDialog(true, cancel, confirm, title, message, onCallbackListener);
    }

    private void showHintDialog(boolean showCancel, CharSequence cancel, CharSequence confirm,
                                CharSequence title, CharSequence message, OnCallbackListener onCallbackListener) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (TextUtils.isEmpty(cancel)) {
            tv_hint_cancel.setText(R.string.dialog_cancel);
        } else {
            tv_hint_cancel.setText(cancel);
        }
        tv_hint_cancel.setVisibility(showCancel ? View.VISIBLE : View.GONE);
        if (TextUtils.isEmpty(confirm)) {
            tv_hint_confirm.setText(R.string.dialog_confirm);
        } else {
            tv_hint_confirm.setText(confirm);
        }
        tv_hint_confirm.setVisibility(View.VISIBLE);
        tv_hint_title.setText(title);
        tv_hint_title.setVisibility(TextUtils.isEmpty(title) ? View.GONE : View.VISIBLE);
        tv_hint_message.setText(message);
        if (TextUtils.isEmpty(title) && message.length() < 10) {
            tv_hint_message.setGravity(Gravity.CENTER);
        } else {
            tv_hint_message.setGravity(Gravity.START);
        }
        setOnCallbackListener(onCallbackListener);
        showDialog();
    }

}
