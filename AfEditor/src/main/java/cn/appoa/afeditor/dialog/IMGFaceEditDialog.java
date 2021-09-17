package cn.appoa.afeditor.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afeditor.AfEditor;
import cn.appoa.afeditor.R;
import cn.appoa.afeditor.face.IMGFaceGroup;
import cn.appoa.afeditor.face.IMGFaceMenu;


public class IMGFaceEditDialog extends Dialog {

    private static final String TAG = "IMGFaceEditDialog";
    private IMGFaceMenu faceMenu;
    private Callback mCallback;

    public IMGFaceEditDialog(Context context, Callback callback) {
        super(context, R.style.ImageFaceDialog);
        setContentView(R.layout.image_dialog_face);
        mCallback = callback;
        Window window = getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        faceMenu = findViewById(R.id.face_menu);
        List<IMGFaceGroup> list = new ArrayList<>();
        if (AfEditor.getInstance().getFaceProvider() != null) {
            list.addAll(AfEditor.getInstance().getFaceProvider().getFaceGroupList());
        }
        faceMenu.init(list);
        faceMenu.setMenuListener(new IMGFaceMenu.OnFaceMenuListener() {
            @Override
            public void closeMenu() {
                dismiss();
            }

            @Override
            public void confirmMenu(Bitmap bitmap) {
                if (bitmap != null && mCallback != null) {
                    mCallback.onFace(bitmap);
                }
                dismiss();
            }
        });
    }

    public interface Callback {

        void onFace(Bitmap bm);
    }
}
