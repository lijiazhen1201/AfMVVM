package cn.appoa.afeditor.face;


import android.graphics.Bitmap;

public class IMGFace {

    private Bitmap icon;
    private String text;

    public IMGFace() {
    }

    public IMGFace(Bitmap icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
