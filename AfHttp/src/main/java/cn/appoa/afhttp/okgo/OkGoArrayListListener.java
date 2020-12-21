package cn.appoa.afhttp.okgo;

import org.json.JSONArray;

import cn.appoa.afmvvm.AfView;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 网络访问成功后数据解析成数组
 */
public abstract class OkGoArrayListListener extends OkGoArrayListener {

    public OkGoArrayListListener(AfView mView) {
        super(mView);
    }

    public OkGoArrayListListener(AfView mView, String tag) {
        super(mView, tag);
    }

    public OkGoArrayListListener(AfView mView, String tag, String loadingMessage) {
        super(mView, tag, loadingMessage);
    }

    public OkGoArrayListListener(AfView mView, String tag, String loadingMessage, int messageType) {
        super(mView, tag, loadingMessage, messageType);
    }

    public OkGoArrayListListener(AfView mView, String tag, String loadingMessage, String errorMessage) {
        super(mView, tag, loadingMessage, errorMessage);
    }

    public OkGoArrayListListener(AfView mView, String tag, int messageType, String errorMessage) {
        super(mView, tag, messageType, errorMessage);
    }

    public OkGoArrayListListener(AfView mView, String tag, String loadingMessage, int messageType, String errorMessage) {
        super(mView, tag, loadingMessage, messageType, errorMessage);
    }

    @Override
    public void onSuccessResponse(String response) {
        //super.onSuccessResponse(response);
        JSONArray array = JsonUtils.parseJSONArrayList(response);
        if (array != null) {
            onArrayResponse(array.toString());
        } else {
            onArrayResponse("[]");
        }
    }

}
