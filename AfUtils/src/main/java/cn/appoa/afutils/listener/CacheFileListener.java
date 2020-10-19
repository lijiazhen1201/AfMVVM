package cn.appoa.afutils.listener;

import java.io.File;

/**
 * 缓存文件监听
 */
public interface CacheFileListener {

    /**
     * 缓存成功
     *
     * @param type 0缓存失败1本地已存在此文件2已经缓存过此文件3新缓存的文件
     * @param file 缓存的文件
     */
    void onCacheFileSuccess(int type, File file);
}
