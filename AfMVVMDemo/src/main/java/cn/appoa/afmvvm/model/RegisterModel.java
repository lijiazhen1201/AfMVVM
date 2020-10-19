package cn.appoa.afmvvm.model;

import android.text.TextUtils;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoDatasListener;
import cn.appoa.afhttp.okgo.OkGoSuccessListener;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afutils.toast.ToastUtils;

public class RegisterModel extends VerifyCodeModel {

    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> passwordHint = new ObservableField<>("请输入密码");
    public ObservableField<String> password2 = new ObservableField<>("");
    public ObservableField<String> passwordHint2 = new ObservableField<>("请再次输入密码");
    public ObservableInt password2Visible = new ObservableInt(View.GONE);
    public ObservableField<String> passwordConfirm = new ObservableField<>("两次输入的密码不一致");
    public ObservableField<String> inviteCode = new ObservableField<>("");
    public ObservableInt registerAgreementVisible = new ObservableInt(View.VISIBLE);
    public ObservableBoolean registerAgreement = new ObservableBoolean(false);
    public ObservableField<String> registerAgreementHint = new ObservableField<>("请同意注册协议");
    public ObservableField<String> registerConfirm = new ObservableField<>("注册");

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
     * 用户协议校验
     *
     * @return
     */
    public boolean checkAgreement() {
        if (registerAgreementVisible.get() == View.VISIBLE) {
            if (registerAgreement.get()) {
                return true;
            } else {
                ToastUtils.showShort(registerAgreementHint.get(), false);
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
                    if (checkAgreement()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 注册
     *
     * @param registerEvent
     */
    public void register(final SingleLiveEvent<UserInfo> registerEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoDatasListener<UserInfo>
                            (getView(), "注册", "正在注册...", 3,
                                    "注册失败，请稍后再试！", UserInfo.class) {
                        @Override
                        public void onDatasResponse(List<UserInfo> datas) {
                            if (datas != null && datas.size() > 0) {
                                registerEvent.postValue(datas.get(0));
                            }
                        }
                    });
        }
    }

    /**
     * 找回密码
     *
     * @param findPwdEvent
     */
    public void findPwd(final SingleLiveEvent<Void> findPwdEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "找回密码",
                            "正在找回密码...", 3,
                            "找回密码失败，请稍后再试！") {

                        @Override
                        public void onSuccessResponse(String response) {
                            findPwdEvent.call();
                        }
                    });
        }
    }

    /**
     * 绑定手机号
     *
     * @param third_type 1、QQ 2、微信 3、新浪微博
     * @param open_id    授权返回的openid
     * @param name       昵称
     * @param photo      头像
     * @param user_id    用户id
     */
    public void bindPhone(int third_type, String open_id, String name, String photo, String user_id,
                          final SingleLiveEvent<UserInfo> bindPhoneEvent) {
        if (checkAll()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoDatasListener<UserInfo>
                            (getView(), "绑定手机号", "正在绑定手机号...", 3,
                                    "绑定手机号失败，请稍后再试！", UserInfo.class) {
                        @Override
                        public void onDatasResponse(List<UserInfo> datas) {
                            if (datas != null && datas.size() > 0) {
                                bindPhoneEvent.postValue(datas.get(0));
                            }
                        }
                    });
        }
    }
}
