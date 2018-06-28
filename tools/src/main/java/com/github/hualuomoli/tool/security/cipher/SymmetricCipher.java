package com.github.hualuomoli.tool.security.cipher;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.codec.Base64;
import com.github.hualuomoli.tool.security.config.Config;
import com.github.hualuomoli.tool.security.lang.CipherException;
import com.github.hualuomoli.tool.security.util.KeyUtils;

/**
 * 对称秘钥加密解密
 */
public class SymmetricCipher {

  private static final Logger logger = LoggerFactory.getLogger(SymmetricCipher.class);

  /**
   * 加密
   * @param keyBase64 Key的Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(String keyBase64, String keyAlgorithm, String algorithm, String content) throws CipherException {
    return encrypt(keyBase64, keyAlgorithm, algorithm, content, Config.CHARSET);
  }

  /**
   * 加密
   * @param keyBase64 Key的Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(String keyBase64, String keyAlgorithm, String algorithm, String content, String charset) throws CipherException {
    try {
      byte[] keyBytes = Base64.decode(keyBase64);
      byte[] contentBytes = content.getBytes(charset);

      byte[] cipherContentBytes = encrypt(keyBytes, keyAlgorithm, algorithm, contentBytes);

      return Base64.encode(cipherContentBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 加密
   * @param keyBytes Key的字节码
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败
   */
  public static byte[] encrypt(byte[] keyBytes, String keyAlgorithm, String algorithm, byte[] contentBytes) throws CipherException {
    // 获取key
    Key key = KeyUtils.getSecretKey(keyBytes, keyAlgorithm);
    // 加密
    return encrypt(key, keyAlgorithm, algorithm, contentBytes);
  }

  /**
   * 加密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, String keyAlgorithm, String algorithm, String content) throws CipherException {
    return encrypt(key, keyAlgorithm, algorithm, content, Config.CHARSET);
  }

  /**
   * 加密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, String keyAlgorithm, String algorithm, String content, String charset) throws CipherException {
    try {
      byte[] contentBytes = content.getBytes(charset);

      byte[] cipherContentBytes = encrypt(key, keyAlgorithm, algorithm, contentBytes);

      return Base64.encode(cipherContentBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 加密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败
   */
  public static byte[] encrypt(Key key, String keyAlgorithm, String algorithm, byte[] contentBytes) throws CipherException {
    try {
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
      cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);

      return cipher.doFinal(contentBytes);
    } catch (Exception e) {
      logger.warn("加密失败", e);
      throw new CipherException("加密失败", e);
    }
    // end
  }

  /**
   * 解密
   * @param keyBase64 Key的Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(String keyBase64, String keyAlgorithm, String algorithm, String cipherContentBase64) throws CipherException {
    return decrypt(keyBase64, keyAlgorithm, algorithm, cipherContentBase64, Config.CHARSET);
  }

  /**
   * 解密
   * @param keyBase64 Key的Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(String keyBase64, String keyAlgorithm, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    try {
      byte[] keyBytes = Base64.decode(keyBase64);
      byte[] cipherContentBytes = Base64.decode(cipherContentBase64);
      byte[] contentBytes = decrypt(keyBytes, keyAlgorithm, algorithm, cipherContentBytes);

      return new String(contentBytes, charset);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 解密
   * @param keyBytes Key的字节码
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败
   */
  public static byte[] decrypt(byte[] keyBytes, String keyAlgorithm, String algorithm, byte[] cipherContentBytes) throws CipherException {
    // 获取key
    Key key = KeyUtils.getSecretKey(keyBytes, keyAlgorithm);
    // 执行解密
    return decrypt(key, keyAlgorithm, algorithm, cipherContentBytes);
  }

  /**
   * 解密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, String keyAlgorithm, String algorithm, String cipherContentBase64) throws CipherException {
    return decrypt(key, keyAlgorithm, algorithm, cipherContentBase64, Config.CHARSET);
  }

  /**
   * 解密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, String keyAlgorithm, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    try {
      byte[] cipherContentBytes = Base64.decode(cipherContentBase64);
      byte[] contentBytes = decrypt(key, keyAlgorithm, algorithm, cipherContentBytes);

      return new String(contentBytes, charset);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 解密
   * @param key Key
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败
   */
  public static byte[] decrypt(Key key, String keyAlgorithm, String algorithm, byte[] cipherContentBytes) throws CipherException {
    try {
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
      cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

      return cipher.doFinal(cipherContentBytes);
    } catch (Exception e) {
      logger.warn("解密失败", e);
      throw new CipherException("解密失败", e);
    }
    // end
  }

}
