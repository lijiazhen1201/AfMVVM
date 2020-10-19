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
 * 上传视频弹窗
 */
public class DefaultUploadVideoDialog extends BaseDialog {

    public DefaultUploadVideoDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    public boolean isConfirm;
    public TextView tv_upload_video_camera;
    public TextView tv_upload_video_album;
    public TextView tv_upload_cancel;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_default_upload_video, null);
        tv_upload_video_camera = (TextView) view.findViewById(R.id.tv_upload_video_camera);
        tv_upload_video_album = (TextView) view.findViewById(R.id.tv_upload_video_album);
        tv_upload_cancel = (TextView) view.findViewById(R.id.tv_upload_cancel);
        tv_upload_video_camera.setOnClickListener(this);
        tv_upload_video_album.setOnClickListener(this);
        tv_upload_cancel.setOnClickListener(this);
        return initBottomInputMethodDialog(view, context);
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        if (onCallbackListener != null) {
            int id = v.getId();
            if (id == R.id.tv_upload_video_camera) {
                // 拍摄
                isConfirm = true;
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_UPLOAD_VIDEO_CAMERA);
            }
            if (id == R.id.tv_upload_video_album) {
                // 本地
                isConfirm = true;
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_UPLOAD_VIDEO_ALBUM);
            }
            if (id == R.id.tv_upload_cancel) {
                // 取消
            }
            dismissDialog();
        }
    }
}
