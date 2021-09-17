package cn.appoa.afmvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.constant.AfConstant;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.activity.ui.RegisterActivity;
import cn.appoa.afmvvm.bean.UserInfo;
import cn.appoa.afmvvm.event.LoginEvent;
import cn.appoa.afmvvm.model.LoginModel;
import cn.appoa.afmvvm.share.ShareSdkUtils;
import cn.appoa.afutils.file.SpUtils;
import cn.appoa.afutils.file.ThreadUtils;
import cn.appoa.afutils.toast.ToastUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginViewModel extends VerifyCodeViewModel<LoginModel>
        implements PlatformActionListener {

    public ObservableBoolean loginChat = new ObservableBoolean(false);
    public ObservableInt third_type = new ObservableInt(0);
    public ObservableField<String> open_id = new ObservableField<>("");
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> photo = new ObservableField<>("");
    public ObservableField<String> user_id = new ObservableField<>("");

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LoginViewModel(@NonNull Application application, LoginModel model) {
        super(application, model);
    }

    public final BindingCommand loginTypeClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (getModel().loginType.get() == 0) {
                getModel().loginType.set(1);
                getModel().loginTypeText.set("验证码登录");
            } else {
                getModel().loginType.set(0);
                getModel().loginTypeText.set("密码登录");
            }
        }
    });

    public SingleLiveEvent<UserInfo> loginEvent = new SingleLiveEvent<>();

    public final BindingCommand loginClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            third_type.set(0);
            getModel().login(loginEvent);
        }
    });

    public final BindingCommand loginQqClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ShareSdkUtils.thirdLogin(1, LoginViewModel.this);
        }
    });

    public final BindingCommand loginWxClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ShareSdkUtils.thirdLogin(3, LoginViewModel.this);
        }
    });

    public final BindingCommand registerClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivityForRegister(1);
        }
    });

    public final BindingCommand findPwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            startActivityForRegister(2);
        }
    });

    /**
     * 注册/找回密码/绑定手机号
     *
     * @param type
     */
    public void startActivityForRegister(int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("third_type", third_type.get());
        bundle.putString("open_id", open_id.get());
        bundle.putString("name", name.get());
        bundle.putString("photo", photo.get());
        bundle.putString("user_id", user_id.get());
        startActivityForResult(RegisterActivity.class, bundle, AfConstant.REQUEST_CODE_REGISTER);
    }

    @Override
    public void onCancel(Platform platform, int action) {
        if (action == Platform.ACTION_USER_INFOR) {
            ThreadUtils.runInMain(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    ToastUtils.showShort("取消授权", false);
                }
            });
        }
    }

    @Override
    public void onError(final Platform platform, int action, Throwable t) {
        if (action == Platform.ACTION_USER_INFOR) {
            ThreadUtils.runInMain(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    if (platform != null && platform.getDb() != null
                            && !TextUtils.isEmpty(platform.getDb().getUserId())) {
                        thirdLogin(platform);
                    } else {
                        ToastUtils.showShort("授权失败", false);
                    }
                }
            });
        }
    }

    @Override
    public void onComplete(final Platform platform, int action, HashMap<String, Object> res) {
        if (action == Platform.ACTION_USER_INFOR) {
            ThreadUtils.runInMain(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    ToastUtils.showShort("授权成功，正在登录...", false);
                    thirdLogin(platform);
                }
            });
        }
    }

    private void thirdLogin(Platform platform) {
        if (platform != null) {
            PlatformDb platDB = platform.getDb();
            String userId = platDB.getUserId();
            String nickName = platDB.getUserName();
            String userPic = platDB.getUserIcon();
            if (TextUtils.equals(QQ.NAME, platform.getName())) {
                // qq登录
                third_type.set(1);
                SpUtils.putData(AfApplication.getAppContext(), AfConstant.IS_LOGIN_QQ, true);
            } else if (TextUtils.equals(Wechat.NAME, platform.getName())) {
                // 微信登录
                third_type.set(2);
                SpUtils.putData(AfApplication.getAppContext(), AfConstant.IS_LOGIN_WX, true);
            } else if (TextUtils.equals(SinaWeibo.NAME, platform.getName())) {
                //微博登录
                third_type.set(3);
                SpUtils.putData(AfApplication.getAppContext(), AfConstant.IS_LOGIN_SINA, true);
            }
            open_id.set(userId);
            name.set(nickName);
            photo.set(userPic);
            getModel().loginThird(third_type.get(), open_id.get(), name.get(), photo.get(), loginEvent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AfConstant.REQUEST_CODE_REGISTER &&
                resultCode == Activity.RESULT_OK && data != null) {
            //注册成功，找回密码成功，绑定手机号成功
            String user = data.getStringExtra("user");
            if (user != null) {
                loginSuccess(JSON.parseObject(user, UserInfo.class));
            } else {
                getModel().phone.set(data.getStringExtra("phone"));
                getModel().password.set(data.getStringExtra("pwd"));
                getModel().loginPwd(loginEvent);
            }
        }
    }

    public void loginSuccess(UserInfo user) {
        if (user == null) {
            return;
        }
        if (loginChat.get()) {
            loginChat(user);
        } else {
            loginChatSuccess(user);
        }
    }

    private void loginChat(final UserInfo user) {
        //TODO 登录聊天
//        EaseChatUtils.loginChat(mActivity, user.hxusername, user.hxpassword, new ChatLoginListener() {
//            @Override
//            public void loginResult(int code, String result) {
//                if (code == -1) {
//                    SpUtils.putData(mActivity, AfConstant.IS_LOGIN_CHAT, true);
//                    mActivity.startService(new Intent(mActivity, MyChatService.class));
//                    loginChatSuccess(user);
//                } else if (code == EMError.USER_ALREADY_LOGIN) {
//                    //用户已登录
//                    EaseChatUtils.logoutChat(mActivity, true, new ChatLogoutListener() {
//                        @Override
//                        public void logoutResult(int code, String result) {
//                            loginChat(mActivity, user);
//                        }
//                    });
//                } else if (code == EMError.USER_NOT_FOUND) {
//                    //用户不存在
//                    EaseChatUtils.registerChat(mActivity, user.hxusername, user.hxpassword,
//                            new ChatRegisterListener() {
//                                @Override
//                                public void registerResult(int code, String result) {
//                                    loginChat(mActivity, user);
//                                }
//                            });
//                } else {
//                    ToastUtils.showShort(result, false);
//                }
//            }
//        });
    }

    private void loginChatSuccess(UserInfo user) {
        if (user != null) {
            user.saveUserInfo();
            ToastUtils.showShort("登录成功", false);
            SpUtils.putData(AfApplication.getAppContext(), AfConstant.IS_LOGIN, true);
            EventBus.getDefault().post(new LoginEvent(1));
            Intent data = new Intent();
            data.putExtra("user", JSON.toJSONString(user));
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

}
