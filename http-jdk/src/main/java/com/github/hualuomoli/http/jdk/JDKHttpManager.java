package com.github.hualuomoli.http.jdk;

import com.github.hualuomoli.http.Http;
import com.github.hualuomoli.http.HttpManager;
import com.github.hualuomoli.http.entity.Header;
import com.github.hualuomoli.http.entity.Request;
import com.github.hualuomoli.http.entity.Response;
import com.github.hualuomoli.http.util.HttpUtils;

import java.io.IOException;
import java.util.List;

/**
 * JDK HTTP 客户端
 */
public class JDKHttpManager implements HttpManager.IHttpManager {


    @Override
    public Http newClient() {
        return new JdkHttp();
    }

    @Override
    public Http newClient(int connectTimeout, int readTimeout, int writeTimeout) {
        return new JdkHttp(connectTimeout, readTimeout);
    }

    private class JdkHttp implements Http {

        private HttpClient client;

        private JdkHttp() {
            this.client = new HttpClient();
        }

        private JdkHttp(int connectTimeout, int readTimeout) {
            this.client = new HttpClient(connectTimeout, readTimeout);
        }

        @Override
        public Response get(String url, Request request) throws IOException {
            String queryString = HttpUtils.parseParam(request.getParams(), request.getCharset());
            if(HttpUtils.isNotBlank(queryString)) {
                if(url.indexOf("?") > 0) {
                    url = url + "&" + queryString;
                } else {
                    url = url + "?" + queryString;
                }
            }
            HttpResponse httpResponse = this.client.get(url, request.getHeaders(), request.getCharset());
            return this.parse(httpResponse);
        }

        @Override
        public Response urlencoded(String url, Request request) throws IOException {
            HttpResponse httpResponse = this.client.urlencoded(url, request.getParams(), request.getHeaders(), request.getCharset());
            return this.parse(httpResponse);
        }

        @Override
        public Response json(String url, Request request) throws IOException {
            HttpResponse httpResponse = this.client.json(url, request.getContent(), request.getHeaders(), request.getCharset());
            return this.parse(httpResponse);
        }

        @Override
        public Response upload(String url, Request request) throws IOException {
            HttpResponse httpResponse = this.client.upload(url, request.getParams(), request.getUploads(), request.getHeaders(), request.getCharset());
            return this.parse(httpResponse);
        }

        /**
         * 解析成Response
         * @param response 网络Response
         * @return 数据Response
         */
        private Response parse(HttpResponse response) {
            return new JdkResponse(response.getResult(), response.getHeaders());
        }

    }

    private class JdkResponse implements Response {

        private String result;
        private List<Header> headers;

        private JdkResponse(String result, List<Header> headers) {
            this.result = result;
            this.headers = headers;
        }

        @Override
        public String getResult() {
            return result;
        }

        @Override
        public List<Header> getHeaders() {
            return headers;
        }
        // end JdkResponse
    }

    // end class
}
