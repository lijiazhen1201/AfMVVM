package cn.appoa.afbase.constant;

/**
 * Router在Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 */
public class AfRouterActivityPath {

    /**
     * Activity
     */
    public static final String ACTIVITY = "/activity";
    /**
     * 登录
     */
    public static final String ACTIVITY_LOGIN = ACTIVITY + "/login";
    /**
     * 注册
     */
    public static final String ACTIVITY_REGISTER = ACTIVITY + "/register";
    /**
     * 启动页
     */
    public static final String ACTIVITY_START = ACTIVITY + "/start";
    /**
     * 引导页
     */
    public static final String ACTIVITY_GUIDE = ACTIVITY + "/guide";
    /**
     * 主页
     */
    public static final String ACTIVITY_MAIN = ACTIVITY + "/main";

}
