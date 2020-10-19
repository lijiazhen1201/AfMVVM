package cn.appoa.afui.model;

import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.mvvm.BaseModel;

public class DefaultHintModel extends BaseModel {

    public ObservableField<String> titleText = new ObservableField<>("提示");
    public ObservableField<String> contentText = new ObservableField<>("");
    public ObservableField<String> cancelText = new ObservableField<>("取消");
    public ObservableField<String> confirmText = new ObservableField<>("确定");

    public ObservableInt titleVisible = new ObservableInt(View.VISIBLE);
    public ObservableInt cancelVisible = new ObservableInt(View.VISIBLE);
    public ObservableInt lineVisible = new ObservableInt(View.VISIBLE);
    public ObservableInt confirmVisible = new ObservableInt(View.VISIBLE);

}
