package cn.appoa.afhttp.okgo;

import org.json.JSONArray;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 网络访问成功后数据解析成数组
 */
public abstract class OkGoArrayListener extends OkGoSuccessListener {

    public OkGoArrayListener(AfView mView) {
        super(mView);
    }

    public OkGoArrayListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoArrayListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoArrayListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoArrayListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoArrayListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoArrayListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    @Override
    public void onSuccessResponse(String response) {
        JSONArray array = JsonUtils.parseJSONArray(response);
        if (array != null) {
            onArrayResponse(array.toString());
        } else {
            onArrayResponse("[]");
        }
    }

    /**
     * 解析出数组json
     *
     * @param array
     */
    public abstract void onArrayResponse(String array);
}
