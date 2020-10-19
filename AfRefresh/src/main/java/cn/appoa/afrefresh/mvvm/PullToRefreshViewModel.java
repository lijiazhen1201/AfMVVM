package cn.appoa.afrefresh.mvvm;

import android.app.Application;

import java.util.Map;

import androidx.annotation.NonNull;
import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afui.titlebar.TitleBarViewModel;

public class PullToRefreshViewModel<M extends PullToRefreshModel> extends TitleBarViewModel<M> {

    public PullToRefreshViewModel(@NonNull Application application) {
        super(application);
    }

    public PullToRefreshViewModel(@NonNull Application application, M model) {
        super(application, model);
    }

    public SingleLiveEvent<String> onSuccessResponse = new SingleLiveEvent<String>();

    /**
     * 获取列表数据
     *
     * @param url
     */
    public void getDataList(String url) {
        getDataList(url, null);
    }

    /**
     * 获取列表数据
     *
     * @param url
     * @param params
     */
    public void getDataList(String url, Map<String, String> params) {
        getDataList(url, params, null);
    }

    /**
     * 获取列表数据
     *
     * @param url
     * @param params
     * @param headers
     */
    public void getDataList(String url, Map<String, String> params, Map<String, String> headers) {
        if (getModel() == null) {
            return;
        }
        getModel().getDataList(url, params, headers, onSuccessResponse);
    }

}
