package com.github.hualuomoli.security;

import com.github.hualuomoli.commons.codec.Base64;
import com.github.hualuomoli.security.config.Config;
import com.github.hualuomoli.security.util.KeyUtils;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Key;

public class AESTest {

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
  public void testEncryptStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(keyBase64, shortMessage);
    origin = AES.decrypt(keyBase64, cipherContentBase64);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(keyBase64, longMessage);
    origin = AES.decrypt(keyBase64, cipherContentBase64);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(keyBase64, shortMessage, charset);
    origin = AES.decrypt(keyBase64, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(keyBase64, longMessage, charset);
    origin = AES.decrypt(keyBase64, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptStringStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(keyBase64, algorithm, shortMessage, charset);
    origin = AES.decrypt(keyBase64, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(keyBase64, algorithm, longMessage, charset);
    origin = AES.decrypt(keyBase64, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptByteArrayByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] originBytes = null;

    cipherContentBytes = AES.encrypt(keyBytes, shortMessageBytes);
    originBytes = AES.decrypt(keyBytes, cipherContentBytes);
    Assert.assertEquals("验证失败", shortMessage, new String(originBytes, charset));

    cipherContentBytes = AES.encrypt(keyBytes, longMessageBytes);
    originBytes = AES.decrypt(keyBytes, cipherContentBytes);
    Assert.assertEquals("验证失败", longMessage, new String(originBytes, charset));
  }

  @Test
  public void testEncryptByteArrayStringByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] originBytes = null;

    cipherContentBytes = AES.encrypt(keyBytes, algorithm, shortMessageBytes);
    originBytes = AES.decrypt(keyBytes, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", shortMessage, new String(originBytes, charset));

    cipherContentBytes = AES.encrypt(keyBytes, algorithm, longMessageBytes);
    originBytes = AES.decrypt(keyBytes, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", longMessage, new String(originBytes, charset));
  }

  @Test
  public void testEncryptKeyString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(key, shortMessage);
    origin = AES.decrypt(key, cipherContentBase64);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(key, longMessage);
    origin = AES.decrypt(key, cipherContentBase64);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptKeyStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(key, shortMessage, charset);
    origin = AES.decrypt(key, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(key, longMessage, charset);
    origin = AES.decrypt(key, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptKeyStringStringString() {
    String cipherContentBase64 = null;
    String origin = null;

    cipherContentBase64 = AES.encrypt(key, algorithm, shortMessage, charset);
    origin = AES.decrypt(key, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", shortMessage, origin);

    cipherContentBase64 = AES.encrypt(key, algorithm, longMessage, charset);
    origin = AES.decrypt(key, algorithm, cipherContentBase64, charset);
    Assert.assertEquals("验证失败", longMessage, origin);
  }

  @Test
  public void testEncryptKeyByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] originBytes = null;

    cipherContentBytes = AES.encrypt(key, shortMessageBytes);
    originBytes = AES.decrypt(key, cipherContentBytes);
    Assert.assertEquals("验证失败", shortMessage, new String(originBytes, charset));

    cipherContentBytes = AES.encrypt(key, longMessageBytes);
    originBytes = AES.decrypt(key, cipherContentBytes);
    Assert.assertEquals("验证失败", longMessage, new String(originBytes, charset));
  }

  @Test
  public void testEncryptKeyStringByteArray() throws UnsupportedEncodingException {
    byte[] cipherContentBytes = null;
    byte[] originBytes = null;

    cipherContentBytes = AES.encrypt(key, algorithm, shortMessageBytes);
    originBytes = AES.decrypt(key, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", shortMessage, new String(originBytes, charset));

    cipherContentBytes = AES.encrypt(key, algorithm, longMessageBytes);
    originBytes = AES.decrypt(key, algorithm, cipherContentBytes);
    Assert.assertEquals("验证失败", longMessage, new String(originBytes, charset));
  }

}
