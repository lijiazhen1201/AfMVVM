package cn.appoa.afutils.app;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


import cn.appoa.afutils.net.LogUtils;

/**
 * 泛型工具类
 */
public final class GenericsUtils {

    private static final String TAG = GenericsUtils.class.getSimpleName();

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenericsManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenericsType(Class clazz) {
        return getSuperClassGenericsType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenericsManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic declaration,start from 0.
     * @return the index generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenericsType(Class clazz, int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            LogUtils.w(TAG, clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            LogUtils.w(TAG, "Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            LogUtils.w(TAG, clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }
}
