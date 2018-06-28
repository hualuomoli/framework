package com.github.hualuomoli.password.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.github.hualuomoli.password.R;

/**
 * 弹出框密码键盘
 */
public class PasswordDialog extends Dialog {

    protected final Activity activity;

    public PasswordDialog(Activity activity) {
        super(activity, R.style.password_dialog);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);
    }

    // 初始化
    protected final void initLayout() {
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 设置dialog位置
        window.setWindowAnimations(R.style.password_dialog_animation); // 添加动画效果

        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = display.getWidth(); // 设置dialog宽度为屏幕宽度
        getWindow().setAttributes(layoutParams);
    }

}
