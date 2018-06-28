package com.github.hualuomoli.password.dealer;

/**
 * 密码键盘点击处理器
 */
public interface PasswordItemClickViewDealer {

    /**
     * 修改密码提示
     * @param passwordCount 用户输入密码个数
     */
    void add(int passwordCount);

    /**
     * 修改密码提示
     * @param passwordCount 用户输入密码个数
     */
    void remove(int passwordCount);

    /**
     * 用户已完成输入
     * @param password 密码
     */
    void end(String password);

    /**
     * 清除密码提示
     */
    void clean();

}
