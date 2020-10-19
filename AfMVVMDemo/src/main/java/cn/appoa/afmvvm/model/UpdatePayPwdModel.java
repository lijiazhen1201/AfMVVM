package cn.appoa.afmvvm.model;

import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoSuccessListener;
import cn.appoa.afutils.toast.ToastUtils;

public class UpdatePayPwdModel extends VerifyCodeModel {

    public ObservableInt phoneCodeVisible = new ObservableInt(View.GONE);
    public ObservableField<String> oldPassword = new ObservableField<>("");
    public ObservableField<String> oldPasswordHint = new ObservableField<>("请输入原支付密码");
    public ObservableInt oldPasswordVisible = new ObservableInt(View.GONE);
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> passwordHint = new ObservableField<>("请输入支付密码");
    public ObservableField<String> password2 = new ObservableField<>("");
    public ObservableField<String> passwordHint2 = new ObservableField<>("请再次输入支付密码");
    public ObservableInt password2Visible = new ObservableInt(View.VISIBLE);
    public ObservableField<String> passwordConfirm = new ObservableField<>("两次输入的密码不一致");

    @Override
    public boolean checkPhone() {
        if (phoneCodeVisible.get() == View.VISIBLE) {
            return super.checkPhone();
        }
        return true;
    }

    @Override
    public boolean checkCode() {
        if (phoneCodeVisible.get() == View.VISIBLE) {
            return super.checkCode();
        }
        return true;
    }

    /**
     * 密码校验
     *
     * @return
     */
    public boolean checkPassword() {
        if (oldPasswordVisible.get() == View.VISIBLE) {
            if (TextUtils.isEmpty(oldPassword.get())) {
                ToastUtils.showShort(oldPasswordHint.get(), false);
                return false;
            }
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort(passwordHint.get(), false);
            return false;
        }
        if (password2Visible.get() == View.VISIBLE) {
            if (TextUtils.isEmpty(password2.get())) {
                ToastUtils.showShort(passwordHint2.get(), false);
                return false;
            }
            if (!TextUtils.equals(password.get(), password2.get())) {
                ToastUtils.showShort(passwordConfirm.get(), false);
                return false;
            }
        }
        return true;
    }

    /**
     * 校验全部
     *
     * @return
     */
    public boolean checkAll() {
        if (checkPhone()) {
            if (checkCode()) {
                if (checkPassword()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 设置支付密码
     *
     * @param updatePayPwdEvent
     */
    public void setPayPwd(final SingleLiveEvent<String> updatePayPwdEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "设置支付密码",
                            "正在设置支付密码...", 3,
                            "设置支付密码失败，请稍后再试！") {
                        @Override
                        public void onSuccessResponse(String response) {
                            updatePayPwdEvent.postValue(password.get());
                        }
                    });
        }
    }

    /**
     * 修改支付密码
     *
     * @param updatePayPwdEvent
     */
    public void updatePayPwd(final SingleLiveEvent<String> updatePayPwdEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "修改支付密码",
                            "正在修改支付密码...", 3,
                            "修改支付密码失败，请稍后再试！") {
                        @Override
                        public void onSuccessResponse(String response) {
                            updatePayPwdEvent.postValue(password.get());
                        }
                    });
        }
    }

    /**
     * 找回支付密码
     *
     * @param updatePayPwdEvent
     */
    public void findPayPwd(final SingleLiveEvent<String> updatePayPwdEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "找回支付密码",
                            "正在找回支付密码...", 3,
                            "找回支付密码失败，请稍后再试！") {
                        @Override
                        public void onSuccessResponse(String response) {
                            updatePayPwdEvent.postValue(password.get());
                        }
                    });
        }
    }
}
