package cn.appoa.afeditor.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.appoa.afeditor.R;


public class IMGFaceMenu extends FrameLayout {

    private IMGFaceScrollTabBar tabBar;
    private IMGFacePagerView pagerView;
    private List<IMGFaceGroup> faceGroupList;
    private OnFaceMenuListener menuListener;

    public IMGFaceMenu(@NonNull Context context) {
        this(context, null);
    }

    public IMGFaceMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IMGFaceMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.image_face_menu, this);
        tabBar = findViewById(R.id.tab_bar);
        pagerView = findViewById(R.id.pager_view);
    }

    public void init(List<IMGFaceGroup> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.faceGroupList = list;
        for (int i = 0; i < faceGroupList.size(); i++) {
            IMGFaceGroup group = faceGroupList.get(i);
            tabBar.addTab(group.getIcon(), i);
        }
        pagerView.init(faceGroupList);

        tabBar.setTabBarItemClickListener(new IMGFaceScrollTabBar.ScrollTabBarItemClickListener() {
            @Override
            public void onBarItemClick(int position) {
                if (position == -1) {
                    //close menu
                    if (menuListener != null) {
                        menuListener.closeMenu();
                    }
                } else {
                    pagerView.setCurrentItem(position);
                }
            }
        });
        pagerView.setPagerViewListener(new IMGFacePagerView.FacePagerViewListener() {
            @Override
            public void onFaceItemClick(IMGFace face) {
                if (menuListener != null) {
                    menuListener.confirmMenu(face.getIcon());
                }
            }

            @Override
            public void onGroupPositionChanged(int groupPosition) {
                tabBar.selectedTo(groupPosition);
            }
        });
    }

    public void setMenuListener(OnFaceMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public interface OnFaceMenuListener {

        void closeMenu();

        void confirmMenu(Bitmap bitmap);
    }
}
