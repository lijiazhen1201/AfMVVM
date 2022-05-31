package cn.appoa.afbase.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;

import com.wangzhen.statusbar.DarkStatusBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import cn.appoa.afbase.dialog.DefaultLoadingDialog;
import cn.appoa.afbase.event.AfEvent;
import cn.appoa.afbase.mvvm.AfObserver;
import cn.appoa.afbase.mvvm.AfObserverListener;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afbase.mvvm.IBaseView;
import cn.appoa.afbase.mvvm.ParameterField;
import cn.appoa.afbase.slidingback.SlideBackActivity;
import cn.appoa.afhttp.net.NetworkType;
import cn.appoa.afpermission.grant.PermissionsManager;
import cn.appoa.afpermission.grant.PermissionsResultAction;
import cn.appoa.afutils.app.Foreground;
import cn.appoa.afutils.res.ColorUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * Activity基类
 */
public abstract class AfActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends SlideBackActivity implements IBaseView, Foreground.ForegroundListener, AfObserverListener {

    protected Activity mActivity = null;
    protected FragmentManager mFragmentManager = null;
    protected V binding;
    protected VM viewModel;
    protected int viewModelId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 参数设置
        initParams();
        // 初始化
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();
        // 传递数据
        initIntent(getIntent());
        // 初始化布局
        binding = DataBindingUtil.setContentView(mActivity, initContent(savedInstanceState));
        if (binding == null) {
            finish();
        }
        bindSlideFrameLayout(binding.getRoot());
        // 私有的初始化DataBinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        // 私有的ViewModel与View的契约事件回调逻辑
        registerUIChangeLiveDataCallBack();
        // 页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();
        // 页面数据初始化方法
        initData();
        // 注册Bus
        if (viewModel != null) {
            viewModel.registerBus(this);
        }
        // 前台监听
        Foreground.get(this).addForegroundListener(this);
        // 全局变灰色
        setViewGray();
    }

    /**
     * 全局变灰色
     */
    protected void setViewGray() {
        if (ColorUtils.isGrayColor) {
            ColorUtils.setViewGray(getWindow().getDecorView());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.removeBus(this);
        }
        if (binding != null) {
            binding.unbind();
        }
        Foreground.get(this).removeForegroundListener(this);
    }

    /**
     * 刷新布局
     */
    public void refreshLayout() {
        if (binding != null && viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }

    /**
     * 至少需要一个接收者，事件接收者必须是public
     * 否则报异常：its super classes have no public methods with the @Subscribe annotation
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateEvent(AfEvent event) {
        if (event == null) {
            return;
        }
        if (TextUtils.equals(event.className, this.getClass().getCanonicalName())) {
            refreshLayout();
        }
    }

    /**
     * 参数设置
     */
    @Override
    public void initParams() {
        // 去标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 横竖屏设置
        setRequestedOrientation(isScreenOrientationLandscape() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 防止启动页重复初始化 https://blog.csdn.net/love100628/article/details/43238135
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) &&
                    TextUtils.equals(action, Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        // 状态栏颜色
        if (initDarkStatusBar()) {
            DarkStatusBar.get().fitDark(this);
        } else {
            DarkStatusBar.get().fitLight(this);
        }
        // 滑动
        setSlideable(enableSliding());
    }

    /**
     * 是否为横屏
     */
    public boolean isScreenOrientationLandscape() {
        return false;
    }

    /**
     * 是否深色状态栏
     *
     * @return
     */
    public boolean initDarkStatusBar() {
        return false;
    }

    /**
     * 是否允许滑动
     */
    public boolean enableSliding() {
        return false;
    }

    /**
     * 获取Intent传递的数据
     *
     * @param intent
     */
    public void initIntent(Intent intent) {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContent(Bundle savedInstanceState);

    /**
     * 注入绑定
     */
    protected void initViewDataBinding(Bundle savedInstanceState) {
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                // 如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        // 关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        // 支持LiveData绑定xml，数据改变，UI自动会更新
        binding.setLifecycleOwner(this);
        // 让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    /**
     * 注册ViewModel与View的契约UI回调事件
     */
    protected void registerUIChangeLiveDataCallBack() {
        if (viewModel == null) {
            return;
        }
        //后台错误码
        viewModel.getUC().getErrorCodeResponse().observe(this, new Observer<String>() {

            @Override
            public void onChanged(String message) {
                onErrorCodeResponse(message);
            }
        });
        //加载对话框显示
        viewModel.getUC().getShowLoadingEvent().observe(this, new Observer<CharSequence>() {

            @Override
            public void onChanged(CharSequence message) {
                showLoading(message);
            }
        });
        //加载对话框消失
        viewModel.getUC().getDismissLoadingEvent().observe(this, new Observer<Void>() {

            @Override
            public void onChanged(Void v) {
                dismissLoading();
            }
        });
        //跳入新Activity
        viewModel.getUC().getStartActivityEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                Class<?> clazz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startActivity(clazz, bundle);
            }
        });
        //跳入新Activity
        viewModel.getUC().getStartActivityForResultEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                Class<?> clazz = (Class<?>) params.get(ParameterField.CLASS);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                int requestCode = (int) params.get(ParameterField.CODE);
                startActivityForResult(clazz, bundle, requestCode);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityEvent().observe(this, new Observer<Map<String, Object>>() {
            @Override
            public void onChanged(@Nullable Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startContainerActivity(canonicalName, bundle);
            }
        });
        //跳入ContainerActivity
        viewModel.getUC().getStartContainerActivityForResultEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                String canonicalName = (String) params.get(ParameterField.CANONICAL_NAME);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                int requestCode = (int) params.get(ParameterField.CODE);
                startContainerActivityForResult(canonicalName, bundle, requestCode);
            }
        });
        //跳入RouterActivity
        viewModel.getUC().getStartRouterActivityEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                String activityPath = (String) params.get(ParameterField.ACTIVITY_PATH);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                startRouterActivity(activityPath, bundle);
            }
        });
        //跳入RouterActivity
        viewModel.getUC().getStartRouterActivityForResultEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                String activityPath = (String) params.get(ParameterField.ACTIVITY_PATH);
                Bundle bundle = (Bundle) params.get(ParameterField.BUNDLE);
                int requestCode = (int) params.get(ParameterField.CODE);
                startRouterActivityForResult(activityPath, bundle, requestCode);
            }
        });
        //权限申请
        viewModel.getUC().getRequestPermissionsEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                String[] permissions = (String[]) params.get(ParameterField.PERMISSIONS);
                PermissionsResultAction action = (PermissionsResultAction) params.get(ParameterField.PERMISSIONS_ACTION);
                requestPermissions(permissions, action);
            }
        });
        //result回调
        viewModel.getUC().getSetResultEventEvent().observe(this, new Observer<Map<String, Object>>() {

            @Override
            public void onChanged(Map<String, Object> params) {
                int resultCode = (int) params.get(ParameterField.RESULT_CODE);
                Intent data = (Intent) params.get(ParameterField.INTENT);
                setResult(resultCode, data);
                finish();
            }
        });
        //关闭界面
        viewModel.getUC().getFinishEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                finish();
            }
        });
        //关闭上一层
        viewModel.getUC().getOnBackPressedEvent().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void v) {
                onBackPressed();
            }
        });
    }

    /**
     * 跳转页面
     *
     * @param clazz
     */
    public void startActivity(Class<?> clazz) {
        startActivity(clazz, null);
    }

    /**
     * 跳转页面
     *
     * @param clazz
     * @param bundle
     */
    public void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转页面
     *
     * @param clazz
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, int requestCode) {
        startActivityForResult(clazz, null, requestCode);
    }

    /**
     * 跳转页面
     *
     * @param clazz
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName
     * @param bundle
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {

    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName
     * @param requestCode
     */
    public void startContainerActivityForResult(String canonicalName, int requestCode) {
        startContainerActivityForResult(canonicalName, null, requestCode);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName
     * @param bundle
     * @param requestCode
     */
    public void startContainerActivityForResult(String canonicalName, Bundle bundle, int requestCode) {

    }

    /**
     * 跳转路由页面
     *
     * @param activityPath
     */
    public void startRouterActivity(String activityPath) {
        startRouterActivity(activityPath, null);
    }

    /**
     * 跳转路由页面
     *
     * @param activityPath
     * @param bundle
     */
    public void startRouterActivity(String activityPath, Bundle bundle) {

    }

    /**
     * 跳转路由页面
     *
     * @param activityPath
     * @param requestCode
     */
    public void startRouterActivityForResult(String activityPath, int requestCode) {
        startRouterActivityForResult(activityPath, null, requestCode);
    }

    /**
     * 跳转路由页面
     *
     * @param activityPath
     * @param bundle
     * @param requestCode
     */
    public void startRouterActivityForResult(String activityPath, Bundle bundle, int requestCode) {

    }

    /**
     * 获取路由Fragment
     *
     * @param fragmentPath
     * @return
     */
    public Object getRouterFragment(String fragmentPath) {
        return null;
    }

    /**
     * 获取路由Fragment的CanonicalName
     *
     * @param fragmentPath
     * @return
     */
    public String getRouterFragmentCanonicalName(String fragmentPath) {
        return null;
    }

    /**
     * 显示加载框
     *
     * @param message
     */
    public void showLoading(CharSequence message) {
        DefaultLoadingDialog.getInstance().showLoading(this, message);
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoading() {
        DefaultLoadingDialog.getInstance().dismissLoading();
    }

    /**
     * 强制下线
     *
     * @param message
     */
    public void onErrorCodeResponse(String message) {

    }

    /**
     * 返回键（可在布局xml中设置）
     */
    public void back(View view) {
        onBackPressed();
    }

    /**
     * 回到桌面
     */
    public void goBackHome() {
        Intent backHome = new Intent(Intent.ACTION_MAIN);
        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backHome.addCategory(Intent.CATEGORY_HOME);
        startActivity(backHome);
    }

    /**
     * 退出间隔时间
     */
    private long intervalTime = 0;

    /**
     * 再按一次返回键退出
     */
    protected void doubleClickExit(long exitTime, CharSequence exitMessage) {
        if ((System.currentTimeMillis() - intervalTime) > exitTime) {
            ToastUtils.showShort(mActivity, exitMessage, false);
            intervalTime = System.currentTimeMillis();
        } else {
            // AtyManager.getInstance().exitApp();
            finish();
        }
    }

    @TargetApi(23)
    public void requestPermissions(String[] permissions, PermissionsResultAction action) {
        // 请求获取权限
        if (permissions != null && permissions.length > 0) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions, action);
        } else {
            requestAllPermissions(action);
        }
    }

    @TargetApi(23)
    public void requestAllPermissions(PermissionsResultAction action) {
        if (action == null) {
            action = new PermissionsResultAction() {
                @Override
                public void onGranted() {
                    // All permissions have been granted
                }

                @Override
                public void onDenied(String permission) {
                    // "Permission " + permission + " has been denied"
                }
            };
        }
        // 请求获取全部权限，调用权限管理类，然后放进去需要申请的权限，通过接口回调的方法获得权限获取结果
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, action);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // 因为权限管理类无法监听系统，所以需要重写onRequestPermissionResult方法，更新权限管理类，并回调结果。这个是必须要有的
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewModel != null) {
            viewModel.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBecameForeground() {
        // 切换为前台

    }

    @Override
    public void onBecameBackground() {
        // 切换为后台

    }

    @Override
    public void onNetDisconnected() {
        //网络连接断开
//        Snackbar.make(findViewById(R.id.content),
//                R.string.on_net_disconnected, Snackbar.LENGTH_LONG)
//                .setAction(R.string.on_net_setting, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (FastClickUtils.isFastClick()) {
//                            return;
//                        }
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                }).show();
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        //网络连接成功
    }

    /**
     * 获取Observer
     *
     * @param type
     * @return
     */
    protected AfObserver getObserver(int type) {
        return new AfObserver(type, this);
    }

    @Override
    public void onObserverChanged(int type, Object o) {

    }
}
