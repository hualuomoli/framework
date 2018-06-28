package com.github.hualuomoli.demo.password;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.hualuomoli.commons.codec.Base64;
import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.password.AESPasswordView;
import com.github.hualuomoli.password.PasswordView;
import com.github.hualuomoli.password.RSAPasswordView;

public class PasswordActivity extends AppCompatActivity {

    private static final String TAG = "密码键盘";

    private Button confirmButton;
    private PasswordView passwordView;
    private Button rsaConfirmButton;
    private RSAPasswordView rsaPasswordView;
    private Button aesConfirmButton;
    private AESPasswordView aesPasswordView;
    private Button dialogConfirmButton;

    private PasswordActivity _self;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        //
        _self = this;
        toast = Toast.makeText(_self, "", Toast.LENGTH_LONG);

        //
        confirmButton = (Button) findViewById(R.id.password_confirm);
        passwordView = (PasswordView) findViewById(R.id.view_password);
        rsaConfirmButton = (Button) findViewById(R.id.password_confirm_rsa);
        rsaPasswordView = (RSAPasswordView) findViewById(R.id.view_password_rsa);
        aesConfirmButton = (Button) findViewById(R.id.password_confirm_aes);
        aesPasswordView = (AESPasswordView) findViewById(R.id.view_password_aes);
        dialogConfirmButton = (Button) findViewById(R.id.password_confirm_dialog);

        // 不加密
        passwordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {

            @Override
            public void onCancel() {
                toast.setText("用户取消了支付");
                toast.show();
                passwordView.setVisibility(View.GONE);
            }

            @Override
            public void onForget() {
                toast.setText("用户点击了忘记密码,正在跳转找回密码界面");
                toast.show();
                passwordView.setVisibility(View.GONE);
            }

            @Override
            public void onInputEnd(String password) {
                PasswordUtils.checkPassword(password, new PasswordUtils.CheckCallback() {

                    @Override
                    public void before(String password) {
                        toast.setText("正在验证密码,请稍后...");
                        toast.show();
                        Logger.info(TAG, "用户输入的密码={}", password);
                    }

                    @Override
                    public void end(boolean success) {
                        if(success) {
                            toast.setText("支付成功");
                            toast.show();
                        } else {
                            toast.setText("密码错误");
                            toast.show();
                        }
                        passwordView.setVisibility(View.GONE);
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Logger.warn(TAG, "验证密码失败", throwable);
                        toast.setText("验证密码错误");
                        passwordView.setVisibility(View.GONE);
                    }
                });
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                passwordView.setVisibility(View.VISIBLE);
            }
        });

        // RSA加密
        // 设置加密公钥
        rsaPasswordView.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz5DksVsgSApFa58A/exWdb3o/woxoTTilDw7Zu9hW3eOfQHNkMmjgBrDxvkXwhEHwGCcGgvPHzfkygbUKeVsPyp4nFST4JAnlbyMz1L/kEm4E5TxNbtf+0cgNmPEiVOzhg7VRdw0PcA3Cvn6xAnSTET492ZJqwFLx97LLsjDDC7p4yveyb9MgBP18ei8IO4wSnkKd9XG+r5ffEiqiUnXgh6e4kAnxfuApwblp4M5qzv/p/fJm+1/TAoLVDIXjVzpo9pBCMn/606iR2dmv5wgp4+xCIdRASB13gwgK9huvDo5MaCrFAfHdFSWPtPX1ZCU//Ed2jQDzhJ+OQjdj+JUQQIDAQAB");
        rsaPasswordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {

            @Override
            public void onCancel() {
                toast.setText("用户取消了支付");
                toast.show();
                rsaPasswordView.setVisibility(View.GONE);
            }

            @Override
            public void onForget() {
                toast.setText("用户点击了忘记密码,正在跳转找回密码界面");
                toast.show();
                rsaPasswordView.setVisibility(View.GONE);
            }

            @Override
            public void onInputEnd(String password) {
                PasswordUtils.checkPassword(password, new PasswordUtils.CheckCallback() {

                    @Override
                    public void before(String password) {
                        toast.setText("正在验证密码,请稍后...");
                        toast.show();
                        Logger.info(TAG, "用户输入的密码={}", password);
                    }

                    @Override
                    public void end(boolean success) {
                        if(success) {
                            toast.setText("支付成功");
                            toast.show();
                        } else {
                            toast.setText("密码错误");
                            toast.show();
                        }
                        rsaPasswordView.setVisibility(View.GONE);
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Logger.warn(TAG, "验证密码失败", throwable);
                        toast.setText("验证密码错误");
                        rsaPasswordView.setVisibility(View.GONE);
                    }
                });
            }
        });
        rsaConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rsaPasswordView.setVisibility(View.VISIBLE);
            }
        });

        // AES加密
        // 设置加密Key
        aesPasswordView.setKey(Base64.encode("1234567890123456".getBytes()));
        aesPasswordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {

            @Override
            public void onCancel() {
                toast.setText("用户取消了支付");
                toast.show();
                aesPasswordView.setVisibility(View.GONE);
            }

            @Override
            public void onForget() {
                toast.setText("用户点击了忘记密码,正在跳转找回密码界面");
                toast.show();
                aesPasswordView.setVisibility(View.GONE);
            }

            @Override
            public void onInputEnd(String password) {
                PasswordUtils.checkPassword(password, new PasswordUtils.CheckCallback() {

                    @Override
                    public void before(String password) {
                        toast.setText("正在验证密码,请稍后...");
                        toast.show();
                        Logger.info(TAG, "用户输入的密码={}", password);
                    }

                    @Override
                    public void end(boolean success) {
                        if(success) {
                            toast.setText("支付成功");
                            toast.show();
                        } else {
                            toast.setText("密码错误");
                            toast.show();
                        }
                        aesPasswordView.setVisibility(View.GONE);
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Logger.warn(TAG, "验证密码失败", throwable);
                        toast.setText("验证密码错误");
                        aesPasswordView.setVisibility(View.GONE);
                    }
                });
            }
        });
        aesConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                aesPasswordView.setVisibility(View.VISIBLE);
            }
        });

        // 对话框
        dialogConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final PasswordDialog passwordDialog = new PasswordDialog(_self);
                passwordDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(passwordDialog.isSuccess()) {
                            Logger.info(TAG, "弹出框验证密码结果={}", passwordDialog.isSuccess());
                        }
                    }
                });
                passwordDialog.show();
            }
        });
        // end
    }



}
