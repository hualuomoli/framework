package com.github.hualuomoli.async.util;

import android.os.Handler;
import android.os.Message;

import com.github.hualuomoli.async.dealer.Dealer;
import com.github.hualuomoli.async.executor.Executor;

/**
 * 异步处理工具
 */
public class AsyncUtils {

    /**
     * 获取业务处理器
     * @return 业务处理器
     */
    public static Dealer newDealer() {
        return new DefaultDealer(createName());
    }

    /**
     * 获取业务处理器
     * @param name 执行器名称
     * @return 业务处理器
     */
    public static Dealer newDealer(String name) {
        return new DefaultDealer(name);
    }

    /**
     * 创建定时器名称
     * @return 定时器名称
     */
    private static String createName() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 默认实现
    private static class DefaultDealer implements Dealer {

        private static final int HANDLER_WHAT_SUCCESS = 1;
        private static final int HANDLER_WHAT_ERROR = 2;

        private String name;

        private DefaultDealer(String name) {
            this.name = name;
        }

        @Override
        public <D, E extends Throwable> void execute(final Executor<D, E> executor) {
            // handler
            final Handler handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case HANDLER_WHAT_SUCCESS:
                            executor.onSuccess(name, (D) msg.obj);
                            break;
                        case HANDLER_WHAT_ERROR:
                            executor.onError(name, (E) msg.obj);
                            break;
                    }
                    // end
                }
            };
            // 开启新线程处理
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Message message = new Message();

                    try {
                        D result = executor.deal(name);
                        message.obj = result;
                        message.what = HANDLER_WHAT_SUCCESS;
                    } catch (Throwable e) {
                        message.obj = e;
                        message.what = HANDLER_WHAT_ERROR;

                    }
                    // 发送消息
                    handler.sendMessage(message);
                }
            }).start();
        }


    }

}
