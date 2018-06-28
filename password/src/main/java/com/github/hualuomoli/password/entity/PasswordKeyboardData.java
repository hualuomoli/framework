package com.github.hualuomoli.password.entity;

// 密码键盘数据
public class PasswordKeyboardData {

    /** 按键类型 */
    private PasswordKeyBoardDataType type;
    /** 按键值 */
    private String value;
    /** 按键显示的文本 */
    private String text;

    public PasswordKeyboardData(PasswordKeyBoardDataType type) {
        this.type = type;
    }

    public PasswordKeyboardData(PasswordKeyBoardDataType type, String value, String text) {
        this.type = type;
        this.value = value;
        this.text = text;
    }

    public PasswordKeyBoardDataType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "KeybordData{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
