package cn.appoa.afutils.res;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.Random;

/**
 * 颜色工具类
 */
public class ColorUtils {

    /**
     * 获取随机颜色
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return 颜色
     */
    public static int getRandColor() {
        String R, G, B;

        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return Color.parseColor("#" + R + G + B);
    }

    /**
     * 获取自定义背景
     *
     * @param color  色值
     * @param radius 圆角（单位dp）
     * @return
     */
    public static Drawable getBackgroundDrawable(Context context, int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(SizeUtils.dp2px(context, radius));
        drawable.setColor(color);
        return drawable;
    }

}
