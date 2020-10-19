package cn.appoa.afmvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.model.UpdatePwdModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class UpdatePwdViewModel extends TitleBarViewModel<UpdatePwdModel> {

    public UpdatePwdViewModel(@NonNull Application application) {
        super(application);
    }

    public UpdatePwdViewModel(@NonNull Application application, UpdatePwdModel model) {
        super(application, model);
    }

    @Override
    protected void initTitleBar() {
        setDefaultTitleBar(R.drawable.back_black, "修改登录密码");
    }

    public SingleLiveEvent<String> updatePwdEvent = new SingleLiveEvent<>();

    public final BindingCommand updatePwdClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getModel().updatePwd(updatePwdEvent);
        }
    });

    public void updatePwdSuccess() {
        Intent data = new Intent();
        data.putExtra("pwd", getModel().password.get());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}
