package com.github.hualuomoli.password;


import android.content.Context;
import android.util.AttributeSet;

import com.github.hualuomoli.security.RSA;
import com.github.hualuomoli.security.config.Config;
import com.github.hualuomoli.security.util.KeyUtils;

import java.security.PublicKey;

/**
 * RSA密码加密
 */
public class RSAPasswordView extends PasswordView {

    private PublicKey publicKey;
    private int keyModulusLength;

    public RSAPasswordView(Context context) {
        super(context);
    }

    public RSAPasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPublicKey(String publicKeyBase64) {
        this.publicKey = KeyUtils.getX509PublicKey(publicKeyBase64, RSA.KEY_ALGORITHM);
        this.keyModulusLength = KeyUtils.getX509PublicKeyModulusLength(publicKeyBase64, RSA.KEY_ALGORITHM);
    }

    public void setPublicKey(PublicKey publicKey, int keyModulusLength) {
        this.publicKey = publicKey;
        this.keyModulusLength = keyModulusLength;
    }

    @Override
    protected String securityPassword(String password) {
        if(publicKey == null) {
            return EMPTY;
        }
        return RSA.encrypt(publicKey, keyModulusLength, password, Config.CHARSET);
    }

}
