package com.github.hualuomoli.timer.executor;

/**
 * 执行器
 * @param <D> 业务执行返回数据类型
 * @param <E> 异常
 */
public interface Executor<D, E extends Throwable> {

    /**
     * 业务处理(如耗时操作)
     * @param name 定时器名称
     * @param maxTimes 定时器总次数
     * @param times 当前第几次执行
     * @return 业务处理结果
     * @throws E 业务处理失败错误信息
     */
    D deal(String name, int maxTimes, int times) throws E;

    /**
     * 业务处理成功
     * @param name 定时器名称
     * @param data 业务处理结果
     * @param maxTimes 定时器总次数
     * @param times 当前第几次执行
     */
    void onSuccess(String name, D data, int maxTimes, int times);

    /**
     * 业务处理失败
     * @param name 定时器名称
     * @param e 错误信息
     * @param maxTimes 定时器总次数
     * @param times 当前第几次执行
     */
    void onError(String name, E e, int maxTimes, int times);

}
