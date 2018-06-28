package com.github.hualuomoli.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具
   */
public class CollectionUtils {

  /**
   * 定长截取集合
   * @param list 集合
   * @param size 截取的长度
   * @return 截取后的集合
   */
  public static <T> List<List<T>> split(List<T> list, Integer size) {
    List<List<T>> splitList = new ArrayList<List<T>>();

    if (list == null || list.size() == 0) {
      return splitList;
    }

    if (size == 0 || size < 0) {
      return splitList;
    }

    Integer count = (list.size() + size - 1) / size;
    for (int i = 0; i < count; i++) {
      splitList.add(fetch(list, size * i + 1, size));
    }

    return splitList;
  }

  /**
   * 截取集合中特定位置长度的数据
   * @param list 数据集合
   * @param start 开始位置,从1开始
   * @param size 数据长度
   * @return 截取后的数据长度
   */
  public static <T> List<T> fetch(List<T> list, Integer start, Integer size) {
    List<T> fetchList = new ArrayList<T>();
    start = start - 1; // 集合从零开始
    Integer end = start + size;
    if (end > list.size()) {
      end = list.size();
    }
    for (int i = start; i < end; i++) {
      fetchList.add(list.get(i));
    }
    return fetchList;
  }

}
