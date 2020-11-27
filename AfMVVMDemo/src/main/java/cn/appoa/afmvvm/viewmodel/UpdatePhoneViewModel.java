package cn.appoa.afmvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.model.UpdatePhoneModel;

public class UpdatePhoneViewModel extends VerifyCodeViewModel<UpdatePhoneModel> {

    public UpdatePhoneViewModel(@NonNull Application application) {
        super(application);
    }

    public UpdatePhoneViewModel(@NonNull Application application, UpdatePhoneModel model) {
        super(application, model);
    }

    @Override
    public int getVerifyCodeType() {
        return 6;
    }

    public SingleLiveEvent<String> updatePhoneEvent = new SingleLiveEvent<>();

    public final BindingCommand updatePhoneClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            getModel().updatePhone(updatePhoneEvent);
        }
    });

    public void updatePhoneSuccess() {
        Intent data = new Intent();
        data.putExtra("phone", getModel().phone.get());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}
