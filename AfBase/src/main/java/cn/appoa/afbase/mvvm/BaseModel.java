package cn.appoa.afbase.mvvm;

import androidx.databinding.BaseObservable;
import cn.appoa.afmvvm.AfView;

public class BaseModel extends BaseObservable implements IModel {

    private AfView mView;

    public AfView getView() {
        return mView;
    }

    @Override
    public void onAttach(AfView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }
}
