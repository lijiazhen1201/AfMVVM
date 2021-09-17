package cn.appoa.afeditor.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afeditor.R;
import cn.appoa.afeditor.core.utils.IMGDensityUtils;

public class IMGFaceScrollTabBar extends FrameLayout {

    private Context context;
    private HorizontalScrollView scrollView;
    private LinearLayout tabContainer;
    private ImageView tabClose;
    private List<View> tabList = new ArrayList<View>();
    private ScrollTabBarItemClickListener itemClickListener;

    public IMGFaceScrollTabBar(Context context) {
        this(context, null);
    }

    public IMGFaceScrollTabBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public IMGFaceScrollTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.image_face_scroll_tab_bar, this);
        scrollView = (HorizontalScrollView) findViewById(R.id.scroll_view);
        tabContainer = (LinearLayout) findViewById(R.id.tab_container);
        tabClose = (ImageView) findViewById(R.id.tab_close);
        tabClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onBarItemClick(-1);
                }
            }
        });
    }

    /**
     * add tab
     *
     * @param icon
     */
    public void addTab(Bitmap icon, int i) {
        View tabView = View.inflate(context, R.layout.image_face_scroll_tab_item, null);
        View btnTab = tabView.findViewById(R.id.btn_tab);
        btnTab.setBackgroundResource(i == 0 ? R.drawable.image_bg_face_bar_pressed : R.drawable.image_bg_face_bar_normal);
        ImageView imageView = (ImageView) tabView.findViewById(R.id.iv_icon);
        imageView.setImageBitmap(icon);
        int tabWidth = (int) getResources().getDimension(R.dimen.image_face_image_width) +
                (int) getResources().getDimension(R.dimen.image_face_image_margin);
        int tabHeight = (int) getResources().getDimension(R.dimen.image_face_top_bar_height);
        tabContainer.addView(tabView, new LinearLayout.LayoutParams(tabWidth, tabHeight));
        tabList.add(btnTab);
        final int position = tabList.size() - 1;
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onBarItemClick(position);
                }
            }
        });
    }

    /**
     * remove tab
     *
     * @param position
     */
    public void removeTab(int position) {
        tabContainer.removeViewAt(position);
        tabList.remove(position);
    }

    public void selectedTo(int position) {
        scrollTo(position);
        for (int i = 0; i < tabList.size(); i++) {
            if (position == i) {
                tabList.get(i).setBackgroundResource(R.drawable.image_bg_face_bar_pressed);
            } else {
                tabList.get(i).setBackgroundResource(R.drawable.image_bg_face_bar_normal);
            }
        }
    }

    private void scrollTo(final int position) {
        int childCount = tabContainer.getChildCount();
        if (position < childCount) {
            scrollView.post(new Runnable() {
                @Override
                public void run() {
//                    int mScrollX = tabContainer.getScrollX();
//                    int childX = (int) ViewCompat.getX(tabContainer.getChildAt(position));
//
//                    if (childX < mScrollX) {
//                        scrollView.scrollTo(childX, 0);
//                        return;
//                    }
//
//                    int childWidth = (int) tabContainer.getChildAt(position).getWidth();
//                    int hsvWidth = scrollView.getWidth();
//                    int childRight = childX + childWidth;
//                    int scrollRight = mScrollX + hsvWidth;
//
//                    if (childRight > scrollRight) {
//                        scrollView.scrollTo(childRight - scrollRight, 0);
//                        return;
//                    }
                    View buttonView = tabContainer.getChildAt(position);
                    int[] location = new int[2];
                    buttonView.getLocationInWindow(location);
                    int scrollX = location[0];
                    scrollView.smoothScrollBy(
                            scrollX - (IMGDensityUtils.getScreenWidth(getContext()) / 2)
                                    + (buttonView.getWidth() / 2),
                            0);
                }
            });
        }
    }


    public void setTabBarItemClickListener(ScrollTabBarItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ScrollTabBarItemClickListener {

        void onBarItemClick(int position);
    }
}
