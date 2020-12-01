package cn.appoa.afbase.mvvm;

import androidx.lifecycle.Observer;

/**
 * 自定义Observer
 */
public class AfObserver implements Observer<Object> {

    private int type;
    private AfObserverListener listener;

    public AfObserver(int type, AfObserverListener listener) {
        this.type = type;
        this.listener = listener;
    }

    @Override
    public void onChanged(Object o) {
        if (listener != null) {
            listener.onObserverChanged(type, o);
        }
    }

}
