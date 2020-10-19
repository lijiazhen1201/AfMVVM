package cn.appoa.afmvvm.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.databinding.ObservableField;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoSuccessListener;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afutils.toast.ToastUtils;

public class UpdatePwdModel extends TitleBarModel {

    public ObservableField<String> oldPassword = new ObservableField<>("");
    public ObservableField<String> oldPasswordHint = new ObservableField<>("请输入旧密码");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> passwordHint = new ObservableField<>("请输入新密码");
    public ObservableField<String> password2 = new ObservableField<>("");
    public ObservableField<String> passwordHint2 = new ObservableField<>("请确认新密码");
    public ObservableField<String> passwordConfirm = new ObservableField<>("两次输入的密码不一致");

    /**
     * 密码校验
     *
     * @return
     */
    public boolean checkPassword() {
        if (TextUtils.isEmpty(oldPassword.get())) {
            ToastUtils.showShort(oldPasswordHint.get(), false);
            return false;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort(passwordHint.get(), false);
            return false;
        }
        if (TextUtils.isEmpty(password2.get())) {
            ToastUtils.showShort(passwordHint2.get(), false);
            return false;
        }
        if (!TextUtils.equals(password.get(), password2.get())) {
            ToastUtils.showShort(passwordConfirm.get(), false);
            return false;
        }
        return true;
    }

    /**
     * 修改登录密码
     *
     * @param updatePwdEvent
     */
    public void updatePwd(final SingleLiveEvent<String> updatePwdEvent) {
        if (checkPassword()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "修改登录密码",
                            "正在修改登录密码...", 3,
                            "修改登录密码失败，请稍后再试！") {
                        @Override
                        public void onSuccessResponse(String response) {
                            updatePwdEvent.postValue(password.get());
                        }
                    });
        }
    }

}
