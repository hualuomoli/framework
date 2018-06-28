package com.github.hualuomoli.async.dealer;

import com.github.hualuomoli.async.executor.Executor;

/**
 * 业务处理器
 */
public interface Dealer {

    /**
     * 执行
     * @param executor 业务执行器
     * @param <D> 业务执行返回数据类型
     * @param <E> 异常
     */
    <D, E extends Throwable> void execute(final Executor<D, E> executor);

}
