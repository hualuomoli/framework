package com.github.hualuomoli.logger;


public interface ILogger {

    void verbose(String tag, String msg);

    void verbose(String tag, String msg, Throwable t);

    void debug(String tag, String msg);

    void debug(String tag, String msg, Throwable t);

    void info(String tag, String msg);

    void info(String tag, String msg, Throwable t);

    void warn(String tag, String msg);

    void warn(String tag, String msg, Throwable t);

    void error(String tag, String msg);

    void error(String tag, String msg, Throwable t);

}
