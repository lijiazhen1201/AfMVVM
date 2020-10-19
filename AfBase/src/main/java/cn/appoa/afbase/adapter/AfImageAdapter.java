package cn.appoa.afbase.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class AfImageAdapter extends PagerAdapter {

    private List<ImageView> imageList;

    public AfImageAdapter(List<ImageView> imageList) {
        super();
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        if (imageList == null) {
            return 0;
        } else {
            return imageList.size();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (imageList == null) {
            return super.instantiateItem(container, position);
        } else {
            ImageView iv = imageList.get(position);
            ViewGroup parent = (ViewGroup) iv.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(imageList.get(position));
            return imageList.get(position);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (container == null) {
            super.destroyItem(container, position, object);
        } else {
            container.removeView((View) object);
        }
    }

}
