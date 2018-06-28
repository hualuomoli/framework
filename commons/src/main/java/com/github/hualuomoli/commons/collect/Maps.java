package com.github.hualuomoli.commons.collect;

import java.util.HashMap;

public class Maps {

    /**
     * 创建HashMap
     * @param <K> 键类型
     * @param <V> 值类型
     * @return HashMap实例
     */
    public static  <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

}
