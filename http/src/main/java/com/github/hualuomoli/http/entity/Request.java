package com.github.hualuomoli.http.entity;

import com.github.hualuomoli.http.util.ParamUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 请求
 */
public class Request {

    private String charset;
    private final List<Header> headers = new ArrayList<Header>();
    private final List<Param> params = new ArrayList<Param>();
    private String content;
    private final List<Upload> uploads = new ArrayList<Upload>();

    public Request() {
        this("UTF-8");
    }

    public Request(String charset) {
        super();
        this.charset = charset;
    }

    /**
     * 添加请求头
     *
     * @param name  请求头名称
     * @param value 请求头值
     * @return this
     */
    public Request header(String name, String value) {
        headers.add(new Header(name, value));
        return this;
    }

    /**
     * 添加请求头
     *
     * @param name   请求头名称
     * @param values 请求头值
     * @return this
     */
    public Request header(String name, String... values) {
        this.headers.add(new Header(name, values));
        return this;
    }

    /**
     * 添加请求头
     *
     * @param headers  请求头信息
     * @return this
     */
    public Request header(Map<String, String> headers) {
        if(headers == null || headers.size() == 0) {
            return this;
        }
        Set<String> keys = headers.keySet();
        for (String key : keys) {
            this.headers.add(new Header(key, headers.get(key)));
        }
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param name  请求参数名称
     * @param value 请求参数值
     * @return this
     */
    public Request param(String name, String value) {
        this.params.add(new Param(name, value));
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param params 参数
     * @return this
     */
    public Request param(Map<String, String> params) {
        if(params == null || params.size() == 0) {
            return this;
        }
        Set<String> keys = params.keySet();
        for (String key : keys) {
            this.params.add(new Param(key, params.get(key)));
        }
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param entity 实体类
     * @return this
     */
    public Request param(Object entity) {
        if(entity == null) {
            return this;
        }
        
        this.params.addAll(ParamUtils.parse(entity));
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param entity                   实体类
     * @param dateAnnotationClass      时间注解类
     * @param dateAnnotationMethodName 格式化字符串定义方法
     * @return this
     */
    public Request param(Object entity, Class<? extends Annotation> dateAnnotationClass, String dateAnnotationMethodName) {
        this.params.addAll(ParamUtils.parse(entity, dateAnnotationClass, dateAnnotationMethodName));
        return this;
    }

    /**
     * 添加请求内容
     *
     * @param content 请求内容
     * @return this
     */
    public Request content(String content) {
        this.content = content;
        return this;
    }

    /**
     * 添加请求文件
     *
     * @param fieldName 表单域名称
     * @param file 请求文件
     * @return this
     */
    public Request upload(String fieldName,  File file) {
        Upload upload = Upload.newInstance(fieldName, file);
        this.uploads.add(upload);
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public List<Header> getHeaders() {
        return new ArrayList<Header>(this.headers);
    }

    public List<Param> getParams() {
        return new ArrayList<Param>(this.params);
    }

    public String getContent() {
        return content;
    }

    public List<Upload> getUploads() {
        return new ArrayList<Upload>(uploads);
    }
}
