package com.github.hualuomoli.security;

import com.github.hualuomoli.security.cipher.SymmetricCipher;
import com.github.hualuomoli.security.lang.CipherException;

import java.security.Key;

/**
 * AES加解密
 */
public class AES {

  public static final String KEY_ALGORITHM = "AES";

  // ECB的PKCS5填充
  public static final String AES_ECB_PKCS5_PADDING = "AES/ECB/PKCS5Padding";

  // 默认算法
  private static final String ALGORITHM = AES_ECB_PKCS5_PADDING;

  /**
   * 加密 
   * @param keyBase64 Key的Base64
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(String keyBase64, String content) throws CipherException {
    return SymmetricCipher.encrypt(keyBase64, KEY_ALGORITHM, ALGORITHM, content);
  }

  /**
   * 加密
   * @param keyBase64 Key的Base64
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(String keyBase64, String content, String charset) throws CipherException {
    return SymmetricCipher.encrypt(keyBase64, KEY_ALGORITHM, ALGORITHM, content, charset);
  }

  /**
   * 加密 
   * @param keyBase64 Key的Base64
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(String keyBase64, String algorithm, String content, String charset) throws CipherException {
    return SymmetricCipher.encrypt(keyBase64, KEY_ALGORITHM, algorithm, content);
  }

  /**
   * 加密 
   * @param keyBytes Key的字节码
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(byte[] keyBytes, byte[] contentBytes) throws CipherException {
    return SymmetricCipher.encrypt(keyBytes, KEY_ALGORITHM, ALGORITHM, contentBytes);
  }

  /**
   * 加密 
   * @param keyBytes Key的字节码
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(byte[] keyBytes, String algorithm, byte[] contentBytes) throws CipherException {
    return SymmetricCipher.encrypt(keyBytes, KEY_ALGORITHM, algorithm, contentBytes);
  }

  /**
   * 加密 
   * @param key Key
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, String content) throws CipherException {
    return SymmetricCipher.encrypt(key, KEY_ALGORITHM, ALGORITHM, content);
  }

  /**
   * 加密
   * @param key Key
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, String content, String charset) throws CipherException {
    return SymmetricCipher.encrypt(key, KEY_ALGORITHM, ALGORITHM, content, charset);
  }

  /**
   * 加密 
   * @param key Key
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, String algorithm, String content, String charset) throws CipherException {
    return SymmetricCipher.encrypt(key, KEY_ALGORITHM, algorithm, content);
  }

  /**
   * 加密 
   * @param key Key
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(Key key, byte[] contentBytes) throws CipherException {
    return SymmetricCipher.encrypt(key, KEY_ALGORITHM, ALGORITHM, contentBytes);
  }

  /**
   * 加密 
   * @param key Key
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(Key key, String algorithm, byte[] contentBytes) throws CipherException {
    return SymmetricCipher.encrypt(key, KEY_ALGORITHM, algorithm, contentBytes);
  }

  /**
   * 解密 
   * @param keyBase64 Key的Base64
   * @param cipherContentBase64 密文Base64
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(String keyBase64, String cipherContentBase64) throws CipherException {
    return SymmetricCipher.decrypt(keyBase64, KEY_ALGORITHM, ALGORITHM, cipherContentBase64);
  }

  /**
   * 解密
   * @param keyBase64 Key的Base64
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(String keyBase64, String cipherContentBase64, String charset) throws CipherException {
    return SymmetricCipher.decrypt(keyBase64, KEY_ALGORITHM, ALGORITHM, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param keyBase64 Key的Base64
   * @param algorithm 算法
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(String keyBase64, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    return SymmetricCipher.decrypt(keyBase64, KEY_ALGORITHM, algorithm, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param keyBytes Key的字节码
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(byte[] keyBytes, byte[] cipherContentBytes) throws CipherException {
    return SymmetricCipher.decrypt(keyBytes, KEY_ALGORITHM, ALGORITHM, cipherContentBytes);
  }

  /**
   * 解密
   * @param keyBytes Key的字节码
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(byte[] keyBytes, String algorithm, byte[] cipherContentBytes) throws CipherException {
    return SymmetricCipher.decrypt(keyBytes, KEY_ALGORITHM, algorithm, cipherContentBytes);
  }

  /**
   * 解密 
   * @param key Key
   * @param cipherContentBase64 密文Base64
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, String cipherContentBase64) throws CipherException {
    return SymmetricCipher.decrypt(key, KEY_ALGORITHM, ALGORITHM, cipherContentBase64);
  }

  /**
   * 解密
   * @param key Key
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, String cipherContentBase64, String charset) throws CipherException {
    return SymmetricCipher.decrypt(key, KEY_ALGORITHM, ALGORITHM, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param key Key
   * @param algorithm 算法
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    return SymmetricCipher.decrypt(key, KEY_ALGORITHM, algorithm, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param key Key
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(Key key, byte[] cipherContentBytes) throws CipherException {
    return SymmetricCipher.decrypt(key, KEY_ALGORITHM, ALGORITHM, cipherContentBytes);
  }

  /**
   * 解密
   * @param key Key
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(Key key, String algorithm, byte[] cipherContentBytes) throws CipherException {
    return SymmetricCipher.decrypt(key, KEY_ALGORITHM, algorithm, cipherContentBytes);
  }

}
