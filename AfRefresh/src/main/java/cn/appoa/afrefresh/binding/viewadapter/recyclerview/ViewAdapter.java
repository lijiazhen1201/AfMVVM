package cn.appoa.afrefresh.binding.viewadapter.recyclerview;

import com.chad.library.adapter.base.BaseQuickAdapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import cn.appoa.afrefresh.divider.GridItemDecoration;
import cn.appoa.afrefresh.divider.ListItemDecoration;

public class ViewAdapter {

    @BindingAdapter(value = {"adapter", "decorType", "decorHeight", "decorColor",
            "decorOrientation", "decorSize", "decorMargin"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, BaseQuickAdapter adapter,
                                  int decorType, float decorHeight, int decorColor,
                                  int decorOrientation, int decorSize, boolean decorMargin) {
        try {
            recyclerView.setAdapter(adapter);
            if (decorType > 0) {
                if (recyclerView.getItemDecorationCount() > 0) {
                    recyclerView.removeItemDecoration(recyclerView.getItemDecorationAt(0));
                }
                RecyclerView.ItemDecoration decor = null;
                if (decorType == 1) {
                    decor = new ListItemDecoration(recyclerView.getContext(), decorOrientation);
                    if (decorHeight != 0) {
                        ((ListItemDecoration) decor).setDecorationHeight(decorHeight);
                    }
                    if (decorColor != 0) {
                        ((ListItemDecoration) decor).setDecorationColor(decorColor);
                    }
                } else if (decorType == 2) {
                    if (decorSize == 0) {
                        decorSize = 1;
                    }
                    decor = new GridItemDecoration(recyclerView.getContext(), adapter, decorSize, decorMargin);
                    if (decorHeight != 0) {
                        ((GridItemDecoration) decor).setDecorationHeight(decorHeight);
                    }
                    if (decorColor != 0) {
                        ((GridItemDecoration) decor).setDecorationColor(decorColor);
                    }
                }
                if (decor != null) {
                    recyclerView.addItemDecoration(decor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
