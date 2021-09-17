package cn.appoa.afmvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.model.UpdatePayPwdModel;

public class UpdatePayPwdViewModel extends VerifyCodeViewModel<UpdatePayPwdModel> {

    public int type;

    public UpdatePayPwdViewModel(@NonNull Application application) {
        super(application);
    }

    public UpdatePayPwdViewModel(@NonNull Application application, UpdatePayPwdModel model) {
        super(application, model);
    }

    @Override
    public int getVerifyCodeType() {
        return 4;
    }

    /**
     * 设置修改类型
     *
     * @param type 1设置支付密码2修改支付密码3找回支付密码
     */
    public void setUpdatePayPwdType(int type) {
        this.type = type;
        if (this.type == 1) {
            setDefaultTitleBar(R.drawable.back_black, "设置支付密码");
            getModel().phoneCodeVisible.set(View.GONE);
            getModel().oldPasswordVisible.set(View.GONE);
            getModel().password2Visible.set(View.VISIBLE);
            getModel().passwordHint.set("请输入支付密码");
            getModel().passwordHint2.set("请再次输入支付密码");
        } else if (this.type == 2) {
            setDefaultTitleBar(R.drawable.back_black, "修改支付密码");
            getModel().phoneCodeVisible.set(View.GONE);
            getModel().oldPasswordVisible.set(View.VISIBLE);
            getModel().password2Visible.set(View.VISIBLE);
            getModel().passwordHint.set("请输入新支付密码");
            getModel().passwordHint2.set("请再次输入新支付密码");
        } else if (this.type == 3) {
            setDefaultTitleBar(R.drawable.back_black, "找回支付密码");
            getModel().phoneCodeVisible.set(View.VISIBLE);
            getModel().oldPasswordVisible.set(View.GONE);
            getModel().password2Visible.set(View.VISIBLE);
            getModel().passwordHint.set("请输入新支付密码");
            getModel().passwordHint2.set("请再次输入新支付密码");
        }
    }

    public SingleLiveEvent<String> updatePayPwdEvent = new SingleLiveEvent<>();

    public final BindingCommand updatePayPwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            switch (type) {
                case 1:
                    getModel().setPayPwd(updatePayPwdEvent);
                    break;
                case 2:
                    getModel().updatePayPwd(updatePayPwdEvent);
                    break;
                case 3:
                    getModel().findPayPwd(updatePayPwdEvent);
                    break;
                default:
                    break;
            }
        }
    });

    public void updatePayPwdSuccess() {
        Intent data = new Intent();
        data.putExtra("pwd", getModel().password.get());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}
