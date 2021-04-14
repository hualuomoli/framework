package com.github.hualjomoli.boot.autoconfigure.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractLogbackInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private static boolean ALREADY_RESET = false;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        // 已经重置,不再处理
        if (ALREADY_RESET) {
            return;
        }

        InputStream inputStream = this.inputStream(applicationContext);
        this.reset(applicationContext, inputStream);

        // 设置为已处理
        ALREADY_RESET = true;
    }

    /**
     * logback的输入流
     *
     * @param applicationContext 应用上下文
     * @return 输入流
     */
    protected abstract InputStream inputStream(ConfigurableApplicationContext applicationContext);

    /**
     * 自动关闭输入流
     *
     * @return 如果设置完成后自动关闭, 返回true
     */
    protected boolean autoClose() {
        return true;
    }

    /**
     * 重置Logback
     *
     * @param applicationContext 应用上下文
     * @param is                 输入流
     */
    protected void reset(ConfigurableApplicationContext applicationContext, InputStream is) {
        try {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            context.reset();

            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            configurator.doConfigure(is);

            StatusPrinter.printInCaseOfErrorsOrWarnings(context);
        } catch (JoranException e) {
            throw new RuntimeException(e);
        } finally {
            // 自动关闭
            if (this.autoClose()) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // end
    }


}
