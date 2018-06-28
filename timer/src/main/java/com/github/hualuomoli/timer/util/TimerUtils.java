package com.github.hualuomoli.timer.util;

import android.os.Handler;
import android.os.Message;

import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.timer.ArtificialTimer;
import com.github.hualuomoli.timer.Timer;
import com.github.hualuomoli.timer.config.Config;
import com.github.hualuomoli.timer.executor.Executor;

import java.util.TimerTask;

/**
 * 定时器工具
 */
public class TimerUtils {

    private static final String TAG = "com.github.hualuomoli.timer.util.TimerUtils";
    /**
     * 创建定时器
     * @return 定时器
     */
    public static Timer newTimer() {
        return new DefaultTimer(createName(), Config.getDefaultDelay(), Config.getDefaultPeriod(), Config.getMaxTimes());
    }
    /**
     * 创建定时器
     * @param name 定时器名称
     * @return 定时器
     */
    public static Timer newTimer(String name) {
        return new DefaultTimer(name, Config.getDefaultDelay(), Config.getDefaultPeriod(), Config.getMaxTimes());
    }
    /**
     * 创建定时器
     * @param name 定时器名称
     * @param delay 定时器开始延迟时间，单位为毫秒
     * @param period 定时器执行周期，单位为毫秒
     * @return 定时器
     */
    public static Timer newTimer(String name, long delay, long period) {
        return new DefaultTimer(name, delay, period, Config.getMaxTimes());
    }
    /**
     * 创建定时器
     * @param name 定时器名称
     * @param delay 定时器开始延迟时间，单位为毫秒
     * @param period 定时器执行周期，单位为毫秒
     * @param maxTimes 定时器最大执行次数，单位为毫秒
     * @return 定时器
     */
    public static Timer newTimer(String name, long delay, long period, int maxTimes) {
        return new DefaultTimer(name, delay, period, maxTimes);
    }
    /**
     * 创建人工控制的定时器
     * @return 定时器
     */
    public static ArtificialTimer newArtificialTimer() {
        return new DefaultArtificialTimer(createName(), Config.getDefaultDelay(), Config.getDefaultPeriod(), Config.getMaxTimes());
    }
    /**
     * 创建人工控制的定时器
     * @param name 定时器名称
     * @return 定时器
     */
    public static ArtificialTimer newArtificialTimer(String name) {
        return new DefaultArtificialTimer(name, Config.getDefaultDelay(), Config.getDefaultPeriod(), Config.getMaxTimes());
    }
    /**
     * 创建人工控制的定时器
     * @param name 定时器名称
     * @param delay 定时器开始延迟时间，单位为毫秒
     * @param period 定时器执行周期，单位为毫秒
     * @return 定时器
     */
    public static ArtificialTimer newArtificialTimer(String name, long delay, long period) {
        return new DefaultArtificialTimer(name, delay, period, Config.getMaxTimes());
    }

    /**
     * 创建人工控制的定时器
     * @param name 定时器名称
     * @param delay 定时器开始延迟时间，单位为毫秒
     * @param period 定时器执行周期，单位为毫秒
     * @param maxTimes 定时器最大执行次数，单位为毫秒
     * @return 定时器
     */
    public static ArtificialTimer newArtificialTimer(String name, long delay, long period, int maxTimes) {
        return new DefaultArtificialTimer(name, delay, period, maxTimes);
    }

    /**
     * 创建定时器名称
     * @return 定时器名称
     */
    private static String createName() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 默认定时器
    private static class DefaultTimer implements Timer {

        private static final int HANDLER_WHAT_SUCCESS = 1;
        private static final int HANDLER_WHAT_ERROR = 2;

        /** 定时器名称 */
        private final String name;
        /** 延迟启动时间，单位为毫秒 */
        private final long delay;
        /** 执行间隔，单位为毫秒 */
        private final long period;
        /** 最大执行次数 */
        private final  int maxTimers;

        /** 当前执行次数 */
        private int times;
        /** 定时器 */
        private java.util.Timer timer;
        /** 定时任务 */
        private TimerTask timerTask;
        /** 是否可以继续执行定时器 */
        protected boolean canContinue;
        /** 用于执行器操作主线程的组件 */
        private Handler handler;

        public DefaultTimer(String name, long delay, long period, int maxTimers) {
            this.name = name;
            this.delay = delay;
            this.period = period;
            this.maxTimers = maxTimers;
        }

        @Override
        public <D, E extends Throwable> void  start(final Executor<D, E> executor) {
            Logger.info(TAG, "定时器{}正在启动", name);
            this.times = 1;
            canContinue = true;
            handler = new Handler() {

                @Override
                public void handleMessage(Message msg) {

                    switch (msg.what) {
                        case HANDLER_WHAT_SUCCESS:
                            executor.onSuccess( name, (D)msg.obj,maxTimers, msg.arg1);
                            break;
                        case HANDLER_WHAT_ERROR:
                            executor.onError(name, (E) msg.obj, maxTimers, msg.arg1);
                            break;
                    }
                    // end handle message
                }

            };
            timer = new java.util.Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    // 是否可以继续执行定时器
                    if(!canContinue) {
                        return;
                    }

                    Logger.verbose(TAG, "定时器业务判断进入times={}", times);

                    if(times > maxTimers) {
                        // 已经达到最大次数
                        canContinue = false;
                        DefaultTimer.this.stop();
                    } else {
                        // 使用handler处理消息，否则子线程无法操作主线程信息
                        Message message = new Message();
                        message.arg1 = times;

                        // 执行业务操作
                        try {
                            D result = executor.deal(name, maxTimers, times);
                            message.obj = result;
                            message.what = HANDLER_WHAT_SUCCESS;
                        } catch (Throwable e) {
                            message.obj = e;
                            message.what = HANDLER_WHAT_ERROR;
                        }
                        // 发送消息
                        handler.sendMessage(message);
                        times++;
                    }
                }
            };

            // 启动定时器
            timer.schedule(timerTask, delay, period);
            Logger.info(TAG, "定时器{}启动成功", name);
        }

        @Override
        public void stop() {
            Logger.info(TAG, "定时器{}正在停止", name);
            canContinue = false;
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            Logger.info(TAG, "定时器{}已停止", name);
        }

        // end class
    }

    // 人工干预定时器
    private static class DefaultArtificialTimer extends DefaultTimer implements ArtificialTimer {

        public DefaultArtificialTimer(String name, long delay, long period, int maxTimers) {
            super(name, delay, period, maxTimers);
        }

        @Override
        public void pause() {
            super.canContinue = false;
        }

        @Override
        public void keep() {
            super.canContinue = true;
        }
    }

}
