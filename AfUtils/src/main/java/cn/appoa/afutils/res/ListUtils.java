package cn.appoa.afutils.res;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 集合工具类
 */
public class ListUtils {

    /**
     * 交换list的两个元素
     *
     * @param list
     * @param oldPosition
     * @param newPosition
     */
    public static <T> void swaList(List<T> list, int oldPosition, int newPosition) {
        if (list != null && list.size() > 0 //
                && oldPosition >= 0 && oldPosition < list.size()//
                && newPosition >= 0 && newPosition < list.size()//
                && oldPosition != newPosition) {
            // 向前移动，前面的元素需要向后移动
            if (oldPosition < newPosition) {
                for (int i = oldPosition; i < newPosition; i++) {
                    Collections.swap(list, i, i + 1);
                }
            }
            // 向后移动，后面的元素需要向前移动
            if (oldPosition > newPosition) {
                for (int i = oldPosition; i > newPosition; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
        }
    }

    /**
     * 按页数分割list
     *
     * @param list
     * @param pageSize 一页几个
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        List<List<T>> listArray = new ArrayList<List<T>>();
        List<T> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % pageSize == 0) {// 每次到达页大小的边界就重新申请一个subList
                subList = new ArrayList<T>();
                listArray.add(subList);
            }
            subList.add(list.get(i));
        }
        return listArray;
    }

}
