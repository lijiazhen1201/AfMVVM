package cn.appoa.afui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afui.model.DefaultHintModel;

public class DefaultHintViewModel extends BaseViewModel<DefaultHintModel> {

    public DefaultHintViewModel(@NonNull Application application) {
        super(application);
    }

    public DefaultHintViewModel(@NonNull Application application, DefaultHintModel model) {
        super(application, model);
    }

    public DefaultHintModel getDefaultHintModel() {
        return (DefaultHintModel) getModel();
    }

    public SingleLiveEvent<Integer> onClickEvent = new SingleLiveEvent<Integer>();

    public BindingCommand cancelClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onClickEvent.postValue(CallbackType.CALLBACK_TYPE_CANCEL);
        }
    });

    public BindingCommand confirmClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            onClickEvent.postValue(CallbackType.CALLBACK_TYPE_CONFIRM);
        }
    });

}


