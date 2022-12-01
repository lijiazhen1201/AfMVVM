package cn.appoa.afhttp.okgo;


import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.net.JsonUtils;


/**
 * 网络访问成功的回调
 *
 * @param <T> 后台返回的data里数据
 * @deprecated Use {@link cn.appoa.afhttp.okgo.OkGoBeansListener} Instead
 */
public abstract class OkGoDatasListener<T> extends OkGoSuccessListener {

    /**
     * data里生成的JavaBean
     */
    private Class<T> clazz;
    /**
     * list的类型：0：data就是list；1：data是array，取里面的list；2：data是obj，取里面的list
     */
    private int listType;

    public OkGoDatasListener(AfView mView, Class<T> clazz) {
        super(mView);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, Class<T> clazz) {
        super(mView, tag);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, Class<T> clazz) {
        super(mView, tag, loadingMessage);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, int messageType, Class<T> clazz) {
        super(mView, tag, loadingMessage, messageType);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, String errorMessage, Class<T> clazz) {
        super(mView, tag, loadingMessage, errorMessage);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, int messageType, String errorMessage, Class<T> clazz) {
        super(mView, tag, messageType, errorMessage);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage, Class<T> clazz) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
        this.clazz = clazz;
    }

    public OkGoDatasListener(AfView mView, Class<T> clazz, int type) {
        super(mView);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, Class<T> clazz, int type) {
        super(mView, tag);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, Class<T> clazz, int type) {
        super(mView, tag, loadingMessage);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, int messageType, Class<T> clazz, int type) {
        super(mView, tag, loadingMessage, messageType);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, String errorMessage, Class<T> clazz, int type) {
        super(mView, tag, loadingMessage, errorMessage);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, int messageType, String errorMessage, Class<T> clazz, int type) {
        super(mView, tag, messageType, errorMessage);
        this.clazz = clazz;
        this.listType = type;
    }

    public OkGoDatasListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage, Class<T> clazz, int type) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
        this.clazz = clazz;
        this.listType = type;
    }

    @Override
    public void onSuccessResponse(String response) {
        if (clazz != null) {
            if (listType == 0) {
                onDatasResponse(JsonUtils.filterJsonParseArray(response, clazz));
            } else if (listType == 1) {
                onDatasResponse(JsonUtils.filterJsonParseArrayList(response, clazz));
            } else if (listType == 2) {
                onDatasResponse(JsonUtils.filterJsonParseObjectList(response, clazz));
            }
        } else {
            onDatasResponse(new ArrayList<T>());
        }
    }

    @Override
    public void onFailedResponse(String response) {
        onDatasResponse(new ArrayList<T>());
    }

    /**
     * 解析的回调
     *
     * @param datas
     */
    public abstract void onDatasResponse(List<T> datas);

}
