package cn.appoa.afui.listener;

import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 默认提示框监听
 */
public abstract class DefaultHintDialogListener implements OnCallbackListener {
    @Override
    public void onCallback(int type, Object... obj) {
        if (type == CallbackType.CALLBACK_TYPE_CANCEL) {
            clickCancelButton();
        } else if (type == CallbackType.CALLBACK_TYPE_CONFIRM) {
            clickConfirmButton();
        }
    }

    public abstract void clickConfirmButton();

    public abstract void clickCancelButton();
}
