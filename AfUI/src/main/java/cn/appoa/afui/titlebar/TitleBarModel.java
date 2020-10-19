package cn.appoa.afui.titlebar;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import cn.appoa.afbase.mvvm.BaseModel;

public class TitleBarModel extends BaseModel {

    public ObservableField<Integer> titleBarPaddingTop = new ObservableField<>(0);
    public ObservableField<String> titleText = new ObservableField<>("");
    public ObservableField<String> leftText = new ObservableField<>("");
    public ObservableField<String> rightText = new ObservableField<>("");
    public ObservableField<Drawable> leftImage = new ObservableField<>();
    public ObservableField<Drawable> rightImage = new ObservableField<>();
    public ObservableField<Drawable> rightImage2 = new ObservableField<>();

    public ObservableInt titleBarVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt titleTextVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt leftTextVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt rightTextVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt leftImageVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt rightImageVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt rightImage2VisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt lineVisibleObservable = new ObservableInt(View.GONE);

}
