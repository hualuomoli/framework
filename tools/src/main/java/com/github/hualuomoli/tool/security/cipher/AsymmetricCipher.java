package com.github.hualuomoli.tool.security.cipher;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.codec.Base64;
import com.github.hualuomoli.tool.security.config.Config;
import com.github.hualuomoli.tool.security.lang.CipherException;

/**
 * 非对称秘钥加密解密
 */
public class AsymmetricCipher {

  private static final Logger logger = LoggerFactory.getLogger(AsymmetricCipher.class);

  /**
   * 加密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, String content) throws CipherException {
    return encrypt(key, keyModulusLength, keyAlgorithm, algorithm, content, Config.CHARSET);
  }

  /**
   * 加密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, String content, String charset) throws CipherException {
    try {
      byte[] contentBytes = content.getBytes(charset);

      byte[] cipherContentBytes = encrypt(key, keyModulusLength, keyAlgorithm, algorithm, contentBytes);

      return Base64.encode(cipherContentBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 加密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败
   */
  public static byte[] encrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, byte[] contentBytes) throws CipherException {
    try {
      Validate.isTrue(keyModulusLength % 8 == 0, "非法的key长度");
      // 一次最大解密密文长度 = key模长度/128
      int onceLength = keyModulusLength / 8 - 11;

      // 加密
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
      cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);

      return doFinal(cipher, contentBytes, onceLength);
    } catch (Exception e) {
      logger.warn("加密失败", e);
      throw new CipherException("加密失败", e);
    }
    // end
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength  Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文Base64
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, String cipherContentBase64) throws CipherException {
    return decrypt(key, keyModulusLength, keyAlgorithm, algorithm, cipherContentBase64, Config.CHARSET);
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength  Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    try {
      byte[] cipherContentBytes = Base64.decode(cipherContentBase64);
      byte[] contentBytes = decrypt(key, keyModulusLength, keyAlgorithm, algorithm, cipherContentBytes);

      return new String(contentBytes, charset);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new CipherException("不支持的编码", e);
    }
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength  Key的Modulus长度
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败
   */
  public static byte[] decrypt(Key key, int keyModulusLength, String keyAlgorithm, String algorithm, byte[] cipherContentBytes) throws CipherException {
    try {
      Validate.isTrue(keyModulusLength % 8 == 0, "非法的key长度");
      // 一次最大解密密文长度 = key模长度/128 - 11
      int onceLength = keyModulusLength / 8;

      // 解密
      javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(algorithm);
      cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

      return doFinal(cipher, cipherContentBytes, onceLength);
    } catch (Exception e) {
      logger.warn("解密失败", e);
      throw new CipherException("解密失败", e);
    }
    // end
  }

  /**
   * 执行加密/解密
   * @param cipher 密码
   * @param input 输入数据
   * @param onceLength 数据对应类型每次加密/加密长度
   * @return 密文
   * @throws Exception 操作失败
   */
  private static byte[] doFinal(javax.crypto.Cipher cipher, byte[] input, int onceLength) throws Exception {
    int length = input.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;
    byte[] cache;
    int i = 0;
    // 对数据分段解密
    while (length - offSet > 0) {
      if (length - offSet > onceLength) {
        cache = cipher.doFinal(input, offSet, onceLength);
      } else {
        cache = cipher.doFinal(input, offSet, length - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * onceLength;
    }
    byte[] output = out.toByteArray();
    out.close();

    return output;
  }

}
