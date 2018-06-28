package com.github.hualuomoli.http.entity;

import java.util.Arrays;

/**
 * Header信息
 */
public class Header {

    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String[] value;

    public Header(String name, String value) {
        this.name = name;
        this.value = new String[]{value};
    }

    public Header(String name, String[] value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Header{" +
                "name='" + name + '\'' +
                ", value=" + Arrays.toString(value) +
                '}';
    }

}
