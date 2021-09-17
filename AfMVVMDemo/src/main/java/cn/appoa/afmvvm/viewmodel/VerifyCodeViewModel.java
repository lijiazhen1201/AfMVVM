package cn.appoa.afmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.model.VerifyCodeModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class VerifyCodeViewModel<M extends VerifyCodeModel> extends TitleBarViewModel<M> {

    public VerifyCodeViewModel(@NonNull Application application) {
        super(application);
    }

    public VerifyCodeViewModel(@NonNull Application application, M model) {
        super(application, model);
    }

    public SingleLiveEvent<String> getVerifyCodeEvent = new SingleLiveEvent<String>();

    public final BindingCommand getCodeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getVerifyCode();
        }
    });


    /**
     * 获取验证码类型
     *
     * @return
     */
    public int getVerifyCodeType() {
        return 0;
    }

    /**
     * 获取验证码
     */
    public void getVerifyCode() {
        getVerifyCode(getVerifyCodeType());
    }

    /**
     * 获取验证码
     *
     * @param type 0验证码登录1注册2找回登录密码3三方登录绑定手机号
     *             4找回支付密码5修改手机号（旧）6修改手机号（新）
     */
    public void getVerifyCode(int type) {
        getModel().getVerifyCode(getVerifyCodeType(type), getVerifyCodeEvent);
    }

    /**
     * 获取验证码类型（将自定义类型转为接口需要的类型）
     *
     * @param type 0验证码登录
     *             1注册2找回登录密码3三方登录绑定手机号
     *             4找回支付密码5修改手机号（旧）6修改手机号（新）
     * @return
     */
    private String getVerifyCodeType(int type) {
        String codeType = "";
        switch (type) {
            case 0:
                codeType = "0";
                break;
            case 1:
                codeType = "1";
                break;
            case 2:
                codeType = "2";
                break;
            case 3:
                codeType = "3";
                break;
            case 4:
                codeType = "4";
                break;
            case 5:
                codeType = "5";
                break;
            case 6:
                codeType = "6";
                break;
            default:
                break;
        }
        return codeType;
    }
}
