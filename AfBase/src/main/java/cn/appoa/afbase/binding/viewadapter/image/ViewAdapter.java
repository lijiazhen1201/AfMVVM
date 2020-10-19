package cn.appoa.afbase.binding.viewadapter.image;


import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import cn.appoa.afbase.R;
import cn.appoa.afbase.app.AfApplication;

public final class ViewAdapter {

    @BindingAdapter(value = {"imageUrl", "loadingImg", "errorImg", "defaultImg"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String imageUrl, Drawable loadingImg, Drawable errorImg, Drawable defaultImg) {
        if (defaultImg == null) {
            defaultImg = ContextCompat.getDrawable(imageView.getContext(), R.drawable.default_img);
        }
        if (loadingImg == null) {
            loadingImg = defaultImg;
        }
        if (errorImg == null) {
            errorImg = defaultImg;
        }
        AfApplication.getImageLoader().loadImage(imageUrl, imageView, loadingImg, errorImg, defaultImg);
    }

}

