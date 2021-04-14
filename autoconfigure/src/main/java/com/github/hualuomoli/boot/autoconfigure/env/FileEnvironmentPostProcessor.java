package com.github.hualuomoli.boot.autoconfigure.env;

import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.FileSystemResource;

public abstract class FileEnvironmentPostProcessor extends AbstractEnvironmentPostProcessor implements Ordered {

    @Override
    public int getOrder() {
        return -1_000_000_000;
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String filename = this.filename();
        this.add(environment, filename);
    }

    /**
     * 文件路径
     *
     * @return 文件绝对路径
     */
    protected abstract String filename();

    /**
     * 添加到资源库
     *
     * @param environment 运行环境
     * @param filename    文件路径
     */
    private void add(ConfigurableEnvironment environment, String filename) {
        FileSystemResource resource = new FileSystemResource(filename);
        this.addPropertySources(environment, resource);
    }

}
