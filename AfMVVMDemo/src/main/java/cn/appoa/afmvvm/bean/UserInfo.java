package cn.appoa.afmvvm.bean;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.constant.AfConstant;
import cn.appoa.afmvvm.BR;
import cn.appoa.afutils.file.SpUtils;

/**
 * 用户资料
 */
public class UserInfo extends BaseObservable {

    private String token;
    private String id;
    private String phone;
    private String photo;
    private String name;

    @Bindable
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        notifyPropertyChanged(BR.token);
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
        notifyPropertyChanged(BR.photo);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo() {
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_INFO, JSON.toJSONString(this));
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_TOKEN, TextUtils.isEmpty(token) ? "" : token);
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_ID, TextUtils.isEmpty(id) ? "" : id);
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_PHONE, TextUtils.isEmpty(phone) ? "" : phone);
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_AVATAR, TextUtils.isEmpty(photo) ? "" : photo);
        SpUtils.putData(AfApplication.getAppContext(),
                AfConstant.USER_NICK_NAME, TextUtils.isEmpty(name) ? "" : name);
    }
}
