package com.github.hualuomoli.http.jdk;

import com.github.hualuomoli.commons.collect.Lists;
import com.github.hualuomoli.http.entity.Header;
import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Utils {

    private static final String TAG = "JDK工具";

    public static final String EMPTY = "";
    public static final int BUFFER_SIZE = 1024 * 2;
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * 写入请求header
     * @param conn 连接
     * @param mime 请求Content-Type的值
     */
    public static void writeContentTypeHeader(HttpURLConnection conn, String mime) {
        Utils.writeHeader(conn, CONTENT_TYPE, mime);
    }

    /**
     * 写入请求header
     * @param conn 连接
     * @param headerName 请求Header名
     * @param headerValue 请求header值
     */
    public static void writeHeader(HttpURLConnection conn, String headerName, String headerValue) {
        conn.addRequestProperty(headerName, headerValue);
    }

    /**
     * 写入请求header
     * @param conn 连接
     * @param headers 请求header
     */
    public static void writeHeader(HttpURLConnection conn, List<Header> headers) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (Header header : headers) {
            String[] values = header.getValue();
            for (String value : values) {
                conn.addRequestProperty(header.getName(), value);
            }
        }
    }

    /**
     * 读取响应Header
     * @param conn 连接
     * @return 响应header
     */
    public static List<Header> readHeader(HttpURLConnection conn) {
        List<Header> headers = Lists.newArrayList();

        Map<String, List<String>> resHeaderMap = conn.getHeaderFields();
        if(resHeaderMap == null || resHeaderMap.size() == 0) {
            return headers;
        }

        for (String name : resHeaderMap.keySet()) {
            headers.add(new Header(name, resHeaderMap.get(name).toArray(new String[] {})));
        }
        return headers;
    }

    /**
     * 关闭连接
     * @param conn URL连接
     */
    public static void disconnect(HttpURLConnection conn) {
        if(conn == null) {
            return;
        }
        try{
            conn.disconnect();
        }catch(Exception e) {
            Logger.warn(TAG, "关闭连接失败", e);
        }
        // end
    }

}
