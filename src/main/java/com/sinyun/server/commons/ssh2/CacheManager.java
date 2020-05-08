package com.sinyun.server.commons.ssh2;

import com.alibaba.fastjson.JSON;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存管理
 * @author gongwnejun
 */
public class CacheManager {

    private static final Map<String, Object> cache = new ConcurrentHashMap<String, Object>();

    public static void put(String key, Object obj){
        cache.put(key, obj);
    }

    public static boolean exitKey(String key){
        return cache.containsKey(key);
    }

    public static void remove (String key) {
        cache.remove(key);
    }

    public static Object get (String key) {
        return cache.get(key);
    }

    public static synchronized <T> T get (String key, Class<T> clazz) {
        Object obj = cache.get(key);
        T t = null;
        if (null != obj) {
            t = clazz.cast(cache.get(key));
        }
        return t;
    }

    public static synchronized <T> List<T> queryList(String prefix, Class<T> clazz){
       List<T> list = new ArrayList<>();
       Iterator<String> iterator = cache.keySet().iterator();
        String key;
        boolean isOpen = false;
        if (null != prefix && !prefix.isEmpty()){
            isOpen = true;
        }
        while (iterator.hasNext()) {
            key = iterator.next();
            if (!isOpen) {
                list.add((T) cache.get(key));
            }else if (isOpen && key.contains(prefix)) {
                list.add((T) cache.get(key));
            }
        }
        return list;
    }

    public static synchronized <T> Map<String, T> queryMap(String prefix, Class<T> clazz){
        Map<String, T> map = new HashMap<>();
        Iterator<String> iterator = cache.keySet().iterator();
        String key;
        boolean isOpen = false;
        if (null != prefix && !prefix.isEmpty()){
            isOpen = true;
        }
        while (iterator.hasNext()) {
            key = iterator.next();
            if (!isOpen) {
                map.put(key, (T) cache.get(key));
            }else if (isOpen && key.contains(prefix)) {
                map.put(key, (T) cache.get(key));
            }
        }
        return map;
    }
}
