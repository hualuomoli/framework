package com.github.hualuomoli.password;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.hualuomoli.commons.collect.Lists;
import com.github.hualuomoli.logger.Logger;
import com.github.hualuomoli.password.adapter.PasswordKeyboardAdapter;
import com.github.hualuomoli.password.dealer.PasswordItemClickViewDealer;
import com.github.hualuomoli.password.entity.PasswordKeyBoardDataType;
import com.github.hualuomoli.password.entity.PasswordKeyboardData;
import com.github.hualuomoli.password.listener.PasswordKeyboardItemClickListener;

import java.util.List;

public class PasswordView extends RelativeLayout implements View.OnClickListener{

    private static final String TAG = "密码界面";

    protected static final String EMPTY = "";
    private static final int PASSWORD_LENGTH = 6;

    private final Context context;

    private TextView[] contents; // // 密码内容
    private GridView keyboard;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能
    private PasswordKeyboardAdapter adapter;
    private ImageView cancel; // 取消
    private TextView tip; // 提示
    private TextView forget; // 忘记密码

    private PasswordView _self;
    private TypedArray attrArray;
    private OnPasswordListener listener;
    private PasswordKeyboardItemClickListener itemClickListener;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //
        _self = this;

        // 获取用户设置的属性信息
        attrArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView);

        // 获取布局
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.password, this, true);
        cancel = (ImageView) view.findViewById(R.id.password_cancel);
        tip = (TextView) view.findViewById(R.id.password_tip);
        forget = (TextView) view.findViewById(R.id.password_forget);
        keyboard = (GridView) view.findViewById(R.id.password_keyboard);


        // 取消/忘记密码
        cancel.setOnClickListener(this);
        forget.setOnClickListener(this);

        // 密码内容
        contents = new TextView[PASSWORD_LENGTH];
        contents[0] = (TextView) view.findViewById(R.id.password_content_1);
        contents[1] = (TextView) view.findViewById(R.id.password_content_2);
        contents[2] = (TextView) view.findViewById(R.id.password_content_3);
        contents[3] = (TextView) view.findViewById(R.id.password_content_4);
        contents[4] = (TextView) view.findViewById(R.id.password_content_5);
        contents[5] = (TextView) view.findViewById(R.id.password_content_6);


        // 初始化
        this.init();
    }

    @Override
    public void onClick(View v) {

        if(listener == null) {
            return;
        }

        // 取消
        if(v.getId() == R.id.password_cancel) {
            listener.onCancel();
            return;
        }

        // 忘记密码
        if(v.getId() == R.id.password_forget) {
            listener.onForget();
            return;
        }

        // end
    }

    // 初始化
    private void init() {

        // 是否显示忘记密码
        boolean showForget = attrArray.getBoolean(R.styleable.PasswordView_forget, false);
        if(showForget) {
            forget.setVisibility(VISIBLE);
        }

        // 显示的忘记密码内容
        String forgetText = attrArray.getString(R.styleable.PasswordView_forget_text);
        if(forgetText != null) {
            forget.setText(forgetText);
        }

        // 现在的提示内容
        String tipText = attrArray.getString(R.styleable.PasswordView_tip_text);
        if(tipText != null) {
            tip.setText(tipText);
        }

        boolean randomNumber = attrArray.getBoolean(R.styleable.PasswordView_random_number, false);

        _self.initKeybord(randomNumber);
    }

    // 初始化密码键盘
    private void initKeybord(boolean randomNumber) {

        final List<PasswordKeyboardData> datas = Lists.newArrayList();

        /* 初始化按钮上应该显示的数字 */
        PasswordKeyboardData[] numberDatas = _self.getNumberDatas();
        // 打乱
        if(randomNumber) {
            _self.random(numberDatas);
        }
        datas.addAll(Lists.newArrayList(numberDatas));

        // 添加左下角空白
        PasswordKeyboardData blankData = new PasswordKeyboardData(PasswordKeyBoardDataType.BLANK);
        datas.add(9, blankData);
        // 添加右下角的删除
        PasswordKeyboardData deleteData = new PasswordKeyboardData(PasswordKeyBoardDataType.DELETE);
        datas.add(deleteData);

        adapter = new PasswordKeyboardAdapter(_self.context, datas);
        keyboard.setAdapter(adapter);
        keyboard.setOnItemClickListener(itemClickListener = new PasswordKeyboardItemClickListener(datas, new PasswordItemClickViewDealer() {

            @Override
            public void add(int passwordCount) {
                Logger.debug(TAG, "用户点击了数字按键,当前密码个数={}", passwordCount);
                contents[passwordCount - 1].setText("*");
            }

            @Override
            public void remove(int passwordCount) {
                Logger.debug(TAG, "用户点击了删除按键,当前密码个数={}", passwordCount);
                contents[passwordCount].setText("");
            }

            @Override
            public void end(String password) {
                if(listener == null) {
                    return;
                }
                // 获取密文
                String securityPassword = _self.securityPassword(password);
                listener.onInputEnd(securityPassword);
            }

            @Override
            public void clean() {
                for(int i = 0; i < PASSWORD_LENGTH; i++) {
                    contents[i].setText(_self.EMPTY);
                }
            }
        }, PASSWORD_LENGTH));
        // end
    }

    /**
     * 获取密码密文
     * @param password 密码明文
     * @return 密码密文
     */
    protected String securityPassword(String password) {
        return password;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if(itemClickListener != null && visibility == VISIBLE) {
            itemClickListener.clean();
        }

    }

    /**
     * 初始化按钮上应该显示的数字(显示的与实际的内容一样)
     * @return 数字数组
     */
    private PasswordKeyboardData[] getNumberDatas() {
        PasswordKeyboardData[] datas = new PasswordKeyboardData[10];
        int index = 0;
        for(int i = 1; i <= 9; i++) {
            datas[index++] =new PasswordKeyboardData(PasswordKeyBoardDataType.DATA, String.valueOf(i), String.valueOf(i));
        }
        datas[index++] =new PasswordKeyboardData(PasswordKeyBoardDataType.DATA, "0", "0");
        return datas;
    }

    /**
     * 随机化集合
     * @param datas 数据
     */
    private void random(PasswordKeyboardData[] datas) {
        int count = datas.length / 2;
        for(int i=0; i < count; i++) {
            int a = (int) (Math.random() * datas.length);
            int b = (int) (Math.random() * datas.length);
            if(a == b) {
                continue;
            }
            PasswordKeyboardData data = datas[a];
            datas[a] = datas[b];
            datas[b] = data;
            data = null;
        }
    }

    // 清空密码键盘
    public void clean() {
        if(itemClickListener == null) {
            return;
        }
        itemClickListener.clean();
    }

    public void setOnPasswordListener(OnPasswordListener listener) {
        this.listener = listener;
    }

    // 监听器
    public interface OnPasswordListener {

        /**
         * 取消
         */
        void onCancel();

        /**
         * 忘记密码
         */
        void onForget();

        /**
         * 用户输入完成
         * @param password 用户输入的密码
         */
        void onInputEnd(String password);

    }

}
