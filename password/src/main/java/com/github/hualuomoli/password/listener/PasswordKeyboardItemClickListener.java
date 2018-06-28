package com.github.hualuomoli.password.listener;

import android.view.View;
import android.widget.AdapterView;

import com.github.hualuomoli.password.dealer.PasswordItemClickViewDealer;
import com.github.hualuomoli.password.entity.PasswordKeyboardData;

import java.util.List;

public class PasswordKeyboardItemClickListener implements AdapterView.OnItemClickListener {

    private final List<PasswordKeyboardData> datas;
    private final PasswordItemClickViewDealer dealer;
    private final int finishCount;

    private PasswordKeyboardItemClickListener _self;
    private StringBuilder password;
    private int count;

    public PasswordKeyboardItemClickListener(List<PasswordKeyboardData> datas, PasswordItemClickViewDealer dealer, int finishCount) {
        this.datas = datas;
        this.dealer = dealer;
        this.finishCount = finishCount;


        _self = this;
        count = 0;
        password = new StringBuilder();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        synchronized (password) {
            PasswordKeyboardData data = datas.get(position);

            switch (data.getType()) {
                case DATA:
                    _self.onNumberPressed(data);
                    break;
                case DELETE:
                    _self.onDeletePressed();
                    break;
            }
            // end sync
        }

        // end on click
    }

    // 数字按钮
    private void onNumberPressed(PasswordKeyboardData data) {
        if(count >= finishCount) {
            return;
        }
        // 密码个数增加一个
        count++;
        // 密码增加一位
        password.append(data.getValue());
        // 更新主布局
        dealer.add(count);
        // 密码已输入6位
        if(count == finishCount) {
            this.gotoEnd(password.toString());
        }
        // end
    }

    // 删除按钮
    private void onDeletePressed() {
        if(count <= 0) {
            return;
        }
        // 密码个数减少一个
        count--;
        // 密码较少一位
        password.delete(count, count + 1);
        // 更新主布局
        dealer.remove(count);
    }

    /**
     * 输入结束
     * @param passwordText 密码文本
     */
    private void gotoEnd(String passwordText) {
        dealer.end(passwordText);
    }

    /**
     * 初始化
     */
    public void clean() {
        count = 0;
        password = new StringBuilder();
        dealer.clean();
    }

}
