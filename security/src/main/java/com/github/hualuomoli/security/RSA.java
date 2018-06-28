package com.github.hualuomoli.security;

import com.github.hualuomoli.security.cipher.AsymmetricCipher;
import com.github.hualuomoli.security.lang.CipherException;
import com.github.hualuomoli.security.lang.SignatureException;
import com.github.hualuomoli.security.signature.Signature;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA签名验签
 */
public class RSA {

  public static final String KEY_ALGORITHM = "RSA";

  // 签名
  public static final String SIGN_SHA1_WITH_RSA = "SHA1withRSA";

  // 加密
  public static final String ENCRYPT_RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

  // 默认签名算法
  private static final String SIGN_ALGORITHM = SIGN_SHA1_WITH_RSA;
  // 默认加密算法
  private static final String ENCRYPT_ALGORITHM = ENCRYPT_RSA_ECB_PKCS1_PADDING;

  /**
   * 签名
   * @param privateKeyBase64 私钥Base64
   * @param origin 签名原文
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(String privateKeyBase64, String origin) throws SignatureException {
    return Signature.sign(privateKeyBase64, KEY_ALGORITHM, SIGN_ALGORITHM, origin);
  }

  /**
   * 签名
   * @param privateKeyBase64 私钥Base64
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(String privateKeyBase64, String origin, String charset) throws SignatureException {
    return Signature.sign(privateKeyBase64, KEY_ALGORITHM, SIGN_ALGORITHM, origin, charset);
  }

  /**
   * 签名
   * @param privateKeyBase64 私钥Base64
   * @param algorithm 算法
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(String privateKeyBase64, String algorithm, String origin, String charset) throws SignatureException {
    return Signature.sign(privateKeyBase64, KEY_ALGORITHM, algorithm, origin, charset);
  }

  /**
   * 签名
   * @param privateKeyBytes 私钥字节码
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static byte[] sign(byte[] privateKeyBytes, byte[] originBytes) throws SignatureException {
    return Signature.sign(privateKeyBytes, KEY_ALGORITHM, SIGN_ALGORITHM, originBytes);
  }

  /**
   * 签名
   * @param privateKeyBytes 私钥字节码
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static byte[] sign(byte[] privateKeyBytes, String algorithm, byte[] originBytes) throws SignatureException {
    return Signature.sign(privateKeyBytes, KEY_ALGORITHM, algorithm, originBytes);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param origin 签名原文
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(PrivateKey privateKey, String origin) throws SignatureException {
    return Signature.sign(privateKey, KEY_ALGORITHM, SIGN_ALGORITHM, origin);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(PrivateKey privateKey, String origin, String charset) throws SignatureException {
    return Signature.sign(privateKey, KEY_ALGORITHM, SIGN_ALGORITHM, origin, charset);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param algorithm 算法
   * @param origin 签名原文
   * @param charset 数据编码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static String sign(PrivateKey privateKey, String algorithm, String origin, String charset) throws SignatureException {
    return Signature.sign(privateKey, KEY_ALGORITHM, algorithm, origin, charset);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static byte[] sign(PrivateKey privateKey, byte[] originBytes) throws SignatureException {
    return Signature.sign(privateKey, KEY_ALGORITHM, SIGN_ALGORITHM, originBytes);
  }

  /**
   * 签名
   * @param privateKey 私钥
   * @param algorithm 算法
   * @param originBytes 签名原文字节码
   * @return 签名
   * @throws SignatureException 签名失败/不支持的编码
   */
  public static byte[] sign(PrivateKey privateKey, String algorithm, byte[] originBytes) throws SignatureException {
    return Signature.sign(privateKey, KEY_ALGORITHM, algorithm, originBytes);
  }

  /**
   * 验证签名
  * @param publicKeyBase64 公钥Base64
   * @param origin 签名原文
  * @param signBase64 签名Base64
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(String publicKeyBase64, String origin, String signBase64) throws SignatureException {
    return Signature.verify(publicKeyBase64, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64);
  }

  /**
   * 验证签名
  * @param publicKeyBase64 公钥Base64
   * @param origin 签名原文
  * @param signBase64 签名Base64
   * @param charset 数据编码
   * @return 签名是否合法
    * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(String publicKeyBase64, String origin, String signBase64, String charset) throws SignatureException {
    return Signature.verify(publicKeyBase64, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64, charset);
  }

  /**
  * 验证签名
  * @param publicKeyBase64 公钥Base64
   * @param algorithm 算法
  * @param origin 签名原文
  * @param signBase64 签名Base64
  * @param charset 数据编码
  * @return 签名是否合法
  * @throws SignatureException 无法验证签名/不支持的编码
  */
  public static boolean verify(String publicKeyBase64, String algorithm, String origin, String signBase64, String charset) throws SignatureException {
    return Signature.verify(publicKeyBase64, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64, charset);
  }

