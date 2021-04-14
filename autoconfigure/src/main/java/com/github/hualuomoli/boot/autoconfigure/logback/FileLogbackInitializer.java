package com.github.hualuomoli.boot.autoconfigure.logback;

import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public abstract class FileLogbackInitializer extends AbstractLogbackInitializer {

    @Override
    public int getOrder() {
        return -2_000_000;
    }

    @Override
    protected InputStream inputStream(ConfigurableApplicationContext applicationContext) {
        String filename = this.filename();

        try {
            System.out.println(String.format("reset logback with file %s", filename));
            return new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("can not found file %s", filename));
        }

    }

    /**
     * 文件名
     *
     * @return logback文件绝对路径
     */
    protected abstract String filename();

}
