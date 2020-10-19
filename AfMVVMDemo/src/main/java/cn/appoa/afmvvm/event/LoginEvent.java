package cn.appoa.afmvvm.event;

/**
 * 登录事件总线
 */
public class LoginEvent {

    /**
     * 1登录成功2退出登录成功
     */
    public int type;

    public LoginEvent(int type) {
        this.type = type;
    }
}
