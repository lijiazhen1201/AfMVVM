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
 * 上传图片弹窗
 */
public class DefaultUploadImgDialog extends BaseDialog {

    public DefaultUploadImgDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    public boolean isConfirm;
    public TextView tv_upload_from_camera;
    public TextView tv_upload_from_album;
    public TextView tv_upload_cancel;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_default_upload_img, null);
        view.setClickable(true);
        tv_upload_from_camera = (TextView) view.findViewById(R.id.tv_upload_from_camera);
        tv_upload_from_album = (TextView) view.findViewById(R.id.tv_upload_from_album);
        tv_upload_cancel = (TextView) view.findViewById(R.id.tv_upload_cancel);
        tv_upload_from_camera.setOnClickListener(this);
        tv_upload_from_album.setOnClickListener(this);
        tv_upload_cancel.setOnClickListener(this);
        return initBottomInputMethodDialog(view, context);
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        if (onCallbackListener != null) {
            if (v.getId() == R.id.tv_upload_from_camera) {
                // 拍照
                isConfirm = true;
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_UPLOAD_IMAGE_CAMERA);
            }
            if (v.getId() == R.id.tv_upload_from_album) {
                // 相册
                isConfirm = true;
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_UPLOAD_IMAGE_ALBUM);
            }
            if (v.getId() == R.id.tv_upload_cancel) {
                // 取消
            }
        }
        dismissDialog();
    }

}
