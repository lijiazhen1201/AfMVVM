package cn.appoa.afbase.mvvm;

import cn.appoa.afmvvm.AfView;

public interface IModel {

    /**
     * 绑定view回调
     *
     * @param view
     */
    void onAttach(AfView view);

    /**
     * 解绑view回调，清空数据等操作
     */
    void onDetach();

}
