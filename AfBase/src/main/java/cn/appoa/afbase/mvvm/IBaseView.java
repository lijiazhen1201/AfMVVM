package cn.appoa.afbase.mvvm;

public interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    void initParams();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

    /**
     * 初始化数据
     */
    void initData();

}
