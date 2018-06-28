package com.github.hualuomoli.password;


import android.content.Context;
import android.util.AttributeSet;

import com.github.hualuomoli.security.AES;
import com.github.hualuomoli.security.util.KeyUtils;

import java.security.Key;

/**
 * AES密码加密
 */
public class AESPasswordView extends PasswordView {

    private Key key;

    public AESPasswordView(Context context) {
        super(context);
    }

    public AESPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setKey(String keyBase64) {
        this.key = KeyUtils.getSecretKey(keyBase64, AES.KEY_ALGORITHM);
    }

    public void setKey(Key key) {
        this.key = key;
    }

    @Override
    protected String securityPassword(String password) {
        if(key == null) {
            return EMPTY;
        }
        return AES.encrypt(key, password);
    }

}
