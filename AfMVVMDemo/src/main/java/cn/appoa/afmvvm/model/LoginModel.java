package cn.appoa.afmvvm.model;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoDatasListener;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afmvvm.net.API;
import cn.appoa.afutils.toast.ToastUtils;

public class LoginModel extends VerifyCodeModel {

    public ObservableInt loginType = new ObservableInt(0);
    public ObservableField<String> loginTypeText = new ObservableField<>("密码登录");
    public ObservableField<String> password = new ObservableField<>("");
    public ObservableField<String> passwordHint = new ObservableField<>("请输入密码");
    public ObservableField<String> loginConfirm = new ObservableField<>("登录");

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
     * 登录
     *
     * @param loginEvent
     */
    public void login(final SingleLiveEvent<UserInfo> loginEvent) {
        if (checkPhone()) {
            if (loginType.get() == 0) {
                //验证码登录
                loginCode(loginEvent);
            } else {
                //密码登录
                loginPwd(loginEvent);
            }
        }
    }

    /**
     * 验证码登录
     *
     * @param loginEvent
     */
    public void loginCode(final SingleLiveEvent<UserInfo> loginEvent) {
        if (checkCode()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = API.getParams("phone", phone.get());
            params.put("code", code.get());
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoDatasListener<UserInfo>
                            (getView(), "验证码登录", "正在登录...", 2
                                    , "登录失败，请稍后再试！", UserInfo.class) {
                        @Override
                        public void onDatasResponse(List<UserInfo> datas) {
                            if (datas != null && datas.size() > 0) {
                                loginEvent.postValue(datas.get(0));
                            }
                        }
                    });
        }
    }

    /**
     * 密码登录
     *
     * @param loginEvent
     */
    public void loginPwd(final SingleLiveEvent<UserInfo> loginEvent) {
        if (checkPassword()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = API.getParams("phone", phone.get());
            params.put("pwd", password.get());
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoDatasListener<UserInfo>
                            (getView(), "密码登录", "正在登录...", 2
                                    , "登录失败，请稍后再试！", UserInfo.class) {
                        @Override
                        public void onDatasResponse(List<UserInfo> datas) {
                            if (datas != null && datas.size() > 0) {
                                loginEvent.postValue(datas.get(0));
                            }
                        }
                    });
        }
    }

    /**
     * 三方登录
     *
     * @param third_type 1、QQ 2、微信 3、新浪微博
     * @param open_id    授权返回的openid
     * @param name       昵称
     * @param photo      头像
     */
    public void loginThird(final int third_type, final String open_id,
                           final String name, final String photo,
                           final SingleLiveEvent<UserInfo> loginEvent) {
        if (getView() == null) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        AfOkGo.request("", params).tag(getView().getRequestTag())
                .execute(new OkGoDatasListener<UserInfo>
                        (getView(), "三方登录", "正在登录...", 2
                                , "登录失败，请稍后再试！", UserInfo.class) {
                    @Override
                    public void onDatasResponse(List<UserInfo> datas) {
                        if (datas != null && datas.size() > 0) {
                            loginEvent.postValue(datas.get(0));
                        }
                    }
                });
    }
}
