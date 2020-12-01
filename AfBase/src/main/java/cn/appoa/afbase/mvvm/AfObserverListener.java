package cn.appoa.afbase.mvvm;

/**
 * 自定义Observer监听
 */
public interface AfObserverListener {

    void onObserverChanged(int type, Object o);

}
