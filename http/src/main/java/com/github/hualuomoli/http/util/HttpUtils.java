package com.github.hualuomoli.http.util;

import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;

public class HttpUtils {

    private static final String TAG = "Http工具";

    public static final String EMPTY = "";
    public static final int BUFFER_SIZE = 1024 * 2;

    /**
     * 转换请求参数
     * @param params 请求参数
     * @param charset 编码集
     * @return 请求参数字符串
     */
    public static String parseParam(List<Param> params, String charset) {
        if(params == null || params.size() == 0) {
            return EMPTY;
        }
        StringBuilder buffer = new StringBuilder();
        for(Param param: params) {
            buffer.append("&").append(param.getName()).append("=").append(HttpUtils.encoded(param.getValue(), charset));
        }
        return buffer.toString().substring(1);
    }

    /**
     * 复制
     * @param is 输入流
     * @param os 输出流
     * @throws IOException IO异常
     */
    public static void copy(InputStream is, OutputStream os) throws IOException {
        HttpUtils.copy(is, os, BUFFER_SIZE);
    }

    /**
     * 复制
     * @param is 输入流
     * @param os 输出流
     * @param bufferSize 缓存大小
     * @throws IOException IO异常
     */
    public static void copy(InputStream is, OutputStream os, int bufferSize) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int count = -1;
        while((count = is.read(buffer)) > 0) {
            os.write(buffer, 0, count);
        }
        // end
    }

    /**
     * 关闭输入流
     * @param is 输入流
     */
    public static void close(InputStream is) {
        if(is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            Logger.warn(TAG, "关闭输入流失败", e);
        }
        // end
    }

    /**
     * 关闭输出流
     * @param os 输出流
     */
    public static void close(OutputStream os) {
        if(os == null) {
            return;
        }
        try {
            os.close();
        } catch (IOException e) {
            Logger.warn(TAG, "关闭输出流失败", e);
        }
        // end
    }

    /**
     * 编码
     * @param value 需要编码的内容
     * @param charset 数据编码
     * @return 编码后的值
     */
    public static String encoded(String value, String charset) {
        try {
            return URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            return value;
        }
        // end
    }

    /**
     * 非空
     * @param value 字符串值
     * @return 是否非空
     */
    public static boolean isNotBlank(String value) {
        return value != null && value.length() > 0;
    }

    /**
     * 非空集合
     * @param collection 集合
     * @return 集合是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && collection.size() > 0;
    }

}
