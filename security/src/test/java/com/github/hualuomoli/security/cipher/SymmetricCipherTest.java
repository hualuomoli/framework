package com.github.hualuomoli.security.cipher;

import com.github.hualuomoli.commons.codec.Base64;
import com.github.hualuomoli.security.AES;
import com.github.hualuomoli.security.Utils;
import com.github.hualuomoli.security.config.Config;
import com.github.hualuomoli.security.util.KeyUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Key;

public class SymmetricCipherTest {

  private static final String keyAlgorithm = AES.KEY_ALGORITHM;
  private static final String algorithm = AES.AES_ECB_PKCS5_PADDING;
  private static final String charset = Config.CHARSET;

  private static final String keyBase64;
  private static final byte[] keyBytes;
  private static final Key key;

  private static final String shortMessage = "这是一段段的文字信息";
  private static final String longMessage;
  private static final byte[] shortMessageBytes;
  private static final byte[] longMessageBytes;

  static {
    keyBytes = "1234567890123456".getBytes();
    keyBase64 = Base64.encode(keyBytes);
    key = KeyUtils.getSecretKey(keyBytes, keyAlgorithm);
    longMessage = Utils.readContent();
    shortMessageBytes = Utils.getBytes(shortMessage);
    longMessageBytes = Utils.getBytes(longMessage);
  }

  @Test
  public void testEncryptStringStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = SymmetricCipher.encrypt(keyBase64, keyAlgorithm, algorithm, shortMessage);
    origin = SymmetricCipher.decrypt(keyBase64, keyAlgorithm, algorithm, cipherContentBase64);
    Assert.assertEquals("验证失败", origin, shortMessage);

    cipherContentBase64 = SymmetricCipher.encrypt(keyBase64, keyAlgorithm, algorithm, longMessage);
    origin = SymmetricCipher.decrypt(keyBase64, keyAlgorithm, algorithm, cipherContentBase64);
    Assert.assertEquals("验证失败", origin, longMessage);
  }

  @Test
  public void testEncryptStringStringStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = SymmetricCipher.encrypt(keyBase64, keyAlgorithm, algorithm, shortMessage, charset);
    origin = SymmetricCipher.decrypt(keyBase64, keyAlgorithm, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", origin, shortMessage);

    cipherContentBase64 = SymmetricCipher.encrypt(keyBase64, keyAlgorithm, algorithm, longMessage, charset);
    origin = SymmetricCipher.decrypt(keyBase64, keyAlgorithm, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", origin, longMessage);
  }

  @Test
  public void testEncryptByteArrayStringStringByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] origin = null;

    cipherContentBytes = SymmetricCipher.encrypt(keyBytes, keyAlgorithm, algorithm, shortMessageBytes);
    origin = SymmetricCipher.decrypt(keyBytes, keyAlgorithm, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", new String(origin, charset), shortMessage);

    cipherContentBytes = SymmetricCipher.encrypt(keyBytes, keyAlgorithm, algorithm, longMessageBytes);
    origin = SymmetricCipher.decrypt(keyBytes, keyAlgorithm, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", new String(origin, charset), longMessage);
  }

  @Test
  public void testEncryptKeyStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, shortMessage);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBase64);
    Assert.assertEquals("验证失败", origin, shortMessage);

    cipherContentBase64 = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, longMessage);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBase64);
    Assert.assertEquals("验证失败", origin, longMessage);
  }

  @Test
  public void testEncryptKeyStringStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, shortMessage, charset);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", origin, shortMessage);

    cipherContentBase64 = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, longMessage, charset);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", origin, longMessage);
  }

  @Test
  public void testEncryptKeyStringStringByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] origin = null;

    cipherContentBytes = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, shortMessageBytes);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", new String(origin, charset), shortMessage);

    cipherContentBytes = SymmetricCipher.encrypt(key, keyAlgorithm, algorithm, longMessageBytes);
    origin = SymmetricCipher.decrypt(key, keyAlgorithm, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", new String(origin, charset), longMessage);
  }

}
