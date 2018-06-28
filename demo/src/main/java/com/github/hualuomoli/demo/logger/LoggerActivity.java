package com.github.hualuomoli.demo.logger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.logger.Level;
import com.github.hualuomoli.logger.Logger;

public class LoggerActivity extends AppCompatActivity {

    private static final String TAG = "LoggerActivity";

    private RadioGroup group;
    private Button showLogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);

        group = (RadioGroup) findViewById(R.id.logger_level);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.logger_level_all) {
                    Logger.setLevel(Level.ALL);
                    return;
                }

                if (checkedId == R.id.logger_level_verbose) {
                    Logger.setLevel(Level.VERBOSE);
                    return;
                }

                if (checkedId == R.id.logger_level_debug) {
                    Logger.setLevel(Level.DEBUG);
                    return;
                }

                if (checkedId == R.id.logger_level_info) {
                    Logger.setLevel(Level.INFO);
                    return;
                }

                if (checkedId == R.id.logger_level_warn) {
                    Logger.setLevel(Level.WARN);
                    return;
                }

                if (checkedId == R.id.logger_level_error) {
                    Logger.setLevel(Level.ERROR);
                    return;
                }

                if (checkedId == R.id.logger_level_off) {
                    Logger.setLevel(Level.OFF);
                    return;
                }
                // end
            }
        });

        showLogButton = (Button) findViewById(R.id.showLog);
        showLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog();
                Toast.makeText(LoggerActivity.this, "日志已输出，请查看", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLog() {
        Logger.verbose(TAG, "this is verbose message.");
        Logger.debug(TAG, "this is debug message.");
        Logger.info(TAG, "this is info message.");
        Logger.warn(TAG, "this is warn message.");
        Logger.error(TAG, "this is errro message.");
    }

}
