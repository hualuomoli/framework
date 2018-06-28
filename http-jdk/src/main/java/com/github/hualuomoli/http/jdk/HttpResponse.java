package com.github.hualuomoli.http.jdk;

import com.github.hualuomoli.http.entity.Header;

import java.util.List;

public class HttpResponse {

    private String result;
    private List<Header> headers;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "result='" + result + '\'' +
                ", headers=" + headers +
                '}';
    }

}
