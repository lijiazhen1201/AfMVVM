package cn.appoa.afmvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afmvvm.model.RegisterModel;

public class RegisterViewModel extends VerifyCodeViewModel<RegisterModel> {

    public int type;
    public ObservableInt third_type = new ObservableInt(0);
    public ObservableField<String> open_id = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> photo = new ObservableField<>("");
    public ObservableField<String> user_id = new ObservableField<>("");

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public RegisterViewModel(@NonNull Application application, RegisterModel model) {
        super(application, model);
    }

    @Override
    public int getVerifyCodeType() {
        return type;
    }

    /**
     * 设置注册类型
     *
     * @param type 1注册2找回密码3绑定手机号
     */
    public void setRegisterType(int type) {
        this.type = type;
        if (this.type == 1) {
            setDefaultTitleBar(R.drawable.back_black, "注册");
            getModel().passwordHint.set("请输入密码");
            getModel().passwordHint2.set("请再次输入密码");
            getModel().password2Visible.set(View.GONE);
            getModel().registerAgreementVisible.set(View.VISIBLE);
            getModel().registerConfirm.set("注册");
        } else if (this.type == 2) {
            setDefaultTitleBar(R.drawable.back_black, "找回密码");
            getModel().passwordHint.set("请输入新密码");
            getModel().passwordHint2.set("请再次输入新密码");
            getModel().password2Visible.set(View.VISIBLE);
            getModel().registerAgreementVisible.set(View.GONE);
            getModel().registerConfirm.set("找回密码");
        } else if (this.type == 3) {
            setDefaultTitleBar(R.drawable.back_black, "绑定手机号");
            getModel().passwordHint.set("请设置密码");
            getModel().passwordHint2.set("请再次设置密码");
            getModel().password2Visible.set(View.GONE);
            getModel().registerAgreementVisible.set(View.GONE);
            getModel().registerConfirm.set("绑定手机号");
        }
    }

    public SingleLiveEvent<UserInfo> registerEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<Void> findPwdEvent = new SingleLiveEvent<>();
    public SingleLiveEvent<UserInfo> bindPhoneEvent = new SingleLiveEvent<>();

    public final BindingCommand registerClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (type) {
                case 1:
                    getModel().register(registerEvent);
                    break;
                case 2:
                    getModel().findPwd(findPwdEvent);
                    break;
                case 3:
                    getModel().bindPhone(third_type.get(), open_id.get(), name.get(),
                            photo.get(), user_id.get(), bindPhoneEvent);
                    break;
            }
        }
    });

    public final BindingCommand registerAgreementClick1 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startAgreementActivity(1);
        }
    });

    public final BindingCommand registerAgreementClick2 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startAgreementActivity(2);
        }
    });

    /**
     * 跳转协议界面
     *
     * @param type 1用户协议2隐私政策
     */
    public void startAgreementActivity(int type) {
        //TODO 跳转协议界面
    }

    /**
     * 注册成功
     *
     * @param type
     * @param user
     */
    public void registerSuccess(int type, UserInfo user) {
        Intent data = new Intent();
        data.putExtra("type", type);
        data.putExtra("phone", getModel().phone.get());
        data.putExtra("pwd", getModel().password.get());
        data.putExtra("user", user == null ? "" : JSON.toJSONString(user));
        setResult(Activity.RESULT_OK, data);
        finish();
    }

}
