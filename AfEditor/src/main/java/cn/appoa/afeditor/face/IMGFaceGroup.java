package cn.appoa.afeditor.face;


import android.graphics.Bitmap;

import java.util.List;

public class IMGFaceGroup {

    private Bitmap icon;
    private String text;
    private List<IMGFace> faceList;

    public IMGFaceGroup() {
    }

    public IMGFaceGroup(Bitmap icon, String text, List<IMGFace> faceList) {
        this.icon = icon;
        this.text = text;
        this.faceList = faceList;
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

    public List<IMGFace> getFaceList() {
        return faceList;
    }

    public void setFaceList(List<IMGFace> faceList) {
        this.faceList = faceList;
    }
}
