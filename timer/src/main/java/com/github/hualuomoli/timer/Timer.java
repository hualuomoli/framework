package com.github.hualuomoli.timer;

import com.github.hualuomoli.timer.executor.Executor;

/**
 * 定时器
 */
public interface Timer {

    /**
     * 启动定时器
     * @param executor 业务执行器
     * @param <D> 业务执行返回数据类型
     * @param <E> 异常
     */
   <D, E extends Throwable> void start(final Executor<D, E> executor);

    /**
     * 停止定时器
     */
    void stop();

}
