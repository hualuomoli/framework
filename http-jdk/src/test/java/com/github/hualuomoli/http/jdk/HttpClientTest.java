package com.github.hualuomoli.http.jdk;

import com.github.hualuomoli.commons.collect.Lists;
import com.github.hualuomoli.http.entity.Param;
import com.github.hualuomoli.http.entity.Upload;
import com.github.hualuomoli.logger.Level;
import com.github.hualuomoli.logger.Logger;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class HttpClientTest {

    private static final String TAG = "HttpClientTest";

    private static final String CHARSET = "UTF-8";
    private HttpClient client;

    @Before
    public void before() {
        client = new HttpClient();
        Logger.setLogger(new SystemLogger());
        Logger.setLevel(Level.ALL);
    }

    @Test
    public void get() throws Exception {
        String url = "http://localhost/get";
        HttpResponse response = client.get(url, null, CHARSET);
        Logger.debug(TAG, "[get] response={}", response);
    }

    @Test
    public void urlencoded() throws Exception {

    }

    @Test
    public void json() throws Exception {

    }

    @Test
    public void upload() throws Exception {
        List<Param> params = Lists.newArrayList();
        params.add(new Param("name", "hualuomoli"));
        params.add(new Param("age", "18"));
        params.add(new Param("gender", "MALE"));
        List<Upload> uploads = Lists.newArrayList();
        uploads.add(Upload.newInstance("avatar", new File("C:\\Users\\admin\\Pictures", "0b55b319ebc4b745621a54e5c9fc1e178b8215b2.jpg")));
        uploads.add(Upload.newInstance("photo", new File("C:\\Users\\admin\\Pictures", "18779658604142399.gif")));
        uploads.add(Upload.newInstance("images", new File("C:\\Users\\admin\\Pictures", "1.png")));
        uploads.add(Upload.newInstance("images", new File("C:\\Users\\admin\\Pictures", "2.png")));
        HttpResponse response = client.upload("http://localhost/demo/file/upload", params, uploads, null, CHARSET);
        Logger.debug(TAG, "[upload] response={}", response);
    }

}