package cn.appoa.afmvvm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.appoa.afbase.activity.AfActivity;
import cn.appoa.afbase.dialog.BaseDialog;
import cn.appoa.afmvvm.R;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afpermission.utils.PermissionUtils;
import cn.appoa.afutils.listener.OnCallbackListener;
import cn.appoa.afutils.res.ViewUtils;


public class RequestPermissionsDialog extends BaseDialog {

    public RequestPermissionsDialog(Context context) {
        super(context);
    }

    public RequestPermissionsDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    public RequestPermissionsDialog(Context context, OnCallbackListener onCallbackListener, int layoutId) {
        super(context, onCallbackListener, layoutId);
    }

    private TextView tv_permission_title;
    private TextView tv_permission_msg;
    private TextView tv_permission_tip;
    private TextView tv_permission_open;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_request_permissions, null);
        tv_permission_title = (TextView) view.findViewById(R.id.tv_permission_title);
        tv_permission_msg = (TextView) view.findViewById(R.id.tv_permission_msg);
        tv_permission_tip = (TextView) view.findViewById(R.id.tv_permission_tip);
        tv_permission_open = (TextView) view.findViewById(R.id.tv_permission_open);
        tv_permission_open.setOnClickListener(this);
        dialog = initCenterToastDialog(view, context);
        dialog.setCancelable(false);
        return dialog;
    }

    private AfActivity mActivity;
    private String[] permissions;

    /**
     * 权限申请
     *
     * @param mActivity
     * @param permissions
     */
    public void showRequestPermissionsDialog(AfActivity mActivity, String[] permissions) {
        this.mActivity = mActivity;
        this.permissions = permissions;
        if (mActivity != null && permissions != null && permissions.length > 0) {
            showDialog();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_permission_open) {
            String text = ViewUtils.getText(tv_permission_open);
            if (TextUtils.equals(text, "去开启")) {
                try {
                    PermissionUtils.toPermissionSetting(mActivity);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                tv_permission_tip.setVisibility(View.GONE);
                tv_permission_open.setText("一键开启");
            } else {
                mActivity.requestPermissions(permissions, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        if (onCallbackListener != null) {
                            onCallbackListener.onCallback(-1);
                        }
                        dismissDialog();
                    }

                    @Override
                    public void onDenied(String s) {
                        tv_permission_tip.setVisibility(View.VISIBLE);
                        tv_permission_open.setText("去开启");
                    }
                });
            }
        }
    }

}
