package com.github.hualuomoli.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP管理器
 */
public class HttpManager {

    private static final Map<String, IHttpManager> MAP = new HashMap<String, IHttpManager>();
    private static IHttpManager DEFAULT_MANAGER = null;

    /**
     * 添加管理器
     * @param name 管理器名称
     * @param manager 管理器
     */
    public static void addManager(String name, IHttpManager manager) {
        MAP.put(name, manager);
        if (DEFAULT_MANAGER == null) {
            DEFAULT_MANAGER = manager;
        }
    }

    /**
     * 获取客户端
     * @return 客户端
     */
    public static final Http newClient() {
        if (MAP == null || MAP.size() == 0) {
            throw new RuntimeException("please configure Http Manager at least one.");
        }
        return DEFAULT_MANAGER.newClient();
    }

    /**
     * 获取客户端
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @param writeTimeout 写入数据超时时间
     * @return 客户端
     */
    public static final Http newClient(int connectTimeout, int readTimeout, int writeTimeout) {
        if (MAP == null || MAP.size() == 0) {
            throw new RuntimeException("please configure Http Manager at least one.");
        }
        return DEFAULT_MANAGER.newClient(connectTimeout, readTimeout, writeTimeout);
    }

    /**
     * 获取客户端
     * @param name 管理器名称
     * @return 客户端
     */
    public static final Http newClient(String name) {
        if (MAP == null || MAP.size() == 0) {
            throw new RuntimeException("please configure Http Manager at least one.");
        }

        IHttpManager manager = MAP.get(name);
        if (manager == null) {
            throw new RuntimeException("please configure Http Manager which name is " + name);
        }

        return manager.newClient();
    }

    /**
     * 获取客户端
     * @param name 管理器名称
     * @param connectTimeout 连接超时时间
     * @param readTimeout 读取数据超时时间
     * @param writeTimeout 写入数据超时时间
     * @return 客户端
     */
    public static final Http newClient(String name, int connectTimeout, int readTimeout, int writeTimeout) {
        if (MAP == null || MAP.size() == 0) {
            throw new RuntimeException("please configure Http Manager at least one.");
        }

        IHttpManager manager = MAP.get(name);
        if (manager == null) {
            throw new RuntimeException("please configure Http Manager which name is " + name);
        }

        return manager.newClient(connectTimeout, readTimeout, writeTimeout);
    }

    public interface IHttpManager {

        /**
         * 获取客户端
         * @return HTTP客户端
         */
        Http newClient();

        /**
         * 获取客户端
         * @param connectTimeout 连接超时时间
         * @param readTimeout 读取数据超时时间
         * @param writeTimeout 写入数据超时时间
         * @return HTTP客户端
         */
        Http newClient(int connectTimeout, int readTimeout, int writeTimeout);

    }

}
