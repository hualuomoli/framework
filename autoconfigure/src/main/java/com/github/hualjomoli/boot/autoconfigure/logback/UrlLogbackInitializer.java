package com.github.hualjomoli.boot.autoconfigure.logback;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.io.InputStream;

public abstract class UrlLogbackInitializer extends AbstractLogbackInitializer {

    @Override
    public int getOrder() {
        return -3_000_000;
    }

    @Override
    protected InputStream inputStream(ConfigurableApplicationContext applicationContext) {

        String urlString = this.urlString();
        try {
            System.out.println(String.format("reset logback with url %s", urlString));
            UrlResource resource = new UrlResource(urlString());
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // end
    }

    /**
     * URL地址
     *
     * @return URL地址
     */
    protected abstract String urlString();

}
