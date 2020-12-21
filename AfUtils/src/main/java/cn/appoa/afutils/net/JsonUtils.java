package cn.appoa.afutils.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.appoa.afutils.AfUtils;
import cn.appoa.afutils.toast.ToastUtils;

/**
 * json解析工具类
 */
public class JsonUtils {

    private static int TYPE = 1;
    private static String CODE = "code";
    private static String MESSAGE = "message";
    private static String DATA = "data";
    private static String STATE = "state";
    private static String RESULT = "result";
    private static String LIST = "list";

    /**
     * 是否用Gson解析
     */
    public static boolean isGson = false;

    /**
     * 强制下线code
     */
    private static int ERROR_CODE = 401;

    /**
     * 初始化
     *
     * @param type
     */
    public static void init(int type) {
        TYPE = type;
    }

    /**
     * 初始化
     *
     * @param code
     * @param message
     * @param data
     */
    public static void init(String code, String message, String data) {
        TYPE = 1;
        CODE = code;
        MESSAGE = message;
        DATA = data;
    }

    /**
     * 初始化
     *
     * @param code
     * @param message
     * @param data
     * @param list
     */
    public static void init(String code, String message, String data, String list) {
        TYPE = 1;
        CODE = code;
        MESSAGE = message;
        DATA = data;
        LIST = list;
    }

    /**
     * 初始化
     *
     * @param state
     * @param result
     */
    public static void init(String state, String result) {
        TYPE = 2;
        STATE = state;
        RESULT = result;
    }

    /**
     * 初始化
     *
     * @param code
     * @param message
     * @param data
     */
    public static void init(int type, String code, String message, String data) {
        TYPE = type;
        CODE = code;
        MESSAGE = message;
        DATA = data;
    }

    /**
     * 初始化
     *
     * @param code
     * @param message
     * @param data
     * @param list
     */
    public static void init(int type, String code, String message, String data, String list) {
        TYPE = type;
        CODE = code;
        MESSAGE = message;
        DATA = data;
        LIST = list;
    }

    /**
     * 初始化强制下线code
     *
     * @param code
     */
    public static void initError(int code) {
        ERROR_CODE = code;
    }

    /**
     * 是否是下线code
     *
     * @param json
     * @return
     */
    public static boolean isErrorCode(String json) {
        boolean b = false;
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject obj = new JSONObject(json);
                if (TYPE == 1) {
                    int code = obj.getInt(CODE);
                    if (code == ERROR_CODE) {
                        b = true;
                    }
                } else if (TYPE == 2) {
                    String state = obj.getString(STATE);
                    if (TextUtils.equals(state, String.valueOf(ERROR_CODE))) {
                        b = true;
                    }
                } else if (TYPE == 3) {
                    String state = obj.getString(STATE);
                    if (TextUtils.equals(state, String.valueOf(ERROR_CODE))) {
                        b = true;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 过滤json数据
     *
     * @param json
     * @return
     */
    public static boolean filterJson(String json) {
        boolean b = false;
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject obj = new JSONObject(json);
                if (TYPE == 1) {
                    int code = obj.getInt(CODE);
                    if (code == 200) {
                        b = true;
                    }
                } else if (TYPE == 2) {
                    String state = obj.getString(STATE);
                    if (TextUtils.equals(state, "true")) {
                        b = true;
                    }
                } else if (TYPE == 3) {
                    return obj.getBoolean(CODE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 解析json数据
     *
     * @param json
     * @param t
     * @return
     */
    public static <T> List<T> parse(String json, Class<T> t) {
        if (isGson) {
            return new Gson().fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        } else {
            return JSON.parseArray(json, t);
        }
    }

    /**
     * 解析json数据
     *
     * @param json
     * @param t
     * @return
     */
    public static <T> List<T> parseJson(String json, Class<T> t) {
        List<T> list = new ArrayList<>();
        if (filterJson(json)) {
            JSONArray array = parseJSONArray(json);
            if (array != null && array.length() > 0) {
                list = parse(array.toString(), t);
            }
        }
        return list;
    }

    /**
     * 解析json数据
     *
     * @param json
     * @param t
     * @return
     */
    public static <T> List<T> parseJsonList(String json, Class<T> t) {
        List<T> list = new ArrayList<>();
        if (filterJson(json)) {
            JSONArray array = parseJSONArrayList(json);
            if (array != null && array.length() > 0) {
                list = parse(array.toString(), t);
            }
        }
        return list;
    }

    /**
     * 解析json数组
     *
     * @param json
     * @return
     */
    public static JSONArray parseJSONArray(String json) {
        JSONArray array = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (TYPE == 1) {
                array = obj.getJSONArray(DATA);
            } else if (TYPE == 2) {
                array = obj.getJSONArray(RESULT);
            } else if (TYPE == 3) {
                array = obj.getJSONArray(DATA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    /**
     * 解析json数组
     *
     * @param json
     * @return
     */
    public static JSONArray parseJSONArrayList(String json) {
        JSONArray arrayList = null;
        try {
            JSONArray array = parseJSONArray(json);
            if (array != null && array.length() > 0) {
                JSONObject obj = array.getJSONObject(0);
                arrayList = obj.getJSONArray(LIST);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 获取提示信息
     *
     * @param json
     * @return
     */
    public static String getMsg(String json) {
        String message = null;
        try {
            if (!TextUtils.isEmpty(json)) {
                JSONObject obj = new JSONObject(json);
                if (TYPE == 1) {
                    message = obj.getString(MESSAGE);
                } else if (TYPE == 2) {
                    message = obj.getString(RESULT);
                } else if (TYPE == 3) {
                    message = obj.getString(MESSAGE);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 弹出信息
     *
     * @param context
     * @param json
     */
    public static void showMsg(Context context, String json) {
        String message = getMsg(json);
        if (!TextUtils.isEmpty(message)) {
            ToastUtils.showShort(context, message, false);
        }
    }

    /**
     * 弹出正确信息
     *
     * @param context
     * @param json
     */
    public static void showSuccessMsg(Context context, String json) {
        if (filterJson(json)) {
            showMsg(context, json);
        }
    }

    /**
     * 弹出错误信息
     *
     * @param context
     * @param json
     */
    public static void showErrorMsg(Context context, String json) {
        if (!filterJson(json)) {
            showMsg(context, json);
        }
    }

    /**
     * 读取Assets下的json文件
     *
     * @param context
     * @param fileName 文件名
     * @return
     */
    public static String getJsonString(Context context, String fileName) {
        if (context == null) {
            context = AfUtils.getInstance().getContext();
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 组装访问出错的json
     *
     * @param msg
     * @return
     */
    public static String getJsonError(String msg) {
        return "{\"msg\":\"" + msg + "\",\"status\":false,\"message\":\"" + msg + "\",\"code\":404,\"data\":[]}";
    }
}
