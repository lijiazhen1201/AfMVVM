package cn.appoa.afbase.mvvm;

import java.util.Map;

public final class UIChangeLiveData<Void> extends SingleLiveEvent<Void> {

    private SingleLiveEvent<String> errorCodeResponse;
    private SingleLiveEvent<CharSequence> showLoadingEvent;
    private SingleLiveEvent<Void> dismissLoadingEvent;
    private SingleLiveEvent<Map<String, Object>> startActivityEvent;
    private SingleLiveEvent<Map<String, Object>> startActivityForResultEvent;
    private SingleLiveEvent<Map<String, Object>> startContainerActivityEvent;
    private SingleLiveEvent<Map<String, Object>> startContainerActivityForResultEvent;
    private SingleLiveEvent<Map<String, Object>> startRouterActivityEvent;
    private SingleLiveEvent<Map<String, Object>> startRouterActivityForResultEvent;
    private SingleLiveEvent<Map<String, Object>> requestPermissionsEvent;
    private SingleLiveEvent<Map<String, Object>> setResultEvent;
    private SingleLiveEvent<Void> finishEvent;
    private SingleLiveEvent<Void> onBackPressedEvent;

    public SingleLiveEvent<String> getErrorCodeResponse() {
        return errorCodeResponse = createLiveData(errorCodeResponse);
    }

    public SingleLiveEvent<CharSequence> getShowLoadingEvent() {
        return showLoadingEvent = createLiveData(showLoadingEvent);
    }

    public SingleLiveEvent<Void> getDismissLoadingEvent() {
        return dismissLoadingEvent = createLiveData(dismissLoadingEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartActivityEvent() {
        return startActivityEvent = createLiveData(startActivityEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartActivityForResultEvent() {
        return startActivityForResultEvent = createLiveData(startActivityForResultEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartContainerActivityEvent() {
        return startContainerActivityEvent = createLiveData(startContainerActivityEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartContainerActivityForResultEvent() {
        return startContainerActivityForResultEvent = createLiveData(startContainerActivityForResultEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartRouterActivityEvent() {
        return startRouterActivityEvent = createLiveData(startRouterActivityEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getStartRouterActivityForResultEvent() {
        return startRouterActivityForResultEvent = createLiveData(startRouterActivityForResultEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getRequestPermissionsEvent() {
        return requestPermissionsEvent = createLiveData(requestPermissionsEvent);
    }

    public SingleLiveEvent<Map<String, Object>> getSetResultEventEvent() {
        return setResultEvent = createLiveData(setResultEvent);
    }

    public SingleLiveEvent<Void> getFinishEvent() {
        return finishEvent = createLiveData(finishEvent);
    }

    public SingleLiveEvent<Void> getOnBackPressedEvent() {
        return onBackPressedEvent = createLiveData(onBackPressedEvent);
    }

    private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
        if (liveData == null) {
            liveData = new SingleLiveEvent<T>();
        }
        return liveData;
    }

}
