package cn.appoa.afmvvm.net;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import cn.appoa.afbase.app.AfApplication;
import cn.appoa.afbase.constant.AfConstant;
import cn.appoa.afutils.encrypt.AESUtils;
import cn.appoa.afutils.file.SpUtils;
import cn.appoa.afutils.net.JsonUtils;

/**
 * 接口工具类
 */
public class APIUtils extends JsonUtils {

    /**
     * 参数封装
     *
     * @return
     */
    public static Map<String, String> getParams() {
        return getParams(null, AESUtils.DEFAULT_VALUE);
    }

    /**
     * 参数封装
     *
     * @param key
     * @param value
     * @return
     */
    public static Map<String, String> getParams(String key, String value) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(value)) {
            if (value.length() > 15) {
                params.put("encrypt", AESUtils.encode(value.substring(0, 15)));
            } else {
                params.put("encrypt", AESUtils.encode(value));
            }
        }
        if (!TextUtils.isEmpty(key)) {
            params.put(key, value);
        }
        return params;
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return (Boolean) SpUtils.getData(AfApplication.getAppContext(), AfConstant.IS_LOGIN, false);
    }

    /**
     * 用户id
     *
     * @return
     */
    public static String getUserId() {
        return (String) SpUtils.getData(AfApplication.getAppContext(), AfConstant.USER_ID, "");
    }

}
