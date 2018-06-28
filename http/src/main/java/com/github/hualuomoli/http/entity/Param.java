package com.github.hualuomoli.http.entity;

/**
 * 请求参数
 */
public class Param {

    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

    public Param(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Param{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
