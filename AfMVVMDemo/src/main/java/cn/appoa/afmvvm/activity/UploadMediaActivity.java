package cn.appoa.afmvvm.activity;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.upload.UploadAvatarActivity;
import cn.appoa.afmvvm.activity.upload.UploadImageActivity;
import cn.appoa.afmvvm.activity.upload.UploadImageEditActivity;
import cn.appoa.afmvvm.activity.upload.UploadVideoActivity;
import cn.appoa.afmvvm.activity.upload.UploadVoiceActivity;
import cn.appoa.afmvvm.bean.MainMenu;

/**
 * 多媒体上传
 */

public class UploadMediaActivity extends MainMenuActivity {

    @Override
    protected String initTitle() {
        return "多媒体上传";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("UploadAvatar\n【头像上传】", UploadAvatarActivity.class));
        dataList.add(new MainMenu("UploadVideo\n【视频上传】", UploadVideoActivity.class));
        dataList.add(new MainMenu("UploadVoice\n【音频上传】", UploadVoiceActivity.class));
        dataList.add(new MainMenu("UploadImage\n【图片上传】", UploadImageActivity.class));
        dataList.add(new MainMenu("UploadImageEdit\n【图片上传编辑】", UploadImageEditActivity.class));
        return dataList;
    }

}
