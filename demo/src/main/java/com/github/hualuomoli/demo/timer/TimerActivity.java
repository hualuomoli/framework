package com.github.hualuomoli.demo.timer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.timer.ArtificialTimer;
import com.github.hualuomoli.timer.Timer;
import com.github.hualuomoli.timer.config.Config;
import com.github.hualuomoli.timer.executor.Executor;
import com.github.hualuomoli.timer.util.TimerUtils;

import java.util.UUID;

public class TimerActivity extends AppCompatActivity {

    private static final String TAG = "com.github.hualuomoli.demo.timer.TimerActivity";

    // 默认
    private EditText delayEditText;
    private EditText periodEditText;
    private EditText maxTimeEditText;
    private Button startButton;
    private Button stopButton;

    // 人工控制
    private EditText artificialDelayEditText;
    private EditText aArtificialPeriodEditText;
    private EditText artificialMaxTimeEditText;
    private Button artificialStartButton;
    private Button artificialPauseButton;
    private Button artificialKeepButton;
    private Button artificialStopButton;

    private Timer timer;
    private ArtificialTimer artificialTimer;

    private TimerActivity _self;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        _self = this;
        toast = Toast.makeText(_self, "", Toast.LENGTH_SHORT);
        Config.setMaxTimes(10); // 设置默认最大次数10

        delayEditText = (EditText) findViewById(R.id.et_delay);
        periodEditText = (EditText) findViewById(R.id.et_period);
        maxTimeEditText = (EditText) findViewById(R.id.et_maxTimes);
        startButton = (Button) findViewById(R.id.start);
        stopButton = (Button) findViewById(R.id.stop);

        artificialDelayEditText = (EditText) findViewById(R.id.et_artificial_delay);
        aArtificialPeriodEditText = (EditText) findViewById(R.id.et_artificial_period);
        artificialMaxTimeEditText = (EditText) findViewById(R.id.et_artificial_maxTimes);
        artificialStartButton = (Button) findViewById(R.id.artificial_start);
        artificialPauseButton = (Button) findViewById(R.id.artificial_pause);
        artificialKeepButton = (Button) findViewById(R.id.artificial_keep);
        artificialStopButton = (Button) findViewById(R.id.artificial_stop);

        // 定时器按钮被点击
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delay = delayEditText.getText().toString();
                String period = periodEditText.getText().toString();
                String maxTimes = maxTimeEditText.getText().toString();

                // 定时器名称
                String name = UUID.randomUUID().toString();

                // 停止原来的
                _self.stopTimer();

                if(isBlank(delay) || isBlank(period)) {
                    Logger.info(TAG,"用户未设置任何定时器信息，使用默认配置");
                    timer = TimerUtils.newTimer(name);
                } else if(isBlank(maxTimes)) {
                    Logger.info(TAG,"用户未设置定时器最大执行次数，delay={}, period={}", delay, period);
                    timer = TimerUtils.newTimer(name, Integer.parseInt(delay), Integer.parseInt(period));
                } else {
                    Logger.info(TAG,"用户设置的定时器参数信息，delay={}, period={}, maxTimes={}", delay, period, maxTimes);
                    timer = TimerUtils.newTimer(name, Integer.parseInt(delay), Integer.parseInt(period), Integer.parseInt(maxTimes));
                }

