package cn.appoa.afmvvm.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import androidx.databinding.ObservableField;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoSuccessListener;
import cn.appoa.afutils.toast.ToastUtils;

public class UpdatePhoneModel extends VerifyCodeModel {

    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> passwordHint = new ObservableField<>("请输入登录密码");

    /**
     * 密码校验
     *
     * @return
     */
    public boolean checkPassword() {
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort(passwordHint.get(), false);
            return false;
        }
        return true;
    }

    /**
     * 校验全部
     *
     * @return
     */
    public boolean checkAll() {
        if (checkPassword()) {
            if (checkPhone()) {
                if (checkCode()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 修改手机号
     *
     * @param updatePhoneEvent
     */
    public void updatePhone(final SingleLiveEvent<String> updatePhoneEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "修改手机号",
                            "正在修改手机号...", 3,
                            "修改手机号失败，请稍后再试！") {
                        @Override
                        public void onSuccessResponse(String response) {
                            updatePhoneEvent.postValue(phone.get());
                        }
                    });
        }
    }
}
