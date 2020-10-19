package cn.appoa.afutils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 年月日补0
     *
     * @param value
     * @return
     */
    public static String formatInt(int value) {
        String result = value + "";
        if (value < 100) {
            result = String.format(Locale.getDefault(), "%02d", value);
        }
        return result;
    }

    /**
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String getFormatData(String time) {
        return getFormatData(time, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     *
     * @param time
     * @param patternTo
     * @return
     */
    public static String getFormatData(String time, String patternTo) {
        return getFormatData(time, patternTo, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     *
     * @param time
     * @param patternTo
     * @param patternFrom
     * @return
     */
    public static String getFormatData(String time, String patternTo, String patternFrom) {
        String formatData = "";
        try {
            SimpleDateFormat formatFrom = new SimpleDateFormat(patternFrom, Locale.getDefault());
            SimpleDateFormat formatTo = new SimpleDateFormat(patternTo, Locale.getDefault());
            Date endDate = formatFrom.parse(time);
            formatData = formatTo.format(endDate);
            return formatData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formatData;
    }

}
