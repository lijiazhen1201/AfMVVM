package cn.appoa.afhttp.okgo;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.app.GenericsUtils;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 网络访问成功的回调
 *
 * @param <T> 后台返回的data里数据
 */
public abstract class OkGoBeanListener<T> extends OkGoSuccessListener {

    public OkGoBeanListener(AfView mView) {
        super(mView);
    }

    public OkGoBeanListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoBeanListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoBeanListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoBeanListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoBeanListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoBeanListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    @Override
    public void onSuccessResponse(String response) {
        Class<T> clazz = GenericsUtils.getSuperClassGenericsType(getClass());
        if (clazz != null) {
            onBeanResponse(JsonUtils.filterJsonParseObject(response, clazz));
        } else {
            onBeanResponse(null);
        }
    }

    @Override
    public void onFailedResponse(String response) {
        onBeanResponse(null);
    }

    /**
     * 解析的回调
     *
     * @param data
     */
    public abstract void onBeanResponse(T data);
}
