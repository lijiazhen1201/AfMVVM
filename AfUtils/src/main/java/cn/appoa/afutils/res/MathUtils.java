package cn.appoa.afutils.res;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * 数学工具类
 */
public class MathUtils {

    /**
     * 保留一位小数
     *
     * @param point
     * @return
     */
    public static String get1Point(double point) {
        return getPoint("#.0", point);
    }

    /**
     * 保留一位小数
     *
     * @param pointStr
     * @return
     */
    public static String get1Point(String pointStr) {
        try {
            return get1Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 保留两位小数
     *
     * @param point
     * @return
     */
    public static String get2Point(double point) {
        return getPoint("#.00", point);
    }

    /**
     * 保留两位小数
     *
     * @param pointStr
     * @return
     */
    public static String get2Point(String pointStr) {
        try {
            return get2Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 保留三位小数
     *
     * @param point
     * @return
     */
    public static String get3Point(double point) {
        return getPoint("#.000", point);
    }

    /**
     * 保留三位小数
     *
     * @param pointStr
     * @return
     */
    public static String get3Point(String pointStr) {
        try {
            return get3Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 保留四位小数
     *
     * @param point
     * @return
     */
    public static String get4Point(double point) {
        return getPoint("#.0000", point);
    }

    /**
     * 保留四位小数
     *
     * @param pointStr
     * @return
     */
    public static String get4Point(String pointStr) {
        try {
            return get4Point(Double.parseDouble(pointStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointStr;
    }

    /**
     * 格式化小数
     *
     * @param pattern
     * @param point
     * @return
     */
    public static String getPoint(String pattern, double point) {
        if (TextUtils.isEmpty(pattern)) {
            return String.valueOf(point);
        }
        String format = new DecimalFormat(pattern).format(point);
        if (TextUtils.isEmpty(format)) {
            return String.valueOf(point);
        }
        if (format.startsWith(".")) {
            return "0" + format;
        }
        if (format.startsWith("-.")) {//负数情况
            return "-0" + format.substring(1);
        }
        return format;
    }

}
