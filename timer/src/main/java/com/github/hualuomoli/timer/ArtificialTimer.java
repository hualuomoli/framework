package com.github.hualuomoli.timer;

/**
 * 人为处理定时器
 */
public interface ArtificialTimer extends Timer {

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续
     */
    void keep();

}
