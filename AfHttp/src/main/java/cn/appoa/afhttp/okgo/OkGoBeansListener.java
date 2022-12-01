package cn.appoa.afhttp.okgo;


import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.app.GenericsUtils;
import cn.appoa.afutils.net.JsonUtils;


/**
 * 网络访问成功的回调
 *
 * @param <T> 后台返回的data里数据
 */
public abstract class OkGoBeansListener<T> extends OkGoSuccessListener {

    /**
     * list的类型：0：data就是list；1：data是array，取里面的list；2：data是obj，取里面的list
     */
    private int listType;

    public OkGoBeansListener(AfView mView) {
        super(mView);
    }

    public OkGoBeansListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoBeansListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoBeansListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoBeansListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoBeansListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoBeansListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    public OkGoBeansListener(int type, AfView mView) {
        super(mView);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag) {
        super(mView, tag);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
        this.listType = type;
    }

    public OkGoBeansListener(int type, AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
        this.listType = type;
    }

    @Override
    public void onSuccessResponse(String response) {
        Class<T> clazz = GenericsUtils.getSuperClassGenericsType(getClass());
        if (clazz != null) {
            if (listType == 0) {
                onBeansResponse(JsonUtils.filterJsonParseArray(response, clazz));
            } else if (listType == 1) {
                onBeansResponse(JsonUtils.filterJsonParseArrayList(response, clazz));
            } else if (listType == 2) {
                onBeansResponse(JsonUtils.filterJsonParseObjectList(response, clazz));
            }
        } else {
            onBeansResponse(new ArrayList<T>());
        }
    }

    @Override
    public void onFailedResponse(String response) {
        onBeansResponse(new ArrayList<T>());
    }

    /**
     * 解析的回调
     *
     * @param datas
     */
    public abstract void onBeansResponse(List<T> datas);

}
