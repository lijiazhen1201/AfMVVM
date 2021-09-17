package cn.appoa.afeditor.face;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import cn.appoa.afbase.adapter.AfViewAdapter;
import cn.appoa.afeditor.core.utils.IMGDensityUtils;


public class IMGFacePagerView extends ViewPager {

    private List<IMGFaceGroup> faceGroupList;
    private List<View> viewpages;
    private FacePagerViewListener pagerViewListener;

    public IMGFacePagerView(Context context) {
        this(context, null);
    }

    public IMGFacePagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(List<IMGFaceGroup> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        this.faceGroupList = list;
        this.viewpages = new ArrayList<>();
        for (int i = 0; i < faceGroupList.size(); i++) {
            IMGFaceGroup group = faceGroupList.get(i);
            final List<IMGFace> faceList = group.getFaceList();
            int padding = IMGDensityUtils.dip2px(getContext(), 12);
            TextView tv_text = new TextView(getContext());
            tv_text.setText(group.getText());
            tv_text.setTextColor(Color.WHITE);
            tv_text.setTextSize(12);
            tv_text.setPadding(padding, padding, padding, padding);
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
            IMGFaceGridAdapter mAdapter = new IMGFaceGridAdapter(0, faceList);
            mAdapter.addHeaderView(tv_text);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter<?, ?> adapter, View view, int position) {
                    IMGFace face = faceGroupList.get(getCurrentItem()).getFaceList().get(position);
                    if (pagerViewListener != null) {
                        pagerViewListener.onFaceItemClick(face);
                    }
                }
            });
            recyclerView.setAdapter(mAdapter);
            this.viewpages.add(recyclerView);
        }
        AfViewAdapter adapter = new AfViewAdapter(this.viewpages);
        setAdapter(adapter);
        addOnPageChangeListener(new SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (pagerViewListener != null) {
                    pagerViewListener.onGroupPositionChanged(position);
                }
            }
        });
    }

    public void setPagerViewListener(FacePagerViewListener pagerViewListener) {
        this.pagerViewListener = pagerViewListener;
    }

    public interface FacePagerViewListener {

        void onFaceItemClick(IMGFace face);

        void onGroupPositionChanged(int groupPosition);
    }
}
