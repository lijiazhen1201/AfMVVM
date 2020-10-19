package cn.appoa.afbase.mvvm;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.constant.AfConstant;
import cn.appoa.afbase.constant.AfRouterActivityPath;
import cn.appoa.afhttp.AfHttp;
import cn.appoa.afmvvm.AfView;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afutils.file.SpUtils;

public class BaseViewModel<M extends BaseModel> extends AndroidViewModel
        implements IBaseViewModel, AfView {

    private String requestTag;
    private M model;

    public BaseViewModel(@NonNull Application application) {
        this(application, null);
    }

    public BaseViewModel(@NonNull Application application, M model) {
        super(application);
        requestTag = String.valueOf(System.currentTimeMillis());
        this.model = model;
    }

    public M getModel() {
        if (model == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                // 如果没有指定泛型参数，则默认使用BaseModel
                modelClass = BaseModel.class;
            }
            model = (M) createModel(modelClass);
        }
        if (model.getView() == null) {
            model.onAttach(this);
        }
        return model;
    }

    private <M extends BaseModel> M createModel(Class<M> modelClass) {
        try {
            return modelClass.newInstance();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e2) {
            e2.printStackTrace();
        }
        return null;
    }

    private UIChangeLiveData uc;

    public UIChangeLiveData getUC() {
        if (uc == null) {
            uc = new UIChangeLiveData();
        }
        return uc;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return (boolean) SpUtils.getData(AfApplication.getAppContext(), AfConstant.IS_LOGIN, false);
    }

    /**
     * 跳转登录页面
     */
    public void toLoginActivity() {
        startRouterActivityForResult(AfRouterActivityPath.ACTIVITY_LOGIN, AfConstant.REQUEST_CODE_LOGIN);
    }

    public SingleLiveEvent<Intent> toLoginSuccessEvent = new SingleLiveEvent<Intent>();

    /**
     * 登录成功回调
     *
     * @param data
     */
    public void toLoginSuccess(Intent data) {
        toLoginSuccessEvent.postValue(data);
    }

    @Override
    public String getRequestTag() {
        return requestTag;
    }

    @Override
    public void showLoading(CharSequence message) {
        getUC().getShowLoadingEvent().postValue(message);
    }

    @Override
    public void dismissLoading() {
        getUC().getDismissLoadingEvent().call();
    }

    @Override
    public void errorCodeResponse(String message) {
        getUC().getErrorCodeResponse().postValue(message);
    }

    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    public void startActivity(Class<?> clazz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clazz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartActivityEvent().postValue(params);
    }

    public void startActivityForResult(Class<?> clazz, int requestCode) {
        startActivityForResult(clazz, null, requestCode);
    }

    public void startActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clazz);
        params.put(ParameterField.CODE, requestCode);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartActivityForResultEvent().postValue(params);
    }

    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartContainerActivityEvent().postValue(params);
    }

    public void startContainerActivityForResult(String canonicalName, int requestCode) {
        startContainerActivityForResult(canonicalName, null, requestCode);
    }

    public void startContainerActivityForResult(String canonicalName, Bundle bundle, int requestCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        params.put(ParameterField.CODE, requestCode);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartContainerActivityForResultEvent().postValue(params);
    }

    public void startRouterActivity(String activityPath) {
        startRouterActivity(activityPath, null);
    }

    public void startRouterActivity(String activityPath, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.ACTIVITY_PATH, activityPath);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartRouterActivityEvent().postValue(params);
    }

    public void startRouterActivityForResult(String activityPath, int requestCode) {
        startRouterActivityForResult(activityPath, null, requestCode);
    }

    public void startRouterActivityForResult(String activityPath, Bundle bundle, int requestCode) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.ACTIVITY_PATH, activityPath);
        params.put(ParameterField.CODE, requestCode);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        getUC().getStartRouterActivityForResultEvent().postValue(params);
    }

    public void requestPermissions(List<String> permissions, PermissionsResultAction action) {
        if (permissions != null) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), action);
        } else {
            requestPermissions(new String[0], action);
        }
    }

    public void requestPermissions(String[] permissions, PermissionsResultAction action) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.PERMISSIONS, permissions);
        params.put(ParameterField.PERMISSIONS_ACTION, action);
        getUC().getRequestPermissionsEvent().postValue(params);
    }

    public void setResult(int resultCode) {
        setResult(resultCode, null);
    }

    public void setResult(int resultCode, Intent data) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.RESULT_CODE, resultCode);
        params.put(ParameterField.INTENT, data);
        getUC().getSetResultEventEvent().postValue(params);
    }

    public void finish() {
        getUC().getFinishEvent().call();
    }

    public void onBackPressed() {
        getUC().getOnBackPressedEvent().call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void registerBus(Object o) {
        // 注册事件总线
        EventBus.getDefault().register(o);
    }

    @Override
    public void removeBus(Object o) {
        //解绑事件总线
        if (EventBus.getDefault().isRegistered(o)) {
            EventBus.getDefault().unregister(o);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //requestCode回调
        if (requestCode == AfConstant.REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_OK) {
            //登录成功
            toLoginSuccess(data);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //ViewModel销毁时，取消所有网络访问
        AfHttp.getInstance().cancelTag(getRequestTag());
        if (model != null) {
            //ViewModel销毁时，清除Model，与ViewModel共消亡。Model层同样不能持有长生命周期对象
            model.onDetach();
        }
    }

}