  /**
   * 验证签名
   * @param publicKeyBytes 公钥字节码
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(byte[] publicKeyBytes, byte[] originBytes, byte[] signBytes) throws SignatureException {
    return Signature.verify(publicKeyBytes, KEY_ALGORITHM, SIGN_ALGORITHM, originBytes, signBytes);
  }

  /**
   * 验证签名
   * @param publicKeyBytes 公钥字节码
   * @param algorithm 密钥算法
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(byte[] publicKeyBytes, String algorithm, byte[] originBytes, byte[] signBytes) throws SignatureException {
    return Signature.verify(publicKeyBytes, KEY_ALGORITHM, algorithm, originBytes, signBytes);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param origin 签名原文
  * @param signBase64 签名Base64
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, String origin, String signBase64) throws SignatureException {
    return Signature.verify(publicKey, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param origin 签名原文
  * @param signBase64 签名Base64
   * @param charset 数据编码
   * @return 签名是否合法
    * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, String origin, String signBase64, String charset) throws SignatureException {
    return Signature.verify(publicKey, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64, charset);
  }

  /**
  * 验证签名
   * @param publicKey 公钥
   * @param algorithm 算法
  * @param origin 签名原文
  * @param signBase64 签名Base64
  * @param charset 数据编码
  * @return 签名是否合法
  * @throws SignatureException 无法验证签名/不支持的编码
  */
  public static boolean verify(PublicKey publicKey, String algorithm, String origin, String signBase64, String charset) throws SignatureException {
    return Signature.verify(publicKey, KEY_ALGORITHM, SIGN_ALGORITHM, origin, signBase64, charset);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, byte[] originBytes, byte[] signBytes) throws SignatureException {
    return Signature.verify(publicKey, KEY_ALGORITHM, SIGN_ALGORITHM, originBytes, signBytes);
  }

  /**
   * 验证签名
   * @param publicKey 公钥
   * @param algorithm 密钥算法
   * @param originBytes 签名原文字节码
   * @param signBytes 签名字节码
   * @return 签名是否合法
   * @throws SignatureException 无法验证签名/不支持的编码
   */
  public static boolean verify(PublicKey publicKey, String algorithm, byte[] originBytes, byte[] signBytes) throws SignatureException {
    return Signature.verify(publicKey, KEY_ALGORITHM, algorithm, originBytes, signBytes);
  }

  /**
   * 加密 
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param content 明文
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, int keyModulusLength, String content) throws CipherException {
    return AsymmetricCipher.encrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, content);
  }

  /**
   * 加密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, int keyModulusLength, String content, String charset) throws CipherException {
    return AsymmetricCipher.encrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, content, charset);
  }

  /**
   * 加密 
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param algorithm 算法
   * @param content 明文
   * @param charset 数据编码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static String encrypt(Key key, int keyModulusLength, String algorithm, String content, String charset) throws CipherException {
    return AsymmetricCipher.encrypt(key, keyModulusLength, KEY_ALGORITHM, algorithm, content, charset);
  }

  /**
   * 加密 
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(Key key, int keyModulusLength, byte[] contentBytes) throws CipherException {
    return AsymmetricCipher.encrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, contentBytes);
  }

  /**
   * 加密 
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param algorithm 算法
   * @param contentBytes 明文字节码
   * @return 密文
   * @throws CipherException 加密失败/不支持的编码
   */
  public static byte[] encrypt(Key key, int keyModulusLength, String algorithm, byte[] contentBytes) throws CipherException {
    return AsymmetricCipher.encrypt(key, keyModulusLength, KEY_ALGORITHM, algorithm, contentBytes);
  }

  /**
   * 解密 
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param cipherContentBase64 密文Base64
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, int keyModulusLength, String cipherContentBase64) throws CipherException {
    return AsymmetricCipher.decrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, cipherContentBase64);
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, int keyModulusLength, String cipherContentBase64, String charset) throws CipherException {
    return AsymmetricCipher.decrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param algorithm 算法
   * @param cipherContentBase64 密文Base64
   * @param charset 数据编码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static String decrypt(Key key, int keyModulusLength, String algorithm, String cipherContentBase64, String charset) throws CipherException {
    return AsymmetricCipher.decrypt(key, keyModulusLength, KEY_ALGORITHM, algorithm, cipherContentBase64, charset);
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(Key key, int keyModulusLength, byte[] cipherContentBytes) throws CipherException {
    return AsymmetricCipher.decrypt(key, keyModulusLength, KEY_ALGORITHM, ENCRYPT_ALGORITHM, cipherContentBytes);
  }

  /**
   * 解密
   * @param key Key
   * @param keyModulusLength Key的Modulus长度
   * @param algorithm 算法
   * @param cipherContentBytes 密文字节码
   * @return 明文
   * @throws CipherException 解密失败/不支持的编码
   */
  public static byte[] decrypt(Key key, int keyModulusLength, String algorithm, byte[] cipherContentBytes) throws CipherException {
    return AsymmetricCipher.decrypt(key, keyModulusLength, KEY_ALGORITHM, algorithm, cipherContentBytes);
  }

}
