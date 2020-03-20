package com.sinyun.server.commons.ssh2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理
 * @author gongwnejun
 */
public class CacheManager {

    private static final Map<String, Object> sessions = new ConcurrentHashMap<String, Object>();

    public static void put(String key, Object obj){
        sessions.put(key, obj);
    }

    private static void remove (String key) {
        sessions.remove(key);
    }

    public static Object get (String key) {
        return sessions.get(key);
    }

    public static synchronized <T> T get (String key, Class<T> clazz) {
        Object obj = sessions.get(key);
        T t = null;
        if (null != obj) {
            t = clazz.cast(sessions.get(key));
        }
        return t;
    }
}
