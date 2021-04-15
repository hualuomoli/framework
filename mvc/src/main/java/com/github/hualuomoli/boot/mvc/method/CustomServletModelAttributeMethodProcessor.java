package com.github.hualuomoli.boot.mvc.method;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

public class CustomServletModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

    private String basePackage;

    public CustomServletModelAttributeMethodProcessor() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return super.supportsParameter(parameter);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        CustomExtendedServletRequestDataBinder customBinder = new CustomExtendedServletRequestDataBinder(binder.getTarget(), binder.getObjectName());
        super.bindRequestParameters(customBinder, request);
    }

}
