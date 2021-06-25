package cn.appoa.afhttp.okgo;

import org.json.JSONObject;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 网络访问成功后数据解析成对象
 */
public abstract class OkGoObjectListener extends OkGoSuccessListener {

    public OkGoObjectListener(AfView mView) {
        super(mView);
    }

    public OkGoObjectListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoObjectListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoObjectListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoObjectListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoObjectListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoObjectListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    @Override
    public void onSuccessResponse(String response) {
        JSONObject object = JsonUtils.parseJSONObject(response);
        if (object != null) {
            onObjectResponse(object.toString());
        } else {
            onObjectResponse("{}");
        }
    }

    /**
     * 解析出对象json
     *
     * @param object
     */
    public abstract void onObjectResponse(String object);
}
