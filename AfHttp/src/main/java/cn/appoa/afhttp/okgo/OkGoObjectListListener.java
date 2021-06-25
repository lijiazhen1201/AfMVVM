package cn.appoa.afhttp.okgo;

import org.json.JSONArray;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 网络访问成功后数据解析成数组
 */
public abstract class OkGoObjectListListener extends OkGoArrayListListener {

    public OkGoObjectListListener(AfView mView) {
        super(mView);
    }

    public OkGoObjectListListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoObjectListListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoObjectListListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoObjectListListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoObjectListListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoObjectListListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    @Override
    public void onSuccessResponse(String response) {
        //super.onSuccessResponse(response);
        JSONArray array = JsonUtils.parseJSONObjectList(response);
        if (array != null) {
            onArrayResponse(array.toString());
        } else {
            onArrayResponse("[]");
        }
    }
}
