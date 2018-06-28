package com.github.hualuomoli.tool.security.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.codec.Base64;
import com.github.hualuomoli.tool.security.lang.KeyException;

/**
 * Key工具
 */
public final class KeyUtils {

  private static final Logger logger = LoggerFactory.getLogger(KeyUtils.class);

  /**
   * 获取SecretKey
   * @param keyBase64 Key的Base64
   * @param keyAlgorithm 秘钥算法
   * @return Key实例
   */
  public static Key getSecretKey(String keyBase64, String keyAlgorithm) {
    return getSecretKey(Base64.decode(keyBase64), keyAlgorithm);
  }

  /**
  * 获取SecretKey
  * @param keyBytes Key的字节码
  * @param keyAlgorithm 秘钥算法
  * @return Key实例
  */
  public static Key getSecretKey(byte[] keyBytes, String keyAlgorithm) {
    return new SecretKeySpec(keyBytes, keyAlgorithm);
  }

  /**
   * 获取X509公钥
   * @param publicKeyBase64 公钥Base64
   * @param keyAlgorithm 秘钥算法
   * @return 公钥
   * @throws KeyException 获取公钥失败
   */
  public static final PublicKey getX509PublicKey(String publicKeyBase64, String keyAlgorithm) throws KeyException {
    return getX509PublicKey(Base64.decode(publicKeyBase64), keyAlgorithm);
  }

  /**
  * 获取X509公钥
  * @param publicKey 公钥字节码
  * @param keyAlgorithm 秘钥算法
  * @return 公钥
  * @throws KeyException 获取公钥失败
  */
  public static final PublicKey getX509PublicKey(byte[] publicKey, String keyAlgorithm) throws KeyException {
    try {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
      KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
      return keyFactory.generatePublic(x509KeySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      logger.warn("获取公钥失败", e);
      throw new KeyException("获取公钥失败", e);
    }
  }

  /**
   * 获取PKCS8私钥
   * @param privateKeyBase64 私钥Base64
   * @param keyAlgorithm 秘钥算法
   * @return 私钥
   * @throws KeyException 获取私钥失败
   */
  public static final PrivateKey getPKCS8PrivateKey(String privateKeyBase64, String keyAlgorithm) throws KeyException {
    return getPKCS8PrivateKey(Base64.decode(privateKeyBase64), keyAlgorithm);
  }

  /**
  * 获取PKCS8私钥
  * @param privateKey 私钥字节码
  * @param keyAlgorithm 秘钥算法
  * @return 私钥
  * @throws KeyException 获取私钥失败
  */
  public static final PrivateKey getPKCS8PrivateKey(byte[] privateKey, String keyAlgorithm) throws KeyException {
    try {
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
      KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
      return keyFactory.generatePrivate(pkcs8KeySpec);
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      logger.warn("获取私钥失败", e);
      throw new KeyException("获取私钥失败", e);
    }
  }

  // 秘钥长度  ======================================
  /**
   * 获取X509公钥模长度
   * @param publicKeyBase64 公钥Base64
   * @param keyAlgorithm 算法
   * @return 公钥模长度
   * @throws KeyException 获取公钥模长度失败
   */
  public static final int getX509PublicKeyModulusLength(String publicKeyBase64, String keyAlgorithm) {
    return getX509PublicKeyModulusLength(Base64.decode(publicKeyBase64), keyAlgorithm);
  }

  /**
   * 获取X509公钥模长度
   * @param publicKey 公钥字节码
   * @param keyAlgorithm 算法
   * @return 公钥模长度
   * @throws KeyException 获取公钥模长度失败/不支持的秘钥类型
   */
  public static final int getX509PublicKeyModulusLength(byte[] publicKey, String keyAlgorithm) {
    if (StringUtils.equalsIgnoreCase(keyAlgorithm, "RSA")) {
      return getX509RSAPublicKeyModulusLength(publicKey, keyAlgorithm);
    }
    throw new KeyException("不支持的秘钥类型");
  }

  /**
   * 获取RSA的X509公钥模长度
   * @param publicKey 公钥字节码
   * @return 公钥模长度
   * @throws KeyException 获取公钥模长度失败
   */
  public static final int getX509RSAPublicKeyModulusLength(byte[] publicKey, String keyAlgorithm) throws KeyException {
    try {
      X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
      KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
      RSAPublicKey rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
      return rsaPublicKey.getModulus().toString(2).length();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      logger.warn("获取公钥模长度失败", e);
      throw new KeyException("获取公钥模长度失败", e);
    }
  }

  /**
   * 获取PKCS8私钥模长度
   * @param privateKeyBase64 私钥Base64
   * @param keyAlgorithm 秘钥算法
   * @return 私钥模长度
   * @throws KeyException 获取私钥模长度失败/不支持的秘钥类型
   */
  public static final int getPKCS8PrivateKeyModulusLength(String privateKeyBase64, String keyAlgorithm) throws KeyException {
    return KeyUtils.getPKCS8PrivateKeyModulusLength(Base64.decode(privateKeyBase64), keyAlgorithm);
  }

  /**
   * 获取PKCS8私钥模长度
   * @param privateKey 私钥字节码
   * @param keyAlgorithm 秘钥算法
   * @return 私钥模长度
   * @throws KeyException 获取私钥模长度失败/不支持的秘钥类型
   */
  public static final int getPKCS8PrivateKeyModulusLength(byte[] privateKey, String keyAlgorithm) throws KeyException {
    if (StringUtils.equalsIgnoreCase(keyAlgorithm, "RSA")) {
      return getPKCS8RSAPrivateKeyModulusLength(privateKey, keyAlgorithm);
    }
    throw new KeyException("不支持的秘钥类型");
  }

  /**
   * 获取RSA的PKCS8私钥模长度
   * @param privateKey 私钥字节码
   * @param keyAlgorithm 秘钥算法
   * @return 私钥模长度
   * @throws KeyException 获取私钥模长度失败
   */
  public static final int getPKCS8RSAPrivateKeyModulusLength(byte[] privateKey, String keyAlgorithm) throws KeyException {
    try {
      PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
      KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
      RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
      return rsaPrivateKey.getModulus().toString(2).length();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      logger.warn("获取私钥模长度失败", e);
      throw new KeyException("获取私钥模长度失败", e);
    }
  }

}
