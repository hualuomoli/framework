package com.github.hualuomoli.logger;

import com.github.hualuomoli.logger.helpers.FormattingTuple;
import com.github.hualuomoli.logger.helpers.MessageFormatter;

/**
 * 日志
 */
public class Logger {

    /**
     * 应用级别，默认关闭
     */
    private static Level level = Level.OFF;
    private static ILogger logger = null;

    public static void setLevel(Level level) {
        if(level == null) {
            return;
        }
        Logger.level = level;
    }

    public static void setLogger(ILogger logger) {
        if(logger == null) {
            return;
        }
        Logger.logger = logger;
    }

    /**
     * 是否可以verbose级别输出
     *
     * @return 如果应用设置的级别小于或等于verbose返回true，否则返回false
     */
    public static boolean isVerboseEnabled() {
        return isEnable(Level.VERBOSE);
    }


    /**
     * verbose级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void verbose(String tag, String msg) {
        if (!isVerboseEnabled()) {
            return;
        }
        print(Level.VERBOSE, tag, msg);
    }


    /**
     * verbose级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg    参数
     */
    public static void verbose(String tag, String format, Object arg) {
        if (!isVerboseEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg);
        print(Level.VERBOSE, tag, tp);
    }


    /**
     * verbose级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void verbose(String tag, String format, Object arg1, Object arg2) {
        if (!isVerboseEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        print(Level.VERBOSE, tag, tp);
    }

    /**
     * verbose级别输出日志
     *
     * @param tag       标签
     * @param format    信息格式
     * @param arguments 参数
     */
    public static void verbose(String tag, String format, Object... arguments) {
        if (!isVerboseEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        print(Level.VERBOSE, tag, tp);
    }

    /**
     * verbose级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     * @param t   异常
     */
    public static void verbose(String tag, String msg, Throwable t) {
        if (!isVerboseEnabled()) {
            return;
        }
        print(Level.VERBOSE, tag, msg, t);
    }

    /**
     * 是否可以debug级别输出
     *
     * @return 如果应用设置的级别小于或等于debug返回true，否则返回false
     */
    public static boolean isDebugEnabled() {
        return isEnable(Level.DEBUG);
    }


    /**
     * debug级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void debug(String tag, String msg) {
        if (!isDebugEnabled()) {
            return;
        }
        print(Level.DEBUG, tag, msg);
    }


    /**
     * debug级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg    参数
     */
    public static void debug(String tag, String format, Object arg) {
        if (!isDebugEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg);
        print(Level.DEBUG, tag, tp);
    }


    /**
     * debug级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void debug(String tag, String format, Object arg1, Object arg2) {
        if (!isDebugEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        print(Level.DEBUG, tag, tp);
    }

    /**
     * debug级别输出日志
     *
     * @param tag       标签
     * @param format    信息格式
     * @param arguments 参数
     */
    public static void debug(String tag, String format, Object... arguments) {
        if (!isDebugEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        print(Level.DEBUG, tag, tp);
    }

    /**
     * debug级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     * @param t   异常
     */
    public static void debug(String tag, String msg, Throwable t) {
        if (!isDebugEnabled()) {
            return;
        }
        print(Level.DEBUG, tag, msg, t);
    }

    /**
     * 是否可以info级别输出
     *
     * @return 如果应用设置的级别小于或等于info返回true，否则返回false
     */
    public static boolean isInfoEnabled() {
        return isEnable(Level.INFO);
    }


    /**
     * info级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void info(String tag, String msg) {
        if (!isInfoEnabled()) {
            return;
        }
        print(Level.INFO, tag, msg);
    }


    /**
     * info级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg    参数
     */
    public static void info(String tag, String format, Object arg) {
        if (!isInfoEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg);
        print(Level.INFO, tag, tp);
    }


    /**
     * info级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void info(String tag, String format, Object arg1, Object arg2) {
        if (!isInfoEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        print(Level.INFO, tag, tp);
    }

    /**
     * info级别输出日志
     *
     * @param tag       标签
     * @param format    信息格式
     * @param arguments 参数
     */
    public static void info(String tag, String format, Object... arguments) {
        if (!isInfoEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        print(Level.INFO, tag, tp);
    }

    /**
     * info级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     * @param t   异常
     */
    public static void info(String tag, String msg, Throwable t) {
        if (!isInfoEnabled()) {
            return;
        }
        print(Level.INFO, tag, msg, t);
    }

    /**
     * 是否可以warn级别输出
     *
     * @return 如果应用设置的级别小于或等于warn返回true，否则返回false
     */
    public static boolean isWarnEnabled() {
        return isEnable(Level.WARN);
    }


    /**
     * warn级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void warn(String tag, String msg) {
        if (!isWarnEnabled()) {
            return;
        }
        print(Level.WARN, tag, msg);
    }


    /**
     * warn级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg    参数
     */
    public static void warn(String tag, String format, Object arg) {
        if (!isWarnEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg);
        print(Level.WARN, tag, tp);
    }


    /**
     * warn级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void warn(String tag, String format, Object arg1, Object arg2) {
        if (!isWarnEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        print(Level.WARN, tag, tp);
    }

    /**
     * warn级别输出日志
     *
     * @param tag       标签
     * @param format    信息格式
     * @param arguments 参数
     */
    public static void warn(String tag, String format, Object... arguments) {
        if (!isWarnEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        print(Level.WARN, tag, tp);
    }

    /**
     * warn级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     * @param t   异常
     */
    public static void warn(String tag, String msg, Throwable t) {
        if (!isWarnEnabled()) {
            return;
        }
        print(Level.WARN, tag, msg, t);
    }

    /**
     * 是否可以error级别输出
     *
     * @return 如果应用设置的级别小于或等于error返回true，否则返回false
     */
    public static boolean isErrorEnabled() {
        return isEnable(Level.ERROR);
    }


    /**
     * error级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     */
    public static void error(String tag, String msg) {
        if (!isErrorEnabled()) {
            return;
        }
        print(Level.ERROR, tag, msg);
    }


    /**
     * error级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg    参数
     */
    public static void error(String tag, String format, Object arg) {
        if (!isErrorEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg);
        print(Level.ERROR, tag, tp);
    }


    /**
     * error级别输出日志
     *
     * @param tag    标签
     * @param format 信息格式
     * @param arg1   参数1
     * @param arg2   参数2
     */
    public static void error(String tag, String format, Object arg1, Object arg2) {
        if (!isErrorEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
        print(Level.ERROR, tag, tp);
    }

    /**
     * error级别输出日志
     *
     * @param tag       标签
     * @param format    信息格式
     * @param arguments 参数
     */
    public static void error(String tag, String format, Object... arguments) {
        if (!isErrorEnabled()) {
            return;
        }
        FormattingTuple tp = MessageFormatter.arrayFormat(format, arguments);
        print(Level.ERROR, tag, tp);
    }

    /**
     * error级别输出日志
     *
     * @param tag 标签
     * @param msg 信息
     * @param t   异常
     */
    public static void error(String tag, String msg, Throwable t) {
        if (!isErrorEnabled()) {
            return;
        }
        print(Level.ERROR, tag, msg, t);
    }

    /**
     * 是否可以输出
     * 如果没有设置输出ILogger，不进行输出
     * 如果指定的级别小于系统级别，不进行输出
     * @param level 指定的级别
     * @return 是否可以输入
     */
    private static boolean isEnable(Level level) {
        return logger != null && level != null && level.isGreaterOrEqual(Logger.level);
    }

    /**
     * 输出日志
     * @param level 日志级别
     * @param tag 标签
     * @param pt 格式化信息
     */
    private static void print(Level level, String tag, FormattingTuple pt) {
        String message = pt.getMessage();
        Throwable t = pt.getThrowable();
        if(t == null) {
            print(level, tag, message);
        } else {
            print(level, tag, message, t);
        }
    }

    /**
     * 输出日志
     * @param level 日志级别
     * @param tag 标签
     * @param message 消息
     */
    private static void print(Level level, String tag, String message) {
        if(logger == null || level == null) {
            return;
        }
        switch (level) {
            case VERBOSE:
                logger.verbose(tag, message);
                break;
            case DEBUG:
                logger.debug(tag, message);
                break;
            case INFO:
                logger.info(tag, message);
                break;
            case WARN:
                logger.warn(tag, message);
                break;
            case ERROR:
                logger.error(tag, message);
                break;
            default:
                logger.verbose(tag, message);
        }
    }

    /**
     * 输出日志
     * @param level 日志级别
     * @param tag 标签
     * @param message 消息
     * @param t 错误信息
     */
    private static void print(Level level, String tag, String message, Throwable t) {
        if(logger == null || level == null || t == null) {
            return;
        }
        switch (level) {
            case VERBOSE:
                logger.verbose(tag, message, t);
                break;
            case DEBUG:
                logger.debug(tag, message, t);
                break;
            case INFO:
                logger.info(tag, message, t);
                break;
            case WARN:
                logger.warn(tag, message, t);
                break;
            case ERROR:
                logger.error(tag, message, t);
                break;
            default:
                logger.verbose(tag, message, t);
        }
    }

}
