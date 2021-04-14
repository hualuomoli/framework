package com.github.hualuomoli.boot.autoconfigure.logback;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public abstract class ClasspathLogbackInitializer extends AbstractLogbackInitializer {

    @Override
    public int getOrder() {
        return -1_000_000;
    }

    @Override
    protected InputStream inputStream(ConfigurableApplicationContext applicationContext) {

        // logback名称
        String logbackName = this.logbackName();

        // 加载的资源
        ClassPathResource resource = this.resource(applicationContext, logbackName);
        if (resource == null) {
            throw new RuntimeException(String.format("can not found logback %s", logbackName));
        }

        try {
            System.out.println(String.format("reset logback with classpath file %s ", resource.getPath()));
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(String.format("can not found file %s", logbackName));
        }

    }

    private ClassPathResource resource(ConfigurableApplicationContext applicationContext, String logbackName) {
        ClassPathResource resource = null;

        // 激活环境
        String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
        for (int i = activeProfiles.length - 1; i >= 0; i--) {
            resource = new ClassPathResource(String.format("%s-%s.xml", logbackName, activeProfiles[i]));
            if (resource.exists()) {
                return resource;
            }
        }

        // 默认
        resource = new ClassPathResource(String.format("%s.xml", logbackName));
        if (resource.exists()) {
            return resource;
        }

        return null;
    }

    /**
     * 文件名
     *
     * @return logback的名称
     */
    protected abstract String logbackName();

}
