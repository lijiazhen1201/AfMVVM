package cn.appoa.afmvvm;

/**
 * View回调基类
 */
public interface AfView {

    /**
     * 网络请求的Tag（取消网络请求时用到）
     *
     * @return
     */
    String getRequestTag();

    /**
     * 显示加载框
     *
     * @param message 加载信息
     */
    void showLoading(CharSequence message);

    /**
     * 隐藏加载框
     */
    void dismissLoading();

    /**
     * 网络请求错误的回调（强制退出时用到）
     *
     * @param message 接口返回的错误信息
     */
    void errorCodeResponse(String message);

}
