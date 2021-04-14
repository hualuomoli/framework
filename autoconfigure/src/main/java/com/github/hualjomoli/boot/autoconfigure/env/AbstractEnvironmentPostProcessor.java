package com.github.hualjomoli.boot.autoconfigure.env;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

abstract class AbstractEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /**
     * 添加配置资源
     *
     * @param environment 运行环境
     * @param resource    资源
     */
    protected void addPropertySources(ConfigurableEnvironment environment, Resource resource) {

        String resourceName = null;
        Properties properties = null;

        if (ClassPathResource.class.isAssignableFrom(resource.getClass())) {
            resourceName = ((ClassPathResource) resource).getPath();
            properties = this.load(resource, resourceName);
        } else if (FileSystemResource.class.isAssignableFrom(resource.getClass())) {
            resourceName = ((FileSystemResource) resource).getFile().getName();
            properties = this.load(resource, resourceName);
        } else {
            throw new RuntimeException(String.format("can not handle resource %s", resource.getClass().getName()));
        }

        // add last
        environment.getPropertySources().addLast(new PropertiesPropertySource(resourceName, properties));
    }

    /**
     * Load Resource
     *
     * @param resource     Resource to be loaded
     * @param resourceName resource name
     * @return Resource Properties
     */
    private Properties load(Resource resource, String resourceName) {

        // yml
        if (resourceName.endsWith(".yml")) {
            return this.loadYmlResource(resource);
        }

        // properties
        if (resourceName.endsWith(".properties")) {
            return this.loadPropertiesResource(resource);
        }

        throw new RuntimeException(String.format("can not handle resource %s", resourceName));
    }

    /**
     * Load YML Resource
     *
     * @param resource Resource to be loaded
     * @return YML Resource Properties
     */
    private Properties loadYmlResource(Resource resource) {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(resource);
        return yaml.getObject();
    }

    /**
     * Load Properties Resource
     *
     * @param resource Resource to be loaded
     * @return YML Resource Properties
     */
    private Properties loadPropertiesResource(Resource resource) {
        Properties properties = new Properties();
        try {
            properties.load(resource.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
