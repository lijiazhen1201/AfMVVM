package cn.appoa.afutils.res;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

import cn.appoa.afutils.R;

/**
 * 控件工具类
 */
public class ViewUtils {

    /**
     * TextView是否为空
     */
    public static boolean isTextEmpty(TextView textView) {
        if (textView != null) {
            String msg = textView.getText().toString().trim();
            return TextUtils.isEmpty(msg);
        }
        return true;
    }

    /**
     * 获取TextView的字符串
     *
     * @param textView
     * @return
     */
    public static String getText(TextView textView) {
        String text = "";
        if (textView != null) {
            if (isTextEmpty(textView)) {
                text = "";
            } else {
                text = textView.getText().toString().trim();
            }
        }
        return text;
    }

    /**
     * 两个TextView的字符串是否相等
     *
     * @param textView1
     * @param textView2
     * @return
     */
    public static boolean isSameText(TextView textView1, TextView textView2) {
        if (textView1 != null && textView2 != null) {
            String msg1 = textView1.getText().toString().trim();
            String msg2 = textView2.getText().toString().trim();
            if (!TextUtils.isEmpty(msg1) && !TextUtils.isEmpty(msg2)) {
                return TextUtils.equals(msg1, msg2);
            }
        }
        return false;
    }

    /**
     * 取消WebView长按事件
     *
     * @param webView
     */
    public static void cancelLongClick(WebView webView) {
        if (webView != null) {
            webView.setLongClickable(true);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
        }
    }

    /**
     * 设置View焦点
     *
     * @param view
     */
    public static void setFocus(View view) {
        if (view != null) {
            view.requestFocus();
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
        }
    }

    /**
     * View转Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(
                view.getMeasuredWidth(),
                view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        if (bitmap != null) {
            Canvas c = new Canvas(bitmap);
            view.draw(c);
            return bitmap;
        }
        return null;
    }

    /**
     * 设置未读消息数
     *
     * @param count 未读数量
     * @param tv
     */
    public static void setUnReadCount(int count, TextView tv) {
        if (tv == null) {
            return;
        }
        tv.setText(String.valueOf(count));
        tv.setBackgroundResource(R.drawable.unread_dot1);
        if (count > 9) {
            tv.setBackgroundResource(R.drawable.unread_dot2);
            if (count > 99) {
                tv.setText("99+");
            }
        }
        tv.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置TabLayout线宽
     *
     * @param tabs
     * @param leftDip  左边距
     * @param rightDip 右边距
     */
    public static void setTabLayoutIndicator(TabLayout tabs, int leftDip, int rightDip) {
        if (tabs == null)
            return;
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            // 通过反射得到tablayout的下划线的Field
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return;
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            // 得到承载下划线的LinearLayout //源码可以看到SlidingTabStrip继承得到承载下划线的LinearLayout
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return;
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip,
                Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip,
                Resources.getSystem().getDisplayMetrics());
        // 循环设置下划线的左边距和右边距
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (0, LinearLayout.LayoutParams.MATCH_PARENT,
                            1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
