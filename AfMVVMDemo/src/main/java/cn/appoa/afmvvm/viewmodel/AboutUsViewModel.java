package cn.appoa.afmvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afmvvm.bean.AboutUs;
import cn.appoa.afmvvm.model.AboutUsModel;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class AboutUsViewModel extends TitleBarViewModel<AboutUsModel> {

    public AboutUsViewModel(@NonNull Application application) {
        super(application);
    }

    public AboutUsViewModel(@NonNull Application application, AboutUsModel model) {
        super(application, model);
    }

    public SingleLiveEvent<AboutUs> getAboutUsEvent = new SingleLiveEvent<AboutUs>();

    public void getAboutUs() {
        getModel().getAboutUs(getAboutUsEvent);
    }

    public SingleLiveEvent<String> callMobileEvent = new SingleLiveEvent<String>();

    public BindingCommand callMobileClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            callMobileEvent.postValue(getModel().mobile.get());
        }
    });


}