                timer.start(new Executor<Object, RuntimeException>() {

                    @Override
                    public Object deal(String name, int maxTimes, int times) throws RuntimeException {
                        long seconds = (int) (Math.random() * 10);
                        Logger.debug(TAG, "[定时器] 业务耗时业务处理开始 times={}, seconds={}", times, seconds);
                        // 测试延迟操作
                        try {
                            Thread.sleep(seconds * 1000);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Logger.debug(TAG, "[定时器] 业务耗时业务处理结束 times={}", times);
                        return seconds;
                    }

                    @Override
                    public void onSuccess(String name, Object data, int maxTimes, int times) {
                        Logger.debug(TAG, "[定时器] name-{}, maxTimes={}, times={}, data={}", name, maxTimes, times, data);
                        toast.setText(name + "[定时器] 最大执行" + maxTimes + "次，已经执行" + times + "次");
                        toast.show();
                    }

                    @Override
                    public void onError(String name, RuntimeException e, int maxTimes, int times) {
                        Logger.debug(TAG, "[定时器] name-{}, maxTimes={}, times={}", name, maxTimes, times, e);
                        toast.setText("[定时器] 执行失败，错误信息为" + e.getMessage());
                        toast.show();
                    }
                });

            }
        });

        // 停止定时器
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timer == null) {
                    return;
                }
                _self.stopTimer();
            }
        });

        // 人工控制定时器被点击
        artificialStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String delay = artificialDelayEditText.getText().toString();
                String period = aArtificialPeriodEditText.getText().toString();
                String maxTimes = artificialMaxTimeEditText.getText().toString();

                // 定时器名称
                String name = UUID.randomUUID().toString();

                // 停止原来的
                _self.stopTimer();

                if(isBlank(delay) || isBlank(period)) {
                    Logger.info(TAG,"用户未设置任何定时器信息，使用默认配置");
                    artificialTimer = TimerUtils.newArtificialTimer(name);
                } else if(isBlank(maxTimes)) {
                    Logger.info(TAG,"用户未设置定时器最大执行次数，delay={}, period={}", delay, period);
                    artificialTimer = TimerUtils.newArtificialTimer(name, Integer.parseInt(delay), Integer.parseInt(period));
                } else {
                    Logger.info(TAG,"用户设置的定时器参数信息，delay={}, period={}, maxTimes={}", delay, period, maxTimes);
                    artificialTimer = TimerUtils.newArtificialTimer(name, Integer.parseInt(delay), Integer.parseInt(period), Integer.parseInt(maxTimes));
                }

                artificialTimer.start(new Executor<Object, RuntimeException>() {

                    @Override
                    public Object deal(String name, int maxTimes, int times) throws RuntimeException{
                        long seconds = (int) (Math.random() * 10);
                        Logger.debug(TAG, "[人工控制定时器] 业务耗时业务处理开始 times={}, seconds={}", times, seconds);
                        // 测试延迟操作
                        try {
                            Thread.sleep(seconds * 1000);
                        } catch (Exception e) {
                           throw new RuntimeException(e);
                        }
                        Logger.debug(TAG, "[人工控制定时器] 业务耗时业务处理结束 times={}", times);
                        return seconds;
                    }

                    @Override
                    public void onSuccess(String name, Object data, int maxTimes, int times) {
                        Logger.debug(TAG, "[人工控制定时器] name-{}, maxTimes={}, times={}, data={}", name, maxTimes, times, data);
                        toast.setText(name + "[人工控制定时器] 最大执行" + maxTimes + "次，已经执行" + times + "次");
                        toast.show();
                    }

                    @Override
                    public void onError(String name, RuntimeException e, int maxTimes, int times) {
                        Logger.debug(TAG, "[人工控制定时器] name-{}, maxTimes={}, times={}", name, maxTimes, times, e);
                        toast.setText("[人工控制定时器] 执行失败，错误信息为" + e.getMessage());
                        toast.show();
                    }
                });

            }
        });
        // 暂停
        artificialPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(artificialTimer == null) {
                    toast.setText("定时器未启动");
                    toast.show();
                    return;
                }
                artificialTimer.pause();
            }
        });
        // 继续
        artificialKeepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(artificialTimer == null) {
                    toast.setText("定时器未启动");
                    toast.show();
                    return;
                }
                artificialTimer.keep();
            }
        });
        // 人工控制定时器停止
        artificialStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(artificialTimer == null) {
                    toast.setText("定时器未启动");
                    toast.show();
                    return;
                }
               _self.stopTimer();
            }
        });

    }

    // 停止Timer
    private void stopTimer(){
        if(timer != null) {
            timer.stop();
            timer = null;
        }
        if(artificialTimer != null) {
            artificialTimer.stop();
            artificialTimer = null;
        }
        toast.setText("定时器已停止");
        toast.show();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

}
