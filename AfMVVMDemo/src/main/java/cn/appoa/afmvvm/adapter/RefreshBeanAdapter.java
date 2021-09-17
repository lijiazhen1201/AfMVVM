package cn.appoa.afmvvm.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.appoa.afbase.adapter.AfAdapter;
import cn.appoa.afbase.adapter.AfHolder;
import cn.appoa.afmvvm.R;
import cn.appoa.afmvvm.bean.RefreshBean;
import cn.appoa.afutils.toast.ToastUtils;

public class RefreshBeanAdapter extends AfAdapter<RefreshBean> {

    public RefreshBeanAdapter(Context mContext, List<RefreshBean> itemList, int layoutId) {
        super(mContext, itemList, layoutId);
    }

    @Override
    public void setNewData(List<RefreshBean> itemList) {
        super.setNewData(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void init(AfHolder zmHolder, final RefreshBean t, int position) {
        TextView tv_refresh_id = zmHolder.getView(R.id.tv_refresh_id);
        TextView tv_refresh_title = zmHolder.getView(R.id.tv_refresh_title);
        if (t != null) {
            tv_refresh_id.setText("ID:" + t.ID);
            tv_refresh_title.setText(t.Title);
            zmHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showShort(mContext, t.Title, true);
                }
            });
        }
    }
}
