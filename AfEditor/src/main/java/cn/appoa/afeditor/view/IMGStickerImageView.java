package cn.appoa.afeditor.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;


public class IMGStickerImageView extends IMGStickerView {

    private ImageView mImageView;

    public IMGStickerImageView(Context context) {
        super(context);
    }

    public IMGStickerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IMGStickerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public View onCreateContentView(Context context) {
        mImageView = new ImageView(context);
        return mImageView;
    }

    public void setImageResource(@DrawableRes int resId) {
        if (mImageView != null) {
            mImageView.setImageResource(resId);
        }
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        if (mImageView != null) {
            mImageView.setImageDrawable(drawable);
        }
    }

    public void setImageBitmap(Bitmap bm) {
        if (mImageView != null) {
            mImageView.setImageBitmap(bm);
        }
    }
}
