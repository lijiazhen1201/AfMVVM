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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import cn.appoa.afbase.fragment.AfFragment;
import cn.appoa.afui.R;

/**
 * 带底部导航的主页控件
 */
public class IndexView extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

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
    private OnIndexChangeListener mOnIndexChangeListener;

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
        View view = View.inflate(context, R.layout.layout_index_view, this);
        indexLayout = view.findViewById(R.id.index_layout);
        rgIndexTab = view.findViewById(R.id.rg_index_tab);
        indexDividerBottom = view.findViewById(R.id.index_divider_bottom);
        flIndexFragment = view.findViewById(R.id.fl_index_fragment);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndexView);
            array.recycle();
        }
    }

    /**
     * 初始化FragmentManager
     *
     * @param manager
     * @return
     */
    public IndexView initManager(FragmentManager manager) {
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
    public IndexView addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName) {
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
    public IndexView addIndexItem(String label, @DrawableRes int icon, @NonNull String fragmentName, Bundle bundle) {
        return addIndexItem(new IndexItem(label, icon, fragmentName, bundle));
    }

    /**
     * 添加IndexItem
     *
     * @param pageItem IndexItem
     * @return
     */
    public IndexView addIndexItem(IndexItem pageItem) {
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
    public IndexView startIndex() {
        return startIndex(0);
    }

    /**
     * 首次切换下标
     *
     * @param index
     * @return
     */
    public IndexView startIndex(int index) {
        startIndex = index;
        rgIndexTab.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCheckedIndex(startIndex);
            }
        }, 300);
        return this;
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
            try {
                Class<?> fragmentClass = Class.forName(pageItem.fragmentName);
                pageItem.fragment = (Fragment) fragmentClass.newInstance();
                if (pageItem.bundle != null) {
                    pageItem.fragment.setArguments(pageItem.bundle);
                }
                transaction.add(R.id.fl_index_fragment, pageItem.fragment);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            transaction.show(pageItem.fragment);
            if (pageItem.fragment instanceof AfFragment) {
                ((AfFragment) pageItem.fragment).notifyData();
            }
        }
        // 开启事务
        transaction.commitAllowingStateLoss();
        // 回调监听
        if (mOnIndexChangeListener != null) {
            mOnIndexChangeListener.onIndexSelected(currentIndex);
        }
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
        if (pageItemList != null && pageItemList.size() > 0) {
            for (int i = 0; i < pageItemList.size(); i++) {
                IndexItem pageItem = pageItemList.get(i);
                pageItem.fragment = (Fragment) mFragmentManager.getFragment(savedInstanceState, pageItem.fragmentName);
            }
        }
    }

    /**
     * 设置页面切换的监听
     *
     * @param listener
     */
    public void setOnIndexChangeListener(OnIndexChangeListener listener) {
        mOnIndexChangeListener = listener;
    }

    /**
     * 设置页面切换的监听
     *
     * @param listener
     */
    public IndexView addOnIndexChangeListener(OnIndexChangeListener listener) {
        setOnIndexChangeListener(listener);
        return this;
    }


    /**
     * 页面切换的监听
     */
    public interface OnIndexChangeListener {
        /**
         * 切换页面
         *
         * @param position
         */
        void onIndexSelected(int position);
    }
}