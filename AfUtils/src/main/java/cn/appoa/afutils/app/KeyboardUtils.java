package cn.appoa.afutils.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import cn.appoa.afutils.AfUtils;

/**
 * 键盘工具类
 */
public class KeyboardUtils {

    /**
     * 弹出键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     */
    public static void closeSoftInput(Context context) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 输入法不遮挡界面
     *
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        if (root != null && scrollToView != null) {
            root.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            Rect rect = new Rect();
                            // 获取root在窗体的可视区域
                            root.getWindowVisibleDisplayFrame(rect);
                            // 获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                            int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                            // 若不可视区域高度大于100，则键盘显示
                            if (rootInvisibleHeight > 100) {
                                int[] location = new int[2];
                                // 获取scrollToView在窗体的坐标
                                scrollToView.getLocationInWindow(location);
                                // 计算root滚动高度，使scrollToView在可见区域的底部
                                int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                                root.scrollTo(0, srollHeight);
                            } else {
                                // 键盘隐藏
                                root.scrollTo(0, 0);
                            }
                        }
                    });
        }
    }

}
