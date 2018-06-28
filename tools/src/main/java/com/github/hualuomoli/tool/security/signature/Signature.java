package com.github.hualuomoli.tool.security.signature;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.tool.codec.Base64;
import com.github.hualuomoli.tool.security.config.Config;
import com.github.hualuomoli.tool.security.lang.SignatureException;
import com.github.hualuomoli.tool.security.util.KeyUtils;

/**
 * 签名/验证
 */
public class Signature {

  private static final Logger logger = LoggerFactory.getLogger(Signature.class);

  /**
   * 签名
   * @param privateKeyBase64 私钥Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(String privateKeyBase64, String keyAlgorithm, String algorithm, String origin) throws SignatureException {
    return sign(privateKeyBase64, keyAlgorithm, algorithm, origin, Config.CHARSET);
  }

  /**
   * 签名
   * @param privateKeyBase64 私钥Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(String privateKeyBase64, String keyAlgorithm, String algorithm, String origin, String charset) throws SignatureException {
    try {
      byte[] privateKeyBytes = Base64.decode(privateKeyBase64);
      byte[] originBytes = origin.getBytes(charset);

      byte[] signBytes = sign(privateKeyBytes, keyAlgorithm, algorithm, originBytes);

      return Base64.encode(signBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new SignatureException("不支持的编码", e);
    }
  }

  /**
   * 签名
   * @param privateKeyBytes 私钥字节码 
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败
   */
  public static byte[] sign(byte[] privateKeyBytes, String keyAlgorithm, String algorithm, byte[] originBytes) throws SignatureException {
    // 获取私钥
    PrivateKey privateKey = KeyUtils.getPKCS8PrivateKey(privateKeyBytes, keyAlgorithm);
    // 执行签名
    return sign(privateKey, keyAlgorithm, algorithm, originBytes);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(PrivateKey privateKey, String keyAlgorithm, String algorithm, String origin) throws SignatureException {
    return sign(privateKey, keyAlgorithm, algorithm, origin, Config.CHARSET);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(PrivateKey privateKey, String keyAlgorithm, String algorithm, String origin, String charset) throws SignatureException {
    try {
      byte[] originBytes = origin.getBytes(charset);

      byte[] signBytes = sign(privateKey, keyAlgorithm, algorithm, originBytes);

      return Base64.encode(signBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new SignatureException("不支持的编码", e);
    }
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败
   */
  public static byte[] sign(PrivateKey privateKey, String keyAlgorithm, String algorithm, byte[] originBytes) throws SignatureException {
    try {
      java.security.Signature signature = java.security.Signature.getInstance(algorithm);
      signature.initSign(privateKey);
      signature.update(originBytes);

      return signature.sign();
    } catch (Exception e) {
      logger.warn("签名失败", e);
      throw new SignatureException("签名失败", e);
    }
    // end
  }

  /**
   * 验证签名
   * @param publicKeyBase64 公钥Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param signBase64 签名Base64
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(String publicKeyBase64, String keyAlgorithm, String algorithm, String origin, String signBase64) throws SignatureException {
    return verify(publicKeyBase64, keyAlgorithm, algorithm, origin, signBase64, Config.CHARSET);
  }

  /**
   * 验证签名
   * @param publicKeyBase64 公钥Base64
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param originBase64 签名Base64
   * @param charset 签名原文编码集
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(String publicKeyBase64, String keyAlgorithm, String algorithm, String origin, String originBase64, String charset) throws SignatureException {
    try {
      byte[] publicKeyBytes = Base64.decode(publicKeyBase64);
      byte[] originBytes = origin.getBytes(charset);
      byte[] signBytes = Base64.decode(originBase64);

      return verify(publicKeyBytes, keyAlgorithm, algorithm, originBytes, signBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new SignatureException("不支持的编码", e);
    }
  }

  /**
   * 验证签名
   * @param publicKeyBytes 公钥字节码
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名
   */
  public static boolean verify(byte[] publicKeyBytes, String keyAlgorithm, String algorithm, byte[] originBytes, byte[] signBytes) throws SignatureException {
    // 获取公钥
    PublicKey publicKey = KeyUtils.getX509PublicKey(publicKeyBytes, keyAlgorithm);
    // 验证签名
    return verify(publicKey, keyAlgorithm, algorithm, originBytes, signBytes);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param signBase64 签名Base64
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, String keyAlgorithm, String algorithm, String origin, String signBase64) throws SignatureException {
    return verify(publicKey, keyAlgorithm, algorithm, origin, signBase64, Config.CHARSET);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param origin 签名原文
   * @param originBase64 签名Base64
   * @param charset 签名原文编码集
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, String keyAlgorithm, String algorithm, String origin, String originBase64, String charset) throws SignatureException {
    try {
      byte[] originBytes = origin.getBytes(charset);
      byte[] signBytes = Base64.decode(originBase64);

      return verify(publicKey, keyAlgorithm, algorithm, originBytes, signBytes);
    } catch (UnsupportedEncodingException e) {
      logger.warn("不支持的编码", e);
      throw new SignatureException("不支持的编码", e);
    }
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param keyAlgorithm 密钥算法
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名
   */
  public static boolean verify(PublicKey publicKey, String keyAlgorithm, String algorithm, byte[] originBytes, byte[] signBytes) throws SignatureException {
    try {
      java.security.Signature verifier = java.security.Signature.getInstance(algorithm);
      verifier.initVerify(publicKey);
      verifier.update(originBytes);

      return verifier.verify(signBytes);
    } catch (Exception e) {
      logger.warn("无法验证签名", e);
      throw new SignatureException("无法验证签名", e);
    }
    // end
  }

}
