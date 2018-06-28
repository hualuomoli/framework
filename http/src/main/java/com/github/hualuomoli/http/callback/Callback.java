package com.github.hualuomoli.http.callback;

import com.github.hualuomoli.http.entity.Header;

import java.io.IOException;
import java.util.List;

/**
 * 回掉
 */
public interface Callback {

    /**
     * 执行失败
     * @param e 执行错误
     */
    void onError(IOException e);

    /**
     * 调用成功
     * @param result 响应数据
     * @param responseHeaders 响应header
     */
    void onSuccess(String result, List<Header> responseHeaders);

}
