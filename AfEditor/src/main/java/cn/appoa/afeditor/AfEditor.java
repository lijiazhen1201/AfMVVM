package cn.appoa.afeditor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afeditor.face.IMGFace;
import cn.appoa.afeditor.face.IMGFaceGroup;
import cn.appoa.afeditor.face.IMGFaceProvider;

/**
 * 图片编辑相关
 *
 * @author https://github.com/minetsh/Imaging
 */
public class AfEditor {

    private static AfEditor instance = null;

    private AfEditor() {
    }

    public synchronized static AfEditor getInstance() {
        if (instance == null) {
            instance = new AfEditor();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init() {
        setFaceProvider(new IMGFaceProvider() {
            @Override
            public List<IMGFaceGroup> getFaceGroupList() {
                List<IMGFaceGroup> list = new ArrayList<>();
                list.add(getIMGFaceGroup("bp1"));
                list.add(getIMGFaceGroup("bp2"));
                list.add(getIMGFaceGroup("dyzem1"));
                list.add(getIMGFaceGroup("dyzem2"));
                list.add(getIMGFaceGroup("dyzem3"));
                list.add(getIMGFaceGroup("dyzem4"));
                list.add(getIMGFaceGroup("dyzem5"));
                return list;
            }

            @Override
            public List<Integer> getDoodleColorList() {
                List<Integer> list = new ArrayList<>();
                return list;
            }

            @Override
            public List<Integer> getTextColorList() {
                List<Integer> list = new ArrayList<>();
                return list;
            }
        });
    }

    /**
     * 获取asset下的表情
     *
     * @param dir
     * @return
     */
    public IMGFaceGroup getIMGFaceGroup(String dir) {
        IMGFaceGroup group = new IMGFaceGroup();
        try {
            String[] images = AfApplication.getAppContext().getAssets().list(dir);
            List<IMGFace> faceList = new ArrayList<>();
            for (int i = 0; i < images.length; i++) {
                String fileName = images[i];
                InputStream open = AfApplication.getAppContext().getAssets().open(dir + "/" + fileName);
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                if (i == 0) {
                    group.setIcon(bitmap);
                    group.setText(dir);
                }
                faceList.add(new IMGFace(bitmap, fileName.substring(0, fileName.indexOf("."))));
            }
            group.setFaceList(faceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return group;
    }

    private IMGFaceProvider faceProvider;

    public IMGFaceProvider getFaceProvider() {
        if (faceProvider == null) {
            init();
        }
        return faceProvider;
    }

    public void setFaceProvider(IMGFaceProvider faceProvider) {
        this.faceProvider = faceProvider;
    }

    /**
     * 编辑图片
     *
     * @param activity
     * @param path        待编辑的文件路径
     * @param requestCode 请求码
     */
    public void editImage(Activity activity, String path, int requestCode) {
        if (!TextUtils.isEmpty(path)) {
            editImage(activity, new File(path), requestCode);
        }
    }

    /**
     * 编辑图片
     *
     * @param activity
     * @param file        待编辑的文件
     * @param requestCode 请求码
     */
    public void editImage(Activity activity, File file, int requestCode) {
        if (file != null && file.exists()) {
            Uri uri = Uri.fromFile(file);
            String path = file.getParent() + "/IMG_Edit_" + file.getName();
            editImage(activity, uri, path, requestCode);
        }
    }

    /**
     * @param activity
     * @param uri         文件uri
     * @param path        输出文件路径
     * @param requestCode 请求码
     */
    public void editImage(Activity activity, Uri uri, String path, int requestCode) {
        if (activity != null && uri != null && !TextUtils.isEmpty(path)) {
            Intent intent = new Intent(activity, IMGEditActivity.class);
            intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, uri);
            intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, path);
            activity.startActivityForResult(intent, requestCode);
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * 编辑图片
     *
     * @param fragment
     * @param path        待编辑的文件路径
     * @param requestCode 请求码
     */
    public void editImage(Fragment fragment, String path, int requestCode) {
        if (!TextUtils.isEmpty(path)) {
            editImage(fragment, new File(path), requestCode);
        }
    }

    /**
     * 编辑图片
     *
     * @param fragment
     * @param file        待编辑的文件
     * @param requestCode 请求码
     */
    public void editImage(Fragment fragment, File file, int requestCode) {
        if (file != null && file.exists()) {
            Uri uri = Uri.fromFile(file);
            String path = file.getParent() + "/IMG_Edit_" + file.getName();
            editImage(fragment, uri, path, requestCode);
        }
    }

    /**
     * @param fragment
     * @param uri         文件uri
     * @param path        输出文件路径
     * @param requestCode 请求码
     */
    public void editImage(Fragment fragment, Uri uri, String path, int requestCode) {
        if (fragment != null && uri != null && !TextUtils.isEmpty(path)) {
            Intent intent = new Intent(fragment.getActivity(), IMGEditActivity.class);
            intent.putExtra(IMGEditActivity.EXTRA_IMAGE_URI, uri);
            intent.putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, path);
            fragment.startActivityForResult(intent, requestCode);
            fragment.getActivity().overridePendingTransition(0, 0);
        }
    }

}
