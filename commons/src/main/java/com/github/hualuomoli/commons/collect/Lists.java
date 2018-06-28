package com.github.hualuomoli.commons.collect;

import java.util.ArrayList;

public class Lists {

    /**
     * 获取ArrayList
     * @param <T> 泛型
     * @return 数据集合
     */
    public static final <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    /**
     * 获取ArrayList
     * @param datas 数据
     * @param <T> 泛型
     * @return 数据集合
     */
    public static final <T> ArrayList<T> newArrayList(T... datas) {
        ArrayList<T> list = new ArrayList<T>();
        for(T data: datas) {
            list.add(data);
        }
        return list;
    }

}
