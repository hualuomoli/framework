package com.github.hualuomoli.http;

import com.github.hualuomoli.http.entity.Request;
import com.github.hualuomoli.http.entity.Response;

import java.io.IOException;

/**
 * HTTP网络
 */
public interface Http  {

    /**
     * GET获取
     * @param url URL地址
     * @param request 请求
     * @return 执行结果
     * @throws IOException 执行失败
     */
    Response get(String url, Request request) throws IOException;

    /**
     * 表单提交
     *
     * @param url URL地址
     * @param request 请求
     * @return 执行结果
     * @throws IOException 执行失败
     */
    Response urlencoded(String url, Request request)  throws IOException;

    /**
     * JSON提交
     *
     * @param url URL地址
     * @param request 请求
     * @return 执行结果
     * @throws IOException 执行失败
     */
    Response json(String url, Request request)  throws IOException;

    /**
     * 文件上传
     *
     * @param url URL地址
     * @param request 请求
     * @return 执行结果
     * @throws IOException 执行失败
     */
    Response upload(String url, Request request)  throws IOException;

}
