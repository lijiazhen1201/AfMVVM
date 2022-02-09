package cn.appoa.afui.widget.index;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.appoa.afbase.fragment.AfFragment;
import cn.appoa.afui.R;

/**
 * 带底部导航的主页控件
 */
public class DrawerIndexView extends FrameLayout implements CompoundButton.OnCheckedChangeListener,
        DrawerLayout.DrawerListener {

    public DrawerIndexView(Context context) {
        this(context, null);
    }

    public DrawerIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawerIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @IntDef(value = {GravityCompat.START, GravityCompat.END},
            flag = true)
    @Retention(RetentionPolicy.SOURCE)
    private @interface DrawerIndexGravity {
    }

    /**
     * 侧边栏方向（默认左边）
     */
    private @DrawerIndexGravity
    final int edgeGravity = GravityCompat.START;

    /**
     * 侧边栏是否初始化
     */
    private boolean initDrawer;
    /**
     * 侧边Fragment
     */
    private IndexItem drawerItem;

    /**
     * 侧边栏
     */
    private DrawerLayout drawerLayout;

    /**
     * 根布局
     */
    private RelativeLayout indexLayout;
    /**
     * 放底部Tab的容器
     */
    private RadioGroup rgIndexTab;
    /**
     * 底部分割线
     */
    private View indexDividerBottom;
    /**
     * 放Fragment的布局
     */
    private FrameLayout flIndexFragment;
    /**
     * Fragment控制器
     */
    private FragmentManager mFragmentManager;
    /**
     * 初始显示项的下标
     */
    private int startIndex;
    /**
     * 带底部导航的主页Item集合
     */
    private List<IndexItem> pageItemList;
    /**
     * 当前下标
     */
    private int currentIndex = 0;
    /**
     * 页面切换的监听
     */
    private OnDrawerIndexChangeListener mOnDrawerIndexChangeListener;

    /**
     * 获取当前下标
     *
     * @return 当前下标
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * 初始化控件
     *
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.layout_drawer_index_view, this);
        drawerLayout = view.findViewById(R.id.drawer_layout);
        indexLayout = view.findViewById(R.id.index_layout);
        rgIndexTab = view.findViewById(R.id.rg_index_tab);
        indexDividerBottom = view.findViewById(R.id.index_divider_bottom);
        flIndexFragment = view.findViewById(R.id.fl_index_fragment);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DrawerIndexView);
            array.recycle();
        }
    }

    /**
     * 初始化FragmentManager
     *
     * @param manager
     * @return
     */
    public DrawerIndexView initManager(FragmentManager manager) {
        mFragmentManager = manager;
        return this;
    }

    /**
     * 添加侧边栏
     *
     * @param label        文字
     * @param fragmentName Fragment名称
     * @return
     */
    public DrawerIndexView addDrawerItem(String label, @NonNull String fragmentName) {
        return addDrawerItem(new IndexItem(label, 0, fragmentName));
    }

    /**
     * 添加侧边栏
     *
     * @param label        文字
     * @param fragmentName Fragment名称
     * @return
     */
    public DrawerIndexView addDrawerItem(String label, @NonNull String fragmentName, Bundle bundle) {
        return addDrawerItem(new IndexItem(label, 0, fragmentName, bundle));
    }

    /**
     * 添加侧边栏
     *
     * @param label        文字
     * @param icon         图标
     * @param fragmentName Fragment名称
     * @return
     */
    public DrawerIndexView addDrawerItem(String label, @DrawableRes int icon, @NonNull String fragmentName) {
        return addDrawerItem(new IndexItem(label, icon, fragmentName));
    }

    /**
     * 添加侧边栏
     *
     * @param label        文字
     * @param icon         图标
     * @param fragmentName Fragment名称
     * @param bundle       传递数据
     * @return
     */
    public DrawerIndexView addDrawerItem(String label, @DrawableRes int icon, @NonNull String fragmentName, Bundle bundle) {
        return addDrawerItem(new IndexItem(label, icon, fragmentName, bundle));
    }

    /**
     * 添加侧边栏
     *
     * @param drawer 侧边item
     * @return
     */
    public DrawerIndexView addDrawerItem(IndexItem drawer) {
        if (drawerItem == null) {
            drawerItem = drawer;
        }
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
    public DrawerIndexView addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName) {
        return addIndexItem(new IndexItem(label, icon, fragmentName));
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
    public DrawerIndexView addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName, Bundle bundle) {
        return addIndexItem(new IndexItem(label, icon, fragmentName, bundle));
    }

    /**
     * 添加IndexItem
     *
     * @param pageItem IndexItem
     * @return
     */
    public DrawerIndexView addIndexItem(IndexItem pageItem) {
        if (pageItemList == null) {
            pageItemList = new ArrayList<>();
        }
        RadioButton button = (RadioButton) View.inflate(getContext(), R.layout.layout_index_tab, null);
        button.setTag(pageItemList.size());
        button.setText(pageItem.label);
        button.setCompoundDrawablesWithIntrinsicBounds(0, pageItem.icon, 0, 0);
        button.setOnCheckedChangeListener(this);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, -2, 1);
        rgIndexTab.addView(button, params);
        pageItemList.add(pageItem);
        return this;
    }

    /**
     * 首次切换下标
     *
     * @return
     */
    public DrawerIndexView startIndex() {
        return startIndex(0);
    }

    /**
     * 首次切换下标
     *
     * @param index
     * @return
     */
    public DrawerIndexView startIndex(int index) {
        startIndex = index;
        rgIndexTab.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCheckedIndex(startIndex);
            }
        }, 300);
        return setDrawerOpen(false);
    }

    /**
     * 创建主页界面
     *
     * @param manager
     * @param list    带底部导航的主页Item集合
     */
    public void createIndex(FragmentManager manager, List<IndexItem> list) {
        createIndex(manager, list, 0);
    }

    /**
     * 创建主页界面
     *
     * @param manager
     * @param index   初始显示项的下标
     * @param list    带底部导航的主页Item集合
     */
    public void createIndex(FragmentManager manager, List<IndexItem> list, int index) {
        initManager(manager);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                IndexItem pageItem = list.get(i);
                addIndexItem(pageItem);
            }
            startIndex(index);
        }
    }

    /**
     * 切换界面
     *
     * @param index 要切换的下标
     */
    public void setCheckedIndex(int index) {
        if (index < rgIndexTab.getChildCount()) {
            RadioButton button = (RadioButton) rgIndexTab.getChildAt(index);
            button.setChecked(true);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            setTabSelection((int) buttonView.getTag());
        }
    }

    /**
     * 界面切换
     *
     * @param index 要切换的下标
     */
    private void setTabSelection(int index) {
        currentIndex = index;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 侧边栏
        if (!initDrawer && drawerItem != null) {
            transaction.replace(R.id.fl_fragment_drawer, getIndexItemFragment(drawerItem));
            initDrawer = true;
        }
        // 隐藏全部Fragment
        for (int i = 0; i < pageItemList.size(); i++) {
            IndexItem pageItem = pageItemList.get(i);
            if (pageItem.fragment != null) {
                transaction.hide(pageItem.fragment);
            }
        }
        // 要切换的条目
        IndexItem pageItem = pageItemList.get(currentIndex);
        if (pageItem.fragment == null) {
            transaction.add(R.id.fl_index_fragment, getIndexItemFragment(pageItem));
        } else {
            transaction.show(pageItem.fragment);
            if (pageItem.fragment instanceof AfFragment) {
                ((AfFragment) pageItem.fragment).notifyData();
            }
        }
        // 开启事务
        transaction.commitAllowingStateLoss();
        // 回调监听
        if (mOnDrawerIndexChangeListener != null) {
            mOnDrawerIndexChangeListener.onIndexSelected(currentIndex);
        }
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
        if (pageItemList != null && pageItemList.size() > 0) {
            for (int i = 0; i < pageItemList.size(); i++) {
                IndexItem pageItem = pageItemList.get(i);
                if (pageItem.fragment != null) {
                    mFragmentManager.putFragment(outState, pageItem.fragmentName, pageItem.fragment);
                }
            }
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
        initDrawer = false;
        if (drawerItem != null) {
            drawerItem.fragment = mFragmentManager.getFragment(savedInstanceState, drawerItem.fragmentName);
        }
        if (pageItemList != null && pageItemList.size() > 0) {
            for (int i = 0; i < pageItemList.size(); i++) {
                IndexItem pageItem = pageItemList.get(i);
                pageItem.fragment = (Fragment) mFragmentManager.getFragment(savedInstanceState, pageItem.fragmentName);
            }
        }
    }

    /**
     * 设置手势滑动锁定
     *
     * @param locked 是否锁定（锁定后手势失效）
     */
    public DrawerIndexView setDrawerLock(boolean locked) {
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
    public DrawerIndexView setDrawerOpen(boolean opened) {
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
        if (mOnDrawerIndexChangeListener != null) {
            mOnDrawerIndexChangeListener.onStateChanged(newState);
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
     * 设置页面切换的监听
     *
     * @param listener
     */
    public void setOnDrawerIndexChangeListener(OnDrawerIndexChangeListener listener) {
        mOnDrawerIndexChangeListener = listener;
    }

    /**
     * 设置页面切换的监听
     *
     * @param listener
     */
    public DrawerIndexView addOnDrawerIndexChangeListener(OnDrawerIndexChangeListener listener) {
        setDrawerLayoutListener();
        setOnDrawerIndexChangeListener(listener);
        return this;
    }

    /**
     * 页面切换的监听
     */
    public interface OnDrawerIndexChangeListener {
        /**
         * 切换页面
         *
         * @param position
         */
        void onIndexSelected(int position);

        /**
         * 状态改变
         *
         * @param newState
         */
        void onStateChanged(int newState);
    }
}