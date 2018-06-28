package com.github.hualuomoli.http.jdk;


import com.github.hualuomoli.http.entity.Header;
import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.http.entity.Upload;
import com.github.hualuomoli.http.util.HttpUtils;
import com.github.hualuomoli.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class HttpClient {

    private static final String TAG = "com.github.hualuomoli.http.jdk.HttpClient";



    private static final String KERNEL = "WebKit";

    // 连接超时时间
    private Integer connectTimeout;
    // 从主机读取数据超时时间
    private Integer readTimeout;

    public HttpClient(){
    }

    public HttpClient(Integer connectTimeout, Integer readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }

    /**
     * GET请求
     * @param url 请求URL地址
     * @param headers 请求头
     * @param charset 数据编码
     * @return 响应信息
     * @throws IOException 请求失败
     */
    public HttpResponse get(String url, List<Header> headers, String charset) throws IOException {
        Logger.debug(TAG, "[get] url={}, headers={}, charset={}", url, headers, charset);

        HttpURLConnection conn = null;

        try {
            conn = this.getConnection(url);
            conn.setRequestMethod("GET");
            this.configConnection(conn); // 配置链接信息
            conn.setDoInput(true); // 设置是否读入
            conn.setDoOutput(true); // 设置是否输出
            conn.setUseCaches(false); // 不使用缓存

            // set request header
            Utils.writeHeader(conn, headers);

            // connect
            conn.connect();

            // read data
            // 重定向
            if (conn.getResponseCode() == 302) {
                String location = conn.getHeaderField("Location");
                Logger.debug(TAG, "302Location {}", location);
                return this.get(location, headers, charset);
            }

            return this.readData(conn, charset);
        } finally {
            Utils.disconnect(conn);
        }
        // end
    }

    /**
     * urlencoded请求
     * @param url 请求URL地址
     * @param params 请求参数
     * @param headers 请求头
     * @param charset 数据编码
     * @return 响应信息
     * @throws IOException 请求失败
     */
    public HttpResponse urlencoded(String url, List<Param> params, List<Header> headers, String charset) throws IOException {
        Logger.debug(TAG, "[urlencoded] url={}, params={}, headers={}, charset={}", url, params, headers, charset);

        HttpURLConnection conn = null;
        OutputStream os = null;

        try {
            conn = this.getConnection(url);
            conn.setRequestMethod("POST");
            this.configConnection(conn); // 配置链接信息
            conn.setDoInput(true); // 设置是否读入
            conn.setDoOutput(true); // 设置是否输出
            conn.setUseCaches(false);// 不使用缓存

            // set request header
            Utils.writeHeader(conn, headers);
            Utils.writeContentTypeHeader(conn, "application/x-www-form-urlencoded;charset=" + charset);

            // connect
            conn.connect();

            // flush data
            if (HttpUtils.isNotEmpty(params)) {
                os = conn.getOutputStream();
                Writer writer = new Writer(os);
                writer.write(HttpUtils.parseParam(params, charset));
            }

            return this.readData(conn, charset);
        } finally {
            HttpUtils.close(os);
            Utils.disconnect(conn);
        }
        // end
    }

    /**
     * urlencoded请求
     * @param url 请求URL地址
     * @param content 请求内容
     * @param headers 请求头
     * @param charset 数据编码
     * @return 响应信息
     * @throws IOException 请求失败
     */
    public HttpResponse json(String url, String content, List<Header> headers, String charset) throws IOException {
        Logger.debug(TAG, "[json] url={}, content={}, headers={}, charset={}", url, content, headers, charset);

        HttpURLConnection conn = null;
        OutputStream os = null;

        try {
            conn = this.getConnection(url);
            conn.setRequestMethod("POST");
            this.configConnection(conn); // 配置链接信息
            conn.setDoInput(true); // 设置是否读入
            conn.setDoOutput(true); // 设置是否输出
            conn.setUseCaches(false);// 不使用缓存

            // set request header
            Utils.writeHeader(conn, headers);
            Utils.writeContentTypeHeader(conn, "application/json;charset=" + charset);

            // connect
            conn.connect();

            // flush data
            if (HttpUtils.isNotBlank(content)) {
                os = conn.getOutputStream();
                Writer writer = new Writer(os);
                writer.write(content);
            }

            return this.readData(conn, charset);
        } finally {
            HttpUtils.close(os);
            Utils.disconnect(conn);
        }
        // end
    }

    /**
     * https://blog.csdn.net/lizhengwei1989/article/details/75635261
     * urlencoded请求
     * @param url 请求URL地址
     * @param params 请求参数
     * @param uploads 上传的文件
     * @param headers 请求头
     * @param charset 数据编码
     * @return 响应信息
     * @throws IOException 请求失败
     */
    public HttpResponse upload(String url, List<Param> params, List<Upload> uploads, List<Header> headers, String charset) throws IOException {
        Logger.debug(TAG, "[upload] url={}, params={}, uploads={}, headers={}, charset={}", url, params, uploads, headers, charset);

        HttpURLConnection conn = null;
        OutputStream os = null;
        FileInputStream is = null;

        final String LINE = "\r\n";
        final String QUOTE = "\"";
        final String PREFIX = "--";
        final String SUFFIX = "--";

        final String boundary = new StringBuilder("------")
                .append(KERNEL)
                .append("FormBoundary")
                .append(UUID.randomUUID().toString().replaceAll("[-]", "").substring(0, 16))
                .toString();

        try {
            conn = this.getConnection(url);
            conn.setRequestMethod("POST");
            this.configConnection(conn); // 配置链接信息
            conn.setDoInput(true); // 设置是否读入
            conn.setDoOutput(true); // 设置是否输出
            conn.setUseCaches(false);// 不使用缓存

            // set request header
            Utils.writeHeader(conn, headers);
            Utils.writeContentTypeHeader(conn, "multipart/form-data;boundary=" + boundary);

            // connect
            conn.connect();

            // flush data
            os = conn.getOutputStream();
            Writer writer = new Writer(os);

            // param
            if (HttpUtils.isNotEmpty(params)) {
                // ------WebKitFormBoundarygE8iiBw0RBmQBtzy
                // Content-Disposition: form-data; name="name"
                //
                // 张三
                for(Param param: params) {
                    // 第一行(-- + boundary)
                    writer.write(PREFIX).write(boundary).write(LINE);
                    // 第二行(Content-Disposition)
                    writer.write("Content-Disposition: form-data; name=").write(QUOTE).write(param.getName()).write(QUOTE).write(LINE);
                    // 第三行(空行)
                    writer.write(LINE);
                    // 第四行(值)
                    writer.write(param.getValue()).write(LINE);
                }
            }
            // file
            if(HttpUtils.isNotEmpty(uploads)) {
                // ------WebKitFormBoundarygE8iiBw0RBmQBtzy
                // Content-Disposition: form-data; name="avatar"; filename="0b55b319ebc4b745621a54e5c9fc1e178b8215b2.jpg"
                // Content-Type: image/jpeg
                //
                // 这里是文件内容
                //
                for(Upload upload: uploads) {
                    // 第一行(-- + boundary)
                    writer.write(PREFIX).write(boundary).write(LINE);
                    // 第二行(Content-Disposition)
                    writer.write("Content-Disposition: form-data")
                            // name
                            .write("; name=").write(QUOTE).write(upload.getFieldName()).write(QUOTE)
                            // filename
                            .write("; filename=").write(QUOTE).write(upload.getFilename()).write(QUOTE)
                            // line
                            .write(LINE);
                    // 第三行(Content-Type)
                    writer.write("Content-Type: ").write(upload.getMime()).write(LINE);
                    // 第四行(空行)
                    writer.write(LINE);
                    // 第五行(文件内容)
                    is = new FileInputStream(upload.getFile());
                    HttpUtils.copy(is, os);
                    is.close();
                    writer.write(LINE);
                    // 第六行(空行)
                    writer.write(LINE);
                }
            }

            // end
            writer.write(PREFIX).write(boundary).write(SUFFIX);

            return this.readData(conn, charset);
        } finally {
            HttpUtils.close(is);
            HttpUtils.close(os);
            Utils.disconnect(conn);
        }
        // end
    }

    private class Writer {

        private OutputStream os;

        private Writer(OutputStream os) {
            this.os = os;
        }

        /**
         * 输出数据
         * @param data 数据
         * @return this
         * @throws IOException IO异常
         */
        private Writer write(String data) throws IOException {
            os.write(data.getBytes());
            return this;
        }

        /**
         * 输出数据
         * @param data 数据
         * @param charset 数据编码
         * @return this
         * @throws IOException IO异常
         */
        private Writer write(String data, String charset) throws IOException {
            os.write(data.getBytes(charset));
            return this;
        }

    }

    /**
     * 读取响应数据
     * @param conn 连接
     * @param charset 编码集
     * @return 响应数据
     * @throws IOException 读取异常
     */
    private HttpResponse readData(HttpURLConnection conn, String charset) throws IOException {
        InputStream is = null;

        try {
            int responseCode = conn.getResponseCode();
            Logger.info(TAG, "响应Http状态码={}", responseCode);

            // get response
            if (responseCode == 200) {
                // 成功
                HttpResponse httpResponse = new HttpResponse();

                // header
                List<Header> headers = Utils.readHeader(conn);
                httpResponse.setHeaders(headers);

                // data
                is = conn.getInputStream();
                String result = this.readString(is, charset);
                httpResponse.setResult(result);
                return httpResponse;
            }else if(responseCode == 500) {
                // 错误
                is = conn.getErrorStream();
                String result = this.readString(is, charset);
                throw new IOException(result);
            } else {
                // 其它
                throw new IOException(String.valueOf(responseCode));
            }
        } finally {
            HttpUtils.close(is);
        }
        // end
    }

    /**
     * 读取输入流
     * @param is 输入流
     * @param charset 数据编码
     * @return 数据
     */
    private String readString(InputStream is, String charset) throws IOException {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            HttpUtils.copy(is, os);
            return os.toString(charset);
        }finally {
            HttpUtils.close(os);
        }
        // end
    }

    /**
     * 获取HTTP链接,可考虑使用连接池
     * @param urlString 链接URL
     * @return HTTP链接
     * @throws IOException 链接异常
     */
    private HttpURLConnection getConnection(String urlString) throws IOException {
        // 信任所有的https
        this.initCertification(urlString);

        URL url = new URL(urlString);
        return (HttpURLConnection) url.openConnection();
    }

    /**
     * 初始化证书
     * @param url 请求的URL
     */
    protected void initCertification(String url) {
        if (!url.startsWith("https://")) {
            return;
        }
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[] { new X509TrustManager() {

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

            } }, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 配置连接信息
     */
    private void configConnection(HttpURLConnection conn) {
        if(this.connectTimeout != null) {
            conn.setConnectTimeout(connectTimeout);
        }
        if(this.readTimeout != null) {
            conn.setReadTimeout(readTimeout);
        }
    }

    // end class
}
