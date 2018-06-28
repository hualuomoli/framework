package com.github.hualuomoli.http.jdk;

import com.github.hualuomoli.logger.ILogger;


public class SystemLogger implements ILogger {

    @Override
    public void verbose(String tag, String msg) {
        System.out.println(msg);
    }

    @Override
    public void verbose(String tag, String msg, Throwable t) {
        System.out.println(msg);
        t.printStackTrace();
    }

    @Override
    public void debug(String tag, String msg) {
        System.out.println(msg);
    }

    @Override
    public void debug(String tag, String msg, Throwable t) {
        System.out.println(msg);
        t.printStackTrace();
    }

    @Override
    public void info(String tag, String msg) {
        System.out.println(msg);
    }

    @Override
    public void info(String tag, String msg, Throwable t) {
        System.out.println(msg);
        t.printStackTrace();
    }

    @Override
    public void warn(String tag, String msg) {
        System.out.println(msg);
    }

    @Override
    public void warn(String tag, String msg, Throwable t) {
        System.out.println(msg);
        t.printStackTrace();
    }

    @Override
    public void error(String tag, String msg) {
        System.out.println(msg);
    }

    @Override
    public void error(String tag, String msg, Throwable t) {
        System.out.println(msg);
        t.printStackTrace();
    }
}
