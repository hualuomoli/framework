package com.github.hualuomoli.boot.autoconfigure.env;

import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class ClasspathEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor implements Ordered {

    @Override
    public int getOrder() {
        return 1_000_000_000;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String[] activeProfiles = environment.getActiveProfiles();
        String resourceName = this.resourceName();

        // 加载激活环境
        for (int i = activeProfiles.length - 1; i >= 0; i--) {
            this.add(environment, String.format("%s-%s", resourceName, activeProfiles[i]));
        }

        // 加载默认 profile
        this.add(environment, resourceName);
    }

    /**
     * 配置资源名称 (application = application.yml/application.properties)
     *
     * @return 资源名称
     */
    protected abstract String resourceName();

    /**
     * 添加配置资源
     *
     * @param environment  运行环境
     * @param resourceName 资源名称
     */
    private void add(ConfigurableEnvironment environment, String resourceName) {
        Resource resource = null;

        // load from yml
        resource = new ClassPathResource(String.format("%s.yml", resourceName));
        if (resource.exists()) {
            System.out.println(String.format("load resource %s.yml", resourceName));
            this.addPropertySources(environment, resource);
            return;
        }

        // load from properties
        resource = new ClassPathResource(String.format("%s.properties", resourceName));
        if (resource.exists()) {
            System.out.println(String.format("load resource %s.properties", resourceName));
            this.addPropertySources(environment, resource);
            return;
        }

        // end
    }

}
