package cn.appoa.afui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afbase.dialog.BaseDialog;
import cn.appoa.afui.R;
import cn.appoa.afui.bean.BMapApi;
import cn.appoa.afui.bean.BMapCityList;
import cn.appoa.afui.bean.BMapDistrictList;
import cn.appoa.afui.bean.BMapProvinceList;
import cn.appoa.afui.widget.wheel.ArrayWheelAdapter;
import cn.appoa.afui.widget.wheel.OnWheelChangedListener;
import cn.appoa.afui.widget.wheel.WheelView;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.listener.OnCallbackListener;

/**
 * 省市区三级区域滚轮
 */
public class AreaWheelDialog extends BaseDialog
        implements OnWheelChangedListener {

    public AreaWheelDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    protected TextView tv_dialog_cancel;
    protected TextView tv_dialog_confirm;
    protected TextView tv_dialog_title;
    protected WheelView mWheelView1;
    protected WheelView mWheelView2;
    protected WheelView mWheelView3;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_area_wheel, null);
        tv_dialog_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
        tv_dialog_cancel.setOnClickListener(this);
        tv_dialog_confirm = (TextView) view.findViewById(R.id.tv_dialog_confirm);
        tv_dialog_confirm.setOnClickListener(this);
        tv_dialog_title = (TextView) view.findViewById(R.id.tv_dialog_title);
        mWheelView1 = (WheelView) view.findViewById(R.id.mWheelView1);
        mWheelView1.addChangingListener(this);
        mWheelView2 = (WheelView) view.findViewById(R.id.mWheelView2);
        mWheelView2.addChangingListener(this);
        mWheelView3 = (WheelView) view.findViewById(R.id.mWheelView3);
        mWheelView3.addChangingListener(this);
        getProvinceList();
        return initBottomInputMethodDialog(view, context);
    }

    /**
     * 显示弹窗
     *
     * @param title
     */
    public void showAreaDialog(CharSequence title) {
        tv_dialog_title.setText(title);
        showDialog();
    }

    /**
     * 显示弹窗
     *
     * @param title
     * @param confirm
     */
    public void showAreaDialog(CharSequence title, CharSequence confirm) {
        tv_dialog_title.setText(title);
        tv_dialog_confirm.setText(confirm);
        showDialog();
    }

    /**
     * 显示弹窗
     *
     * @param title
     * @param confirm
     * @param cancel
     */
    public void showAreaDialog(CharSequence title, CharSequence confirm, CharSequence cancel) {
        tv_dialog_title.setText(title);
        tv_dialog_confirm.setText(confirm);
        tv_dialog_cancel.setText(cancel);
        showDialog();
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.tv_dialog_confirm) {
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(-1, id1, id2, id3, name1, name2, name3);
            }
        } else if (id == R.id.tv_dialog_cancel) {
            // 取消
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(-2, id1, id2, id3, name1, name2, name3);
            }
        }
        dismissDialog();
    }

    protected int position1;
    protected int position2;
    protected int position3;
    protected String id1;
    protected String id2;
    protected String id3;
    protected String name1;
    protected String name2;
    protected String name3;

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        int id = wheel.getId();
        if (id == R.id.mWheelView1) {// 省
            position1 = newValue;
            id1 = provinces.get(position1).area_code;
            name1 = provinces.get(position1).area_name;
            initCityAdapter(provinces.get(position1).sub);
        } else if (id == R.id.mWheelView2) {// 市
            position2 = newValue;
            id2 = provinces.get(position1).sub.get(position2).area_code;
            name2 = provinces.get(position1).sub.get(position2).area_name;
            initDistrictAdapter(provinces.get(position1).sub.get(position2).sub);
        } else if (id == R.id.mWheelView3) {// 区
            position3 = newValue;
            id3 = provinces.get(position1).sub.get(position2).sub.get(position3).area_code;
            name3 = provinces.get(position1).sub.get(position2).sub.get(position3).area_name;
        }
    }

    protected List<BMapProvinceList> provinces;

    /**
     * 获取三级列表
     */
    protected void getProvinceList() {
        provinces = BMapApi.getProvinceList(context);
        initProviceAdapter(provinces);
    }

    protected void initProviceAdapter(List<BMapProvinceList> list) {
        List<String> objs = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                objs.add(list.get(i).area_name);
            }
        }
        initAdapter(1, objs.toArray(new String[objs.size()]), mWheelView1);
    }

    protected void initCityAdapter(List<BMapCityList> list) {
        List<String> objs = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                objs.add(list.get(i).area_name);
            }
        }
        initAdapter(2, objs.toArray(new String[objs.size()]), mWheelView2);
    }

    protected void initDistrictAdapter(List<BMapDistrictList> list) {
        List<String> objs = new ArrayList<String>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                objs.add(list.get(i).area_name);
            }
        }
        initAdapter(3, objs.toArray(new String[objs.size()]), mWheelView3);
    }

    protected void initAdapter(int index, String[] items, WheelView mWheelView) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, new String[]{});
        switch (index) {
            case 1:
                position1 = 0;
                id1 = "";
                name1 = "";
                initCityAdapter(null);
                break;
            case 2:
                position2 = 0;
                id2 = "";
                name2 = "";
                initDistrictAdapter(null);
                break;
            case 3:
                position3 = 0;
                id3 = "";
                name3 = "";
                break;
            default:
                break;
        }
        if (items != null && items.length > 0) {
            adapter = new ArrayWheelAdapter<String>(context, items);
            switch (index) {
                case 1:
                    id1 = provinces.get(position1).area_code;
                    name1 = provinces.get(position1).area_name;
                    initCityAdapter(provinces.get(position1).sub);
                    break;
                case 2:
                    id2 = provinces.get(position1).sub.get(position2).area_code;
                    name2 = provinces.get(position1).sub.get(position2).area_name;
                    initDistrictAdapter(provinces.get(position1).sub.get(position2).sub);
                    break;
                case 3:
                    id3 = provinces.get(position1).sub.get(position2).sub.get(position3).area_code;
                    name3 = provinces.get(position1).sub.get(position2).sub.get(position3).area_name;
                    break;
                default:
                    break;
            }
        }
        mWheelView.setAdapter(adapter);
        mWheelView.setCurrentItem(0);
    }

}
