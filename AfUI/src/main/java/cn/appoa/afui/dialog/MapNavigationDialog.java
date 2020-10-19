package cn.appoa.afui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.appoa.afbase.dialog.BaseDialog;
import cn.appoa.afui.R;
import cn.appoa.afui.constant.CallbackType;
import cn.appoa.afutils.app.FastClickUtils;
import cn.appoa.afutils.listener.OnCallbackListener;
import cn.appoa.afutils.net.MapUtils;

/**
 * 地图导航弹窗
 */
public class MapNavigationDialog extends BaseDialog {

    public MapNavigationDialog(Context context) {
        super(context);
    }

    public MapNavigationDialog(Context context, OnCallbackListener onCallbackListener) {
        super(context, onCallbackListener);
    }

    private TextView tv_baidu_map;
    private TextView tv_gaode_map;
    private TextView tv_qq_map;
    private TextView tv_dialog_cancel;

    @Override
    public Dialog initDialog(Context context) {
        View view = View.inflate(context, R.layout.dialog_map_navigation, null);
        tv_baidu_map = (TextView) view.findViewById(R.id.tv_baidu_map);
        tv_gaode_map = (TextView) view.findViewById(R.id.tv_gaode_map);
        tv_qq_map = (TextView) view.findViewById(R.id.tv_qq_map);
        tv_dialog_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
        tv_baidu_map.setOnClickListener(this);
        tv_gaode_map.setOnClickListener(this);
        tv_qq_map.setOnClickListener(this);
        tv_dialog_cancel.setOnClickListener(this);
        return initBottomInputMethodDialog(view, context);
    }

    private double slat;
    private double slon;
    private String sname;
    private double dlat;
    private double dlon;
    private String dname;

    /**
     * 显示弹窗(默认百度坐标)
     *
     * @param slat  起点纬度 可不填
     * @param slon  起点经度 可不填
     * @param sname 起点名称 可不填
     * @param dlat  终点纬度 必填
     * @param dlon  终点经度 必填
     * @param dname 终点名称 必填
     */
    public void showMapNavigationDialog(double slat, double slon, String sname,
                                        double dlat, double dlon, String dname) {
        this.slat = slat;
        this.slon = slon;
        this.sname = sname;
        this.dlat = dlat;
        this.dlon = dlon;
        this.dname = dname;
        showDialog();
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.isFastClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.tv_baidu_map) {
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_MAP_BAIDU);
            }
            MapUtils.openBaiDuNavi(context, slat, slon, sname, dlat, dlon, dname);
        } else if (id == R.id.tv_gaode_map) {
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_MAP_ALI);
            }
            double[] slatlon = MapUtils.baidu2AMap(slat, slon);
            double[] dlatlon = MapUtils.baidu2AMap(dlat, dlon);
            MapUtils.openGaoDeNavi(context, slatlon[0], slatlon[1], sname, dlatlon[0], dlatlon[1], dname);
        } else if (id == R.id.tv_qq_map) {
            if (onCallbackListener != null) {
                onCallbackListener.onCallback(CallbackType.CALLBACK_TYPE_MAP_QQ);
            }
            double[] slatlon = MapUtils.bdToGaoDe(slat, slon);
            double[] dlatlon = MapUtils.bdToGaoDe(dlat, dlon);
            MapUtils.openTencentMap(context, slatlon[0], slatlon[1], sname, dlatlon[0], dlatlon[1], dname);
        }
        dismissDialog();
    }

}
