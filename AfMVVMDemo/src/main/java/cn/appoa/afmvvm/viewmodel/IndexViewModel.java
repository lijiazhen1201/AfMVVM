package cn.appoa.afmvvm.viewmodel;

import android.app.Application;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import cn.appoa.afbase.mvvm.BaseViewModel;
import cn.appoa.afmvvm.model.IndexModel;

public class IndexViewModel extends BaseViewModel<IndexModel> {

    public IndexViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public IndexViewModel(@NonNull @NotNull Application application, IndexModel model) {
        super(application, model);
    }

    public ObservableField<String> indexPage = new ObservableField<String>("");
}
