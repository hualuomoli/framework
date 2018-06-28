package com.github.hualuomoli.async.executor;

/**
 * 执行器
 * @param <D> 业务执行返回数据类型
 * @param <E> 异常
 */
public interface Executor<D, E extends Throwable> {

    /**
     * 业务处理(如耗时操作)
     * @param name 执行器名称
     * @return 业务处理结果
     * @throws E 业务处理失败
     */
    D deal(String name) throws E;

    /**
     * 业务处理成功
     * @param name 定时器名称
     * @param data 业务处理结果
     */
    void onSuccess(String name, D data);

    /**
     * 业务处理失败
     * @param name 定时器名称
     * @param e 异常
     */
    void onError(String name, E e);

}
