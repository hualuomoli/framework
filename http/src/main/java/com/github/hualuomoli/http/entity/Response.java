package com.github.hualuomoli.http.entity;

import java.util.List;

/**
 * 响应结果
 */
public interface Response {

    /**
     * 获取响应字符串
     *
     * @return 响应数据
     */
    String getResult();

    /**
     * 获取响应header
     *
     * @return 响应header
     */
    List<Header> getHeaders();

}
