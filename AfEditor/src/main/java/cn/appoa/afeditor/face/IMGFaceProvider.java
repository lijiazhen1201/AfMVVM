package cn.appoa.afeditor.face;


import java.util.List;

public interface IMGFaceProvider {

    /**
     * 自定义表情
     *
     * @return
     */
    List<IMGFaceGroup> getFaceGroupList();

    /**
     * 自定义涂鸦颜色
     *
     * @return
     */
    List<Integer> getDoodleColorList();

    /**
     * 自定义文字颜色
     *
     * @return
     */
    List<Integer> getTextColorList();
}
