package com.github.hualuomoli.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.hualuomoli.demo.async.AsyncActivity;
import com.github.hualuomoli.demo.logger.LoggerActivity;
import com.github.hualuomoli.demo.password.PasswordActivity;
import com.github.hualuomoli.demo.security.SecurityActivity;
import com.github.hualuomoli.demo.timer.TimerActivity;
import com.github.hualuomoli.logger.Level;
import com.github.hualuomoli.logger.Logger;


public class MainActivity extends AppCompatActivity {

    private Button testLoggerButton;
    private Button testSecurityButton;
    private Button testTimerButton;
    private Button testAsyncButton;
    private Button testPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 默认输出所有日志
        Logger.setLevel(Level.ALL);
        Logger.setLogger(new AppLogger());

        testLoggerButton = (Button) findViewById(R.id.testLogger);
        testLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoggerActivity.class);
                startActivity(intent);
            }
        });

        testSecurityButton = (Button) findViewById(R.id.testSecurity);
        testSecurityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecurityActivity.class);
                startActivity(intent);
            }
        });


        testTimerButton = (Button) findViewById(R.id.testTimer);
        testTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        testAsyncButton = (Button) findViewById(R.id.testAsync);
        testAsyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AsyncActivity.class);
                startActivity(intent);
            }
        });

        // 密码键盘
        testPasswordButton = (Button) findViewById(R.id.testPassword);
        testPasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
        // end
    }

}
