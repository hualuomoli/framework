package com.github.hualuomoli.timer.config;

/**
 * 配置信息
 */
public class Config {

    /** 默认最大次数 */
    private static int MAX_TIMES = 100;
    /** 默认延迟启动时长，单位为毫秒 */
    private static long DEFAULT_DELAY = 0;
    /** 默认定时器间隔，单位为毫秒 */
    private static long DEFAULT_PERIOD = 1000;

    public static void setMaxTimes(int maxTimes) {
        MAX_TIMES = maxTimes;
    }

    public static int getMaxTimes() {
        return MAX_TIMES;
    }

    public static void setDefaultDelay(long defaultDelay) {
        DEFAULT_DELAY = defaultDelay;
    }

    public static long getDefaultDelay() {
        return DEFAULT_DELAY;
    }

    public static void setDefaultPeriod(long defaultPeriod) {
        DEFAULT_PERIOD = defaultPeriod;
    }

    public static long getDefaultPeriod() {
        return DEFAULT_PERIOD;
    }

}
