package com.github.hualuomoli.demo.async;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hualuomoli.async.dealer.Dealer;
import com.github.hualuomoli.async.executor.Executor;
import com.github.hualuomoli.async.util.AsyncUtils;
import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.http.Http;
import com.github.hualuomoli.http.HttpManager;
import com.github.hualuomoli.http.entity.Request;
import com.github.hualuomoli.http.entity.Response;
import com.github.hualuomoli.http.jdk.JDKHttpManager;
import com.github.hualuomoli.http.okhttp.OkHttpManager;
import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.security.config.Config;

public class AsyncActivity extends AppCompatActivity {

    private static final String TAG = "com.github.hualuomoli.demo.async.AsyncActivity";

    private static final String URL = "http://www.sina.com.cn";

    private static final String HTTP_NAME_OK_HTTP = "okhttp";
    private static final String HTTP_NAME_JDK_HTTP = "jdkhttp";

    private TextView asyncTextView;
    private Button asyncOkHttpButton;
    private Button asyncJdkHttpButton;
    private WebView asyncWebView;

    private AsyncActivity _self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        _self = this;

        // 注册
        HttpManager.addManager(HTTP_NAME_OK_HTTP, new OkHttpManager());
        HttpManager.addManager(HTTP_NAME_JDK_HTTP, new JDKHttpManager());

        asyncTextView = (TextView) findViewById(R.id.async_tv);
        asyncOkHttpButton = (Button) findViewById(R.id.async_http_okhttp);
        asyncJdkHttpButton = (Button) findViewById(R.id.async_http_jdk);
        asyncWebView = (WebView) findViewById(R.id.async_view);

        // OKHttp
        asyncOkHttpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dealer dealer = AsyncUtils.newDealer("异步执行网络请求");
                dealer.execute(new Executor<String, Exception>() {

                    @Override
                    public String deal(String name) throws Exception {
                        Http client = HttpManager.newClient(HTTP_NAME_OK_HTTP);
                        Response response = client.get(URL, new Request());
                        return response.getResult();
                    }

                    @Override
                    public void onSuccess(String name, String html) {
                        Logger.info(TAG, "html={}", html);
                        Toast.makeText(_self, "访问成功" , Toast.LENGTH_LONG).show();
                        _self.showHtml(html);
                    }

                    @Override
                    public void onError(String name, Exception e) {
                        Logger.info(TAG, e.getMessage(), e);
                        Toast.makeText(_self, "访问失败，错误信息未=" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                });
            }
        });


        // JDK
        asyncJdkHttpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dealer dealer = AsyncUtils.newDealer("异步执行网络请求");
                dealer.execute(new Executor<String, Throwable>() {

                    @Override
                    public String deal(String name) throws Throwable {
                        Http client = HttpManager.newClient(HTTP_NAME_JDK_HTTP);
                        Response response = client.get(URL, new Request());
                        return response.getResult();
                    }

                    @Override
                    public void onSuccess(String name, String html) {
                        Logger.info(TAG, "html={}", html);
                        Toast.makeText(_self, "访问成功" , Toast.LENGTH_LONG).show();
                        _self.showHtml(html);
                    }

                    @Override
                    public void onError(String name, Throwable t) {
                        Logger.info(TAG, t.getMessage(), t);
                        Toast.makeText(_self, "访问失败，错误信息未=" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }


                });
            }
        });
    }

    private void showHtml(String html) {
        WebSettings settings = asyncWebView.getSettings();
        // 允许JS
        settings.setJavaScriptEnabled(true);
        //设置默认为utf-8
        settings.setDefaultTextEncodingName(Config.CHARSET);
        // 加载数据
        asyncWebView.loadData(html, "text/html; charset=" + Config.CHARSET, null);
    }

}
