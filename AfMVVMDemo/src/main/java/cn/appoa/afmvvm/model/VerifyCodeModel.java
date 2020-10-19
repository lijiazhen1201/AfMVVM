package cn.appoa.afmvvm.model;

import android.text.TextUtils;

import com.lzy.okgo.model.Response;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoSuccessListener;
import cn.appoa.afmvvm.net.API;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afutils.file.ThreadUtils;
import cn.appoa.afutils.res.PhoneUtils;
import cn.appoa.afutils.toast.ToastUtils;

public class VerifyCodeModel extends TitleBarModel {

    public ObservableField<String> phone = new ObservableField<>("");
    public ObservableField<String> phoneHint = new ObservableField<>("请输入手机号");
    public ObservableField<String> phoneConfirm = new ObservableField<>("请输入正确的手机号");
    public ObservableField<String> code = new ObservableField<>("");
    public ObservableField<String> codeHint = new ObservableField<>("请输入验证码");
    public ObservableField<String> getCode = new ObservableField<>("获取验证码");
    public ObservableBoolean getCodeEnabled = new ObservableBoolean(true);

    /**
     * 手机号校验
     *
     * @return
     */
    public boolean checkPhone() {
        if (TextUtils.isEmpty(phone.get())) {
            ToastUtils.showShort(phoneHint.get(), false);
            return false;
        }
        if (!PhoneUtils.isMobile(phone.get())) {
            ToastUtils.showShort(phoneConfirm.get(), false);
            return false;
        }
        return true;
    }

    /**
     * 验证码校验
     *
     * @return
     */
    public boolean checkCode() {
        if (TextUtils.isEmpty(code.get())) {
            ToastUtils.showShort(codeHint.get(), false);
            return false;
        }
        return true;
    }

    /**
     * 发送验证码
     *
     * @param type               0验证码登录1注册2找回登录密码3三方登录绑定手机号
     *                           4找回支付密码5修改手机号（旧）6修改手机号（新）
     * @param getVerifyCodeEvent
     */
    public void getVerifyCode(String type, final SingleLiveEvent<String> getVerifyCodeEvent) {
        if (checkPhone()) {
            if (getView() == null) {
                return;
            }
            Map<String, String> params = API.getParams("phone", phone.get());
            params.put("type", type);
            AfOkGo.request("", params).tag(getView().getRequestTag())
                    .execute(new OkGoSuccessListener(getView(), "发送验证码",
                            "正在发送验证码...", 3
                            //, "发送验证码失败，请稍后再试！"
                    ) {
                        @Override
                        public void onSuccessResponse(String response) {
                            String verifyCode = response;
                            getVerifyCodeEvent.postValue(verifyCode);
                            getVerifyCodeSuccess(verifyCode);
                        }

                        @Override
                        public void onError(Response<String> error) {
                            super.onError(error);
                            // TODO 模拟数据
                            String verifyCode = "000000";
                            getVerifyCodeEvent.postValue(verifyCode);
                            getVerifyCodeSuccess(verifyCode);
                        }
                    });
        }

    }

    /**
     * 发送验证码成功
     *
     * @param verifyCode 验证码
     */
    private void getVerifyCodeSuccess(String verifyCode) {
        countDown();
        // TODO 模拟数据
        // code.set(verifyCode);
    }

    private Timer timer;
    private int time;

    /**
     * 验证码倒计时
     */
    private void countDown() {
        if (TextUtils.equals(getCode.get(), "获取验证码")) {
            time = 60;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ThreadUtils.runInMain(new Runnable() {
                        @Override
                        public void run() {
                            if (time <= 0) {
                                getCodeEnabled.set(true);
                                getCode.set("获取验证码");
                                timer.cancel();
                                timer = null;
                                return;
                            } else {
                                getCodeEnabled.set(false);
                                getCode.set(Integer.toString(time--) + "s后重发");
                            }
                        }
                    });
                }
            }, 1000, 1000);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

}
