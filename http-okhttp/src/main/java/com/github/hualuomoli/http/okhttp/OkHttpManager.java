package com.github.hualuomoli.http.okhttp;

import com.github.hualuomoli.http.Http;
import com.github.hualuomoli.http.HttpManager;

/**
 * OK HTTP 客户端
 */
public class OkHttpManager implements HttpManager.IHttpManager {

    @Override
    public Http newClient() {
        return new OkHttp();
    }

    @Override
    public Http newClient(int connectTimeout, int readTimeout, int writeTimeout) {
        return new OkHttp(connectTimeout, readTimeout, writeTimeout);
    }

}
