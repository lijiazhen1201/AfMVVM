package cn.appoa.afui.widget.index;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.appoa.afui.R;

/**
 * 带侧边栏的主页控件
 */
public class DrawerView extends FrameLayout implements DrawerLayout.DrawerListener {

    public DrawerView(Context context) {
        this(context, null);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @IntDef(value = {GravityCompat.START, GravityCompat.END},
            flag = true)
    @Retention(RetentionPolicy.SOURCE)
    private @interface DrawerGravity {
    }

    /**
     * 侧边栏方向（默认左边）
     */
    private @DrawerGravity
    final int edgeGravity = GravityCompat.START;

    /**
     * 侧边栏
     */
    private DrawerLayout drawerLayout;

    /**
     * Fragment控制器
     */
    private FragmentManager mFragmentManager;

    /**
     * 侧边Fragment
     */
    private IndexItem drawerItem;

    /**
     * 内容Fragment
     */
    private IndexItem contentItem;

    /**
     * 初始化控件
     *
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.layout_drawer_view, this);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawerView);
            array.recycle();
        }
    }

    /**
     * 初始化FragmentManager
     *
     * @param manager
     * @return
     */
    public DrawerView initManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    /**
     * 添加IndexItem
     *
     * @param label        文字
     * @param icon         图标
     * @param fragmentName Fragment名称
     * @return
     */
    public IndexItem addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName) {
        return new IndexItem(label, icon, fragmentName);
    }

    /**
     * 添加IndexItem
     *
     * @param label        文字
     * @param icon         图标
     * @param fragmentName Fragment名称
     * @param bundle       传递数据
     * @return
     */
    public IndexItem addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName, Bundle bundle) {
        return new IndexItem(label, icon, fragmentName, bundle);
    }

    /**
     * 初始化Fragment
     *
     * @param drawer  侧边栏
     * @param content 内容
     * @return
     */
    public DrawerView initFragment(IndexItem drawer, IndexItem content) {
        if (mFragmentManager != null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            if (drawerItem == null) {
                drawerItem = drawer;
            }
            transaction.replace(R.id.fl_fragment_drawer, getIndexItemFragment(drawerItem));
            if (contentItem == null) {
                contentItem = content;
            }
            transaction.replace(R.id.fl_fragment_content, getIndexItemFragment(contentItem));
            transaction.commitAllowingStateLoss();
        }
        return this;
    }

    /**
     * 获取Fragment
     *
     * @param indexItem
     * @return
     */
    private Fragment getIndexItemFragment(IndexItem indexItem) {
        if (indexItem.fragment == null) {
            try {
                Class<?> fragmentClass = Class.forName(indexItem.fragmentName);
                indexItem.fragment = (Fragment) fragmentClass.newInstance();
                if (indexItem.bundle != null) {
                    indexItem.fragment.setArguments(indexItem.bundle);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
        return indexItem.fragment;
    }

    /**
     * 创建完毕
     *
     * @return
     */
    public DrawerView create() {
        return setDrawerOpen(false);
    }

    /**
     * 保存Fragment的状态（重写Activity的onSaveInstanceState中并调用）
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        if (mFragmentManager == null) {
            return;
        }
        if (drawerItem != null) {
            mFragmentManager.putFragment(outState, drawerItem.fragmentName, drawerItem.fragment);
        }
        if (contentItem != null) {
            mFragmentManager.putFragment(outState, contentItem.fragmentName, contentItem.fragment);
        }

    }

    /**
     * 获取保存的Fragment状态（重写Activity的onCreate中并在加载布局之前调用）
     *
     * @param savedInstanceState
     */
    public void onGetInstanceState(Bundle savedInstanceState) {
        if (mFragmentManager == null || savedInstanceState == null) {
            return;
        }
        if (drawerItem != null) {
            drawerItem.fragment = mFragmentManager.getFragment(savedInstanceState, drawerItem.fragmentName);
        }
        if (contentItem != null) {
            contentItem.fragment = mFragmentManager.getFragment(savedInstanceState, contentItem.fragmentName);
        }
    }

    /**
     * 设置手势滑动锁定
     *
     * @param locked 是否锁定（锁定后手势失效）
     */
    public DrawerView setDrawerLock(boolean locked) {
        if (drawerLayout != null) {
            drawerLayout.setDrawerLockMode(locked ? DrawerLayout.LOCK_MODE_LOCKED_CLOSED : DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        return this;
    }

    /**
     * 手势滑动锁定
     *
     * @return
     */
    public boolean getDrawerLock() {
        if (drawerLayout != null) {
            int lockMode = drawerLayout.getDrawerLockMode(edgeGravity);
            return lockMode == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        }
        return false;
    }

    /**
     * 设置开启/关闭侧边栏
     *
     * @param opened 是否开启
     */
    public DrawerView setDrawerOpen(boolean opened) {
        if (drawerLayout != null) {
            if (opened) {
                drawerLayout.openDrawer(edgeGravity);
            } else {
                drawerLayout.closeDrawer(edgeGravity);
            }
        }
        return this;
    }

    /**
     * 是否开启侧边栏
     *
     * @return
     */
    public boolean getDrawerOpen() {
        if (drawerLayout != null) {
            return drawerLayout.isDrawerOpen(edgeGravity);
        }
        return false;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        // 滑动中
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        // 打开
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        // 关闭
    }

    @Override
    public void onDrawerStateChanged(int newState) {
        // 状态改变
        if (mOnDrawerChangeListener != null) {
            mOnDrawerChangeListener.onStateChanged(newState);
        }
    }

    /**
     * 设置DrawerLayout的DrawerListener
     */
    public void setDrawerLayoutListener() {
        setDrawerLayoutListener(this);
    }

    /**
     * 设置DrawerLayout的DrawerListener
     *
     * @param listener
     */
    public void setDrawerLayoutListener(DrawerLayout.DrawerListener listener) {
        if (drawerLayout != null) {
            drawerLayout.addDrawerListener(listener);
        }
    }

    /**
     * 状态改变的监听
     */
    private OnDrawerChangeListener mOnDrawerChangeListener;

    /**
     * 设置状态改变的监听
     *
     * @param listener
     */
    public void setOnDrawerChangeListener(OnDrawerChangeListener listener) {
        mOnDrawerChangeListener = listener;
    }

    /**
     * 状态改变的监听
     */
    public interface OnDrawerChangeListener {
        /**
         * 状态改变
         *
         * @param newState
         */
        void onStateChanged(int newState);
    }
}
