package com.github.hualuomoli.logger;

/**
 * 日志级别
 */
public enum Level {

    OFF(Integer.MAX_VALUE, "OFF"),
    ERROR(40000, "ERROR"),
    WARN(30000, "WARN"),
    INFO(20000, "INFO"),
    DEBUG(10000, "DEBUG"),
    VERBOSE(0000, "VERBOSE"),
    ALL(Integer.MIN_VALUE, "ALL");

    private int level;
    private String levelStr;

    Level(int level, String levelStr) {
        this.level = level;
        this.levelStr = levelStr;
    }

    /**
     * 当前级别是否大于等于比较的级别
     *
     * @param l 待比较的级别
     * @return 当前级别是否大于等于比较的级别
     */
    public boolean isGreaterOrEqual(Level l) {
        return level >= l.level;
    }

    @Override
    public String toString() {
        return "Level{" +
                "level=" + level +
                ", levelStr='" + levelStr + '\'' +
                '}';
    }
}
