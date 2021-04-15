package com.github.hualuomoli.boot.mvc.runner;

import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import com.github.hualuomoli.boot.mvc.method.CustomExtendedServletRequestDataBinder;
import com.github.hualuomoli.boot.mvc.method.CustomServletModelAttributeMethodProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

@Component(value = "com.github.hualuomoli.boot.mvc.runner.CustomMvcRunner")
public class CustomMvcRunner implements ApplicationRunner {

    @Autowired
    private RequestMappingHandlerAdapter adapter;

    @Autowired
    private CustomHandler customHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 定义处理器
        CustomExtendedServletRequestDataBinder.setCustomHandler(customHandler);

        // 添加自定义参数解析器
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>(adapter.getArgumentResolvers());
        argumentResolvers.add(0, new CustomServletModelAttributeMethodProcessor());
        adapter.setArgumentResolvers(argumentResolvers);

    }

}
