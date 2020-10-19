package cn.appoa.afrefresh.mvvm;

import android.text.TextUtils;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

import cn.appoa.afbase.mvvm.SingleLiveEvent;
import cn.appoa.afhttp.okgo.AfOkGo;
import cn.appoa.afui.titlebar.TitleBarModel;
import cn.appoa.afutils.net.JsonUtils;
import cn.appoa.afutils.net.LogUtils;

public class PullToRefreshModel extends TitleBarModel {

    private static final String TAG = PullToRefreshModel.class.getSimpleName();

    /**
     * 获取列表数据
     *
     * @param url               接口地址
     * @param onSuccessResponse 接口回调
     */
    public void getDataList(String url, final SingleLiveEvent<String> onSuccessResponse) {
        getDataList(url, null, onSuccessResponse);
    }

    /**
     * 获取列表数据
     *
     * @param url               接口地址
     * @param params            接口参数
     * @param onSuccessResponse 接口回调
     */
    public void getDataList(String url, Map<String, String> params,
                            final SingleLiveEvent<String> onSuccessResponse) {
        getDataList(url, params, null, onSuccessResponse);
    }

    /**
     * 获取列表数据
     *
     * @param url               接口地址
     * @param params            接口参数
     * @param headers           接口请求头
     * @param onSuccessResponse 接口回调
     */
    public void getDataList(String url, Map<String, String> params, Map<String, String> headers,
                            final SingleLiveEvent<String> onSuccessResponse) {
        if (getView() == null) {
            return;
        }
        if (url == null) {
            url = "";
        }
        if (params == null) {
            params = new HashMap<>();
        }
        if (headers == null) {
            headers = new HashMap<>();
        }
        AfOkGo.request(url, params, headers)
                .tag(getView().getRequestTag())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> responses) {
                        try {
                            String response = responses.body();
                            LogUtils.i(TAG, response);
                            if (onSuccessResponse != null) {
                                onSuccessResponse.postValue(response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (onSuccessResponse != null) {
                                onSuccessResponse.postValue(JsonUtils.getJsonError("onSuccess Exception"));
                            }
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<String> responses) {
                        onSuccess(responses);
                    }

                    @Override
                    public void onError(Response<String> error) {
                        super.onError(error);
                        try {
                            Throwable throwable = error.getException();
                            String message = error.toString();
                            if (!TextUtils.isEmpty(throwable.getMessage())) {
                                message = throwable.getMessage();
                            }
                            String response = JsonUtils.getJsonError(message);
                            LogUtils.i(TAG, message);
                            if (onSuccessResponse != null) {
                                onSuccessResponse.postValue(response);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (onSuccessResponse != null) {
                                onSuccessResponse.postValue(JsonUtils.getJsonError("onError Exception"));
                            }
                        }
                    }
                });
    }
}
