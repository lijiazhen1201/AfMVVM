package cn.appoa.afmvvm.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import cn.appoa.afmvvm.BR;

/**
 * 关于我们
 */
public class AboutUs extends BaseObservable {

    private String image;
    private String site;
    private String email;
    private String mobile;
    private String copyRight;
    private String fillingNum;

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @Bindable
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
        notifyPropertyChanged(BR.site);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
        notifyPropertyChanged(BR.copyRight);
    }

    @Bindable
    public String getFillingNum() {
        return fillingNum;
    }

    public void setFillingNum(String fillingNum) {
        this.fillingNum = fillingNum;
        notifyPropertyChanged(BR.fillingNum);
    }
}
