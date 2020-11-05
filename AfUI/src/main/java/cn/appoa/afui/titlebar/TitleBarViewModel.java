package cn.appoa.afui.titlebar;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import cn.appoa.afbase.binding.command.BindingAction;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afutils.res.ScreenUtils;

/**
 * TitleBar封装的ViewModel
 *
 * @param <M>
 */
public class TitleBarViewModel<M extends TitleBarModel> extends BaseViewModel<M> {

    public TitleBarViewModel(@NonNull Application application) {
        super(application);
    }

    public TitleBarViewModel(@NonNull Application application, M model) {
        super(application, model);
    }

    public TitleBarViewModel getTitleBarViewModel() {
        return this;
    }

    public TitleBarModel getTitleBarModel() {
        return (TitleBarModel) getModel();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initTitleBar();
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {

    }

    public final BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            backOnClick();
        }
    });
    public final BindingCommand menuOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            menuOnClick();
        }
    });
    public final BindingCommand menuOnClick2 = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            menuOnClick2();
        }
    });

    /**
     * 返回键点击事件，子类可重写
     */
    protected void backOnClick() {
        finish();
    }

    /**
     * 菜单键点击事件，子类可重写
     */
    protected void menuOnClick() {

    }

    /**
     * 菜单键点击事件2，子类可重写
     */
    protected void menuOnClick2() {
    }

    /**
     * 设置顶部间距（状态栏高度）
     */
    public void setTitleBarPaddingTop() {
        setTitleBarPaddingTop(ScreenUtils.getStatusHeight(getApplication()));
    }

    /**
     * 设置顶部间距（自定义高度）
     *
     * @param paddingTop
     */
    public void setTitleBarPaddingTop(int paddingTop) {
        getTitleBarModel().titleBarPaddingTop.set(paddingTop);
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitleText(String text) {
        getTitleBarModel().titleText.set(text);
        setTitleTextVisible(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置左边文字
     *
     * @param text
     */
    public void setLeftText(String text) {
        getTitleBarModel().leftText.set(text);
        setLeftTextVisible(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置右边文字
     *
     * @param text
     */
    public void setRightText(String text) {
        getTitleBarModel().rightText.set(text);
        setRightTextVisible(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置左边图片
     *
     * @param imageResId
     */
    public void setLeftImage(int imageResId) {
        if (imageResId != 0) {
            getTitleBarModel().leftImage.set(ContextCompat.getDrawable(getApplication(), imageResId));
        }
        setLeftImageVisible(imageResId == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置右边图片
     *
     * @param imageResId
     */
    public void setRightImage(int imageResId) {
        if (imageResId != 0) {
            getTitleBarModel().rightImage.set(ContextCompat.getDrawable(getApplication(), imageResId));
        }
        setRightImageVisible(imageResId == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置右边图片2
     *
     * @param imageResId
     */
    public void setRightImage2(int imageResId) {
        if (imageResId != 0) {
            getTitleBarModel().rightImage2.set(ContextCompat.getDrawable(getApplication(), imageResId));
        }
        setRightImageVisible2(imageResId == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置标题栏显示状态
     *
     * @param visibility
     */
    public void setTitleBarVisible(int visibility) {
        getTitleBarModel().titleBarVisibleObservable.set(visibility);
    }

    /**
     * 设置标题显示状态
     *
     * @param visibility
     */
    public void setTitleTextVisible(int visibility) {
        getTitleBarModel().titleTextVisibleObservable.set(visibility);
    }

    /**
     * 设置左边文字显示状态
     *
     * @param visibility
     */
    public void setLeftTextVisible(int visibility) {
        getTitleBarModel().leftTextVisibleObservable.set(visibility);
    }

    /**
     * 设置右边文字显示状态
     *
     * @param visibility
     */
    public void setRightTextVisible(int visibility) {
        getTitleBarModel().rightTextVisibleObservable.set(visibility);
    }

    /**
     * 设置左边图片显示状态
     *
     * @param visibility
     */
    public void setLeftImageVisible(int visibility) {
        getTitleBarModel().leftImageVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图片显示状态
     *
     * @param visibility
     */
    public void setRightImageVisible(int visibility) {
        getTitleBarModel().rightImageVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图片显示状态2
     *
     * @param visibility
     */
    public void setRightImageVisible2(int visibility) {
        getTitleBarModel().rightImage2VisibleObservable.set(visibility);
    }

    /**
     * 设置分割线显示状态
     *
     * @param visibility
     */
    public void setLineVisible(int visibility) {
        getTitleBarModel().lineVisibleObservable.set(visibility);
    }

    /**
     * 设置默认标题栏（返回键+标题）
     *
     * @param back
     * @param title
     */
    public void setDefaultTitleBar(int back, String title) {
        setDefaultTitleBar(back, title, false);
    }
	
    /**
     * 设置默认标题栏（返回键+标题+分割线）
     *
     * @param back
     * @param title
	 * @param isShowLine
     */
    public void setDefaultTitleBar(int back, String title, boolean isShowLine) {
        setLeftImage(back);
        setTitleText(title);
        setTitleBarPaddingTop();
        setTitleBarVisible(View.VISIBLE);
        setLineVisible(isShowLine ? View.VISIBLE : View.GONE);
    }
}
