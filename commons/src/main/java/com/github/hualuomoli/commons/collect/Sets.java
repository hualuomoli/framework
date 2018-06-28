package com.github.hualuomoli.commons.collect;

import java.util.HashSet;

public class Sets {

    /**
     * 获取HashSet
     * @param <T> 泛型
     * @return 数据集合
     */
    public static final <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    /**
     * 获取HashSet
     * @param datas 数据
     * @param <T> 泛型
     * @return 数据集合
     */
    public static final <T> HashSet<T> newHashSet(T... datas) {
        HashSet<T> sets = new HashSet<T>();
        for(T data: datas) {
            sets.add(data);
        }
        return sets;
    }

}
