package com.github.hualuomoli.demo;

import android.util.Log;

import com.github.hualuomoli.logger.ILogger;

/** 本项目Logger管理器 */
class AppLogger implements ILogger {

    @Override
    public void verbose(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void verbose(String tag, String msg, Throwable t) {
        Log.v(tag, msg, t);
    }

    @Override
    public void debug(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void debug(String tag, String msg, Throwable t) {
        Log.d(tag, msg, t);
    }

    @Override
    public void info(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void info(String tag, String msg, Throwable t) {
        Log.i(tag, msg, t);
    }

    @Override
    public void warn(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void warn(String tag, String msg, Throwable t) {
        Log.w(tag, msg, t);
    }

    @Override
    public void error(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void error(String tag, String msg, Throwable t) {
        Log.e(tag, msg, t);
    }
}
