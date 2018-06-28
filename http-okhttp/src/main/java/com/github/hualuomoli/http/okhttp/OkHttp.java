package com.github.hualuomoli.http.okhttp;

import com.github.hualuomoli.commons.collect.Lists;
import com.github.hualuomoli.http.Http;
import com.github.hualuomoli.http.entity.Header;
import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.http.entity.Request;
import com.github.hualuomoli.http.entity.Response;
import com.github.hualuomoli.http.entity.Upload;
import com.github.hualuomoli.http.util.HttpUtils;
import com.github.hualuomoli.logger.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * OK HTTP
 */
public class OkHttp implements Http {

    private static final String TAG = "com.github.hualuomoli.http.okhttp.OkHttp";

    private static final int CODE_SUCCESS = 200;

    protected final OkHttpClient client;
    protected final OkHttpClient.Builder builder;

    public OkHttp(){
        client = new OkHttpClient();
        builder = client.newBuilder();
    }

    public OkHttp(Integer connectTimeout, Integer readTimeout, Integer writeTimeout) {
        client = new OkHttpClient();
        builder = client.newBuilder();

        // 设置连接信息
        builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
    }

    @Override
    public Response get(String url, Request request) throws IOException {
        List<Header> headers = request.getHeaders();
        List<Param> params = request.getParams();
        String charset = request.getCharset();
        Logger.debug(TAG, "[get] url={}, headers={}, params={}, charset={}", url, headers, params, charset);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        // header
        for (Header header : headers) {
            String[] values = header.getValue();
            for (String value : values) {
                builder.header(header.getName(), value);
            }
        }

        // param
        final StringBuilder buffer = new StringBuilder();
        for (Param param : params) {
            buffer.append("&").append(param.getName()).append("=").append(HttpUtils.encoded(param.getValue(), charset));
        }

        okhttp3.Request req = builder.url(new StringBuilder(url).append(buffer).toString()).build();
        return this.execute(req);
    }

    @Override
    public Response urlencoded(String url, Request request) throws IOException {
        List<Header> headers = request.getHeaders();
        List<Param> params = request.getParams();
        String charset = request.getCharset();
        Logger.debug(TAG, "[urlencoded] url={}, headers={}, params={}, charset={}", url, headers, params, charset);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        // header
        for (Header header : headers) {
            String[] values = header.getValue();
            for (String value : values) {
                builder.header(header.getName(), value);
            }
        }

        // param
        FormBody.Builder formBuilder = new FormBody.Builder(Charset.forName(charset));
        for (Param param : params) {
            formBuilder.add(param.getName(),param.getValue());
        }

        okhttp3.Request req = builder.url(url).post(formBuilder.build()).build();
        return this.execute(req);
    }

    @Override
    public Response json(String url, Request request) throws IOException {
        List<Header> headers = request.getHeaders();
        String content = request.getContent();
        String charset = request.getCharset();
        Logger.debug(TAG, "[json] url={}, headers={}, content={}, charset={}", url, headers, content, charset);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        // header
        for (Header header : headers) {
            String[] values = header.getValue();
            for (String value : values) {
                builder.header(header.getName(), value);
            }
        }

        MediaType JSON = MediaType.parse("application/json; charset=" + charset);
        RequestBody body = RequestBody.create(JSON, content);

        okhttp3.Request req = builder.url(url).post(body).build();
        return this.execute(req);
    }

    @Override
    public Response upload(String url, Request request) throws IOException {
        List<Header> headers = request.getHeaders();
        List<Param> params = request.getParams();
        List<Upload> uploads = request.getUploads();

        Logger.debug(TAG, "[upload] url={}, headers={}, params={}", url, headers, params);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        // header
        for (Header header : headers) {
            String[] values = header.getValue();
            for (String value : values) {
                builder.header(header.getName(), value);
            }
        }

        // param
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Param param : params) {
            bodyBuilder.addFormDataPart(param.getName(), param.getValue());
        }

        // file
        for (Upload upload : uploads) {
            Headers uploadHeaders = Headers.of("Content-Disposition", "form-data; name=\"" + upload.getFieldName() + "\";filename=\"" + upload.getFilename() + "\";filesize=" + upload.getFile().length());

            RequestBody uploadRequestBody = RequestBody.create(MultipartBody.FORM, upload.getFile());
            bodyBuilder.addPart(uploadHeaders, uploadRequestBody);
        }

        okhttp3.Request req = builder.url(url).post(bodyBuilder.build()).build();
        return this.execute(req);
    }

    /**
     * 执行HTTP请求
     * @param req 请求
     * @return 响应
     * @throws IOException IO异常
     */
    private Response execute(okhttp3.Request req) throws IOException {
        okhttp3.Response res = client.newCall(req).execute();
        if (res.code() != CODE_SUCCESS) {
            throw new IOException(res.message());
        }
        return this.newResponse(res);
        // end
    }

    /**
     * 获取响应
     *
     * @param res 响应信息
     * @return 需要的响应
     * @throws IOException
     */
    private Response newResponse(final okhttp3.Response res) throws IOException {
        final String result = res.body().string();
        final List<Header> headerList = Lists.newArrayList();
        Headers headers = res.headers();
        Set<String> names = headers.names();
        for(String headerName: names) {
            List<String> headerValues = headers.values(headerName);
            Header header = new Header(headerName, headerValues.toArray(new String[]{}));
            headerList.add(header);
        }
        return new Response() {
            @Override
            public String getResult() {
                return result;
            }

            @Override
            public List<Header> getHeaders() {
                return headerList;
            }
        };
        // end method
    }

}
