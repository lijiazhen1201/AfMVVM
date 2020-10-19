package cn.appoa.afmvvm.model;

import java.util.List;

import androidx.databinding.ObservableField;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afhttp.okgo.OkGoDatasListener;
import cn.appoa.afmvvm.bean.AboutUs;
import cn.appoa.afmvvm.net.API;
import cn.appoa.afui.titlebar.TitleBarModel;

public class AboutUsModel extends TitleBarModel {

    public ObservableField<String> mobile = new ObservableField<>("");

    public void getAboutUs(final SingleLiveEvent<AboutUs> getAboutUsEvent) {
        if (getView() == null) {
            return;
        }
        AfOkGo.request(API.getAbout, API.getParams())
                .tag(getView().getRequestTag())
                .execute(new OkGoDatasListener<AboutUs>
                        (getView(), "关于我们", AboutUs.class) {
                    @Override
                    public void onDatasResponse(List<AboutUs> datas) {
                        if (datas != null && datas.size() > 0) {
                            AboutUs data = datas.get(0);
                            mobile.set(data.getMobile());
                            getAboutUsEvent.postValue(data);
                        } else {
                            getAboutUsEvent.postValue(null);
                        }
                    }
                });
    }

}
