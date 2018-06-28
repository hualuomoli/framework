package com.github.hualuomoli.demo.password;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.hualuomoli.demo.R;
import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.password.PasswordView;


public class PasswordDialog extends com.github.hualuomoli.password.dialog.PasswordDialog {

    private static final String TAG = "弹出框密码";

    private PasswordView passwordView;

    private final PasswordDialog _self;
    private final Toast toast;
    private boolean success;

    public PasswordDialog(Activity activity) {
        super(activity);

        _self = this;
        toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_password);

        // 初始化布局,设置密码框在底部,设置宽度铺满屏幕
        super.initLayout();

        // 设置密码键盘监听
        passwordView = (PasswordView) findViewById(R.id.password_view_dialog);
        passwordView.setOnPasswordListener(new PasswordView.OnPasswordListener() {

            @Override
            public void onCancel() {
                toast.setText("用户取消了支付");
                toast.show();
                passwordView.setVisibility(View.GONE);
                _self.dismiss();
            }

            @Override
            public void onForget() {
                toast.setText("用户点击了忘记密码,正在跳转找回密码界面");
                toast.show();
                passwordView.setVisibility(View.GONE);
                _self.dismiss();
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
                            _self.success = true;
                        } else {
                            toast.setText("密码错误");
                            toast.show();
                        }
                        passwordView.setVisibility(View.GONE);
                        _self.dismiss();
                    }

                    @Override
                    public void error(Throwable throwable) {
                        Logger.warn(TAG, "验证密码失败", throwable);
                        toast.setText("验证密码错误");
                        passwordView.setVisibility(View.GONE);
                        _self.dismiss();
                    }
                });
            }
        });
        // end
    }

    public boolean isSuccess() {
        return success;
    }

}
