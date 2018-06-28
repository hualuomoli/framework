package com.github.hualuomoli.security.signature;

import com.github.hualuomoli.commons.codec.Base64;
import com.github.hualuomoli.security.RSA;
import com.github.hualuomoli.security.Utils;
import com.github.hualuomoli.security.config.Config;
import com.github.hualuomoli.security.util.KeyUtils;

import org.junit.Assert;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureTest {

  private static final String keyAlgorithm = RSA.KEY_ALGORITHM;
  private static final String algorithm = RSA.SIGN_SHA1_WITH_RSA;
  private static final String charset = Config.CHARSET;

  private static final String PRIVATE_KEY_BASE64_1024 = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJKqqRWidpP3L1pLGEswIj5RoaI+GZ5JXZidzNu0vDg3XhXbhRyKQihMq3aqTviVOQlVQwZE4jLsubKmx+fmxxhiHTEiNoETA50+9vYImUYWNIQmJLKMfi0YvpH9vNelGDOTaTVFSY3rHFn0llbFm9OT0vMGExvhTJOezt3sun2XAgMBAAECgYAE59Bf+N8ec9YRnxFg8msyymgtlQYmVS3cPOrtLk5dUvzCnmUFX1p3HpldjrS1e8g8l612ODeYnHJZl1p4Vy+xhAZLbrc+afjLjQMcyq8Zp1Jlwvo7s0pvrmLv4Rcp22M56muzA0uvzKAt8yDhW2CQ2gcvtFjUj36izaf/A//2gQJBANuVsqlY3EgeGAza0k8W0Or8PhkIN2lv1hg/agXuk5GbkD5HKDqH7OJ1PjmIMtw4mMpZMleYpuaHkIq6fuvJEMUCQQCq/UeyevFDg7UZ+VwKVvkXIFKA90NeIowpTOf2+7PlHLgpf8gFTWnfi7ZwcoWEQgj3QI7AKu9V9VPcoOfhS8KrAkEAnV8vGVF3JjRzNhtJqKcIebDn7FbIJ8egH+aq3K98kVl1Sj0DJtetvSsbum3TTaM+VZ8RhlgAHa3AuLwqJGAIpQJBAIgR4NF2prI8iIzv9D/CMJ0nuqAopPKveWyQZ/QTH3iO3zsfFdSqYztjGj7n07qJNktUk4T3Zdi4zxC+92hBetsCQGUYRCAYmtlD4NK+ALSfyr2jR7SFfEwlP1Q4N8rrz15UCJfLLAF3FcY1FcoqWZ5Kt5td20KZNZa0KYMmi7cVXW4=";
  private static final String PUBLIC_KEY_BASE64_1024 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSqqkVonaT9y9aSxhLMCI+UaGiPhmeSV2YnczbtLw4N14V24UcikIoTKt2qk74lTkJVUMGROIy7Lmypsfn5scYYh0xIjaBEwOdPvb2CJlGFjSEJiSyjH4tGL6R/bzXpRgzk2k1RUmN6xxZ9JZWxZvTk9LzBhMb4UyTns7d7Lp9lwIDAQAB";
  private static final String PRIVATE_KEY_BASE64_2048 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDPkOSxWyBICkVrnwD97FZ1vej/CjGhNOKUPDtm72Fbd459Ac2QyaOAGsPG+RfCEQfAYJwaC88fN+TKBtQp5Ww/KnicVJPgkCeVvIzPUv+QSbgTlPE1u1/7RyA2Y8SJU7OGDtVF3DQ9wDcK+frECdJMRPj3ZkmrAUvH3ssuyMMMLunjK97Jv0yAE/Xx6Lwg7jBKeQp31cb6vl98SKqJSdeCHp7iQCfF+4CnBuWngzmrO/+n98mb7X9MCgtUMheNXOmj2kEIyf/rTqJHZ2a/nCCnj7EIh1EBIHXeDCAr2G68OjkxoKsUB8d0VJY+09fVkJT/8R3aNAPOEn45CN2P4lRBAgMBAAECggEBAK00m6HkCOwUphlIdetubDeUYflw6Dk9iFVqbadX2kYZXi2yc3d+y5D6xKQk+E5ZJ77A34JcmFnUqnZrfF/5snqDVmIdsciSgnXRAS+AcwJUgebVQvMziMSGdbrtRfPShNw5RH8nYfCllUjzkO5Fw9chNyA7mv239ySyJZrErP5cJNCQvfYqi5tqNojEj5LiYHp7CXvhl8s2p5ftpu2OY98615nz6ElYtMhjiFpQdwNC9KGOhoA7H4+l9zxGE97aCX/y8AOTPr4gnbS8Gb/qoXdH2xk1OEeMp+cgIanbTzFrlhtbr01NH7bhdGUOw65RshyByvbkL3kjDi3YQaHQRoECgYEA/3SkxCFTLlQF8nOlfOn9MI+k/Nf6h/hE4c5ovotS/WgZaQu74loEVWRdlzxvFgrIaM5U+qUA7MBNcnh0J8voqJ73iH/ZzkRN7j5pATFg/zDHcob6oIHxGF3tHBkrogvoYR1bEOKCdg//qqBQvexHYfmsCIKvO5W33NfSZVyCta8CgYEA0AIf+h64gU8BMnCnfurpRMzTIsm/SsiWA4cgydx1y/UG5qYUNsExv2cY+aTu6Ko3aNeGKOwhmdXcBZllLqWG8HUZUkTqfarAKTmObo/btGd1nvgcaTWcyw7Wn6nVIIO0hhvUAfWekoi5+rfQDhdSomfX2QI229sYPKNkYlu8AQ8CgYEAo2k3WStELZecMkseKnj3+M96qC902PPNBHFoebNgRDAKSTMzXgldSk6ou+zx1rTRHJIYtDlHDSu6nyZEj4yV6P+/srauWo9BFs0fmJ7AwJ2wiCemPXTCCGdVHStQyRk67py7u2JaODHlx31ECSoTQ65WCR+n/sK4WfJJaYOzLYsCgYBXp12USBfWHdXSTJi4qgNMw8eBVGTw/aABspLFncph/UpALeI8xKLTFn32zWMD1p7s5k2Ztw/dxLon/eEejBBWCfJ2saXi+O5rMN7thl++IQ72w3UtfLMq+oDE3Yt7XSTYkqd/f+Knu0gr+AOLvJZM69eQkhdOBICtJOlrbV7HTwKBgD5pnNgj+zQTb4JHQK3VQF69+DBesvI9J6X/3VNF4A3xQR0MoLJUcvh7QCBaZGnA0na4ZsqzpRk4UDhKnErXyPcmt5neiOFIMD3eLnbR3Bh5PppIkHSxDoWY4oGu+4nACdmDzf3utf5Bml+xZKgYGDTDg8OiyvLD9zQqiw5DsuE9";
  private static final String PUBLIC_KEY_BASE64_2048 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz5DksVsgSApFa58A/exWdb3o/woxoTTilDw7Zu9hW3eOfQHNkMmjgBrDxvkXwhEHwGCcGgvPHzfkygbUKeVsPyp4nFST4JAnlbyMz1L/kEm4E5TxNbtf+0cgNmPEiVOzhg7VRdw0PcA3Cvn6xAnSTET492ZJqwFLx97LLsjDDC7p4yveyb9MgBP18ei8IO4wSnkKd9XG+r5ffEiqiUnXgh6e4kAnxfuApwblp4M5qzv/p/fJm+1/TAoLVDIXjVzpo9pBCMn/606iR2dmv5wgp4+xCIdRASB13gwgK9huvDo5MaCrFAfHdFSWPtPX1ZCU//Ed2jQDzhJ+OQjdj+JUQQIDAQAB";

  private static final byte[] PRIVATE_KEY_BYTES_1024 = Base64.decode(PRIVATE_KEY_BASE64_1024);
  private static final byte[] PUBLIC_KEY_BYTES_1024 = Base64.decode(PUBLIC_KEY_BASE64_1024);
  private static final byte[] PRIVATE_KEY_BYTES_2048 = Base64.decode(PRIVATE_KEY_BASE64_2048);
  private static final byte[] PUBLIC_KEY_BYTES_2048 = Base64.decode(PUBLIC_KEY_BASE64_2048);

  private static final PrivateKey PRIVATE_KEY_1024 = KeyUtils.getPKCS8PrivateKey(PRIVATE_KEY_BASE64_1024, keyAlgorithm);
  private static final PublicKey PUBLIC_KEY_1024 = KeyUtils.getX509PublicKey(PUBLIC_KEY_BASE64_1024, keyAlgorithm);
  private static final PrivateKey PRIVATE_KEY_2048 = KeyUtils.getPKCS8PrivateKey(PRIVATE_KEY_BASE64_2048, keyAlgorithm);
  private static final PublicKey PUBLIC_KEY_2048 = KeyUtils.getX509PublicKey(PUBLIC_KEY_BASE64_2048, keyAlgorithm);

  private static final String shortMessage = "这是一段段的文字信息";
  private static final String longMessage;
  private static final byte[] shortMessageBytes;
  private static final byte[] longMessageBytes;

  static {
    longMessage = Utils.readContent();
    shortMessageBytes = Utils.getBytes(shortMessage);
    longMessageBytes = Utils.getBytes(longMessage);
  }

  @Test
  public void testSignStringStringStringString() {
    String sign = null;
    boolean success = false;

    // 1024位
    sign = Signature.sign(PRIVATE_KEY_BASE64_1024, keyAlgorithm, algorithm, shortMessage);
    success = Signature.verify(PUBLIC_KEY_BASE64_1024, keyAlgorithm, algorithm, shortMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_1024, keyAlgorithm, algorithm, longMessage);
    success = Signature.verify(PUBLIC_KEY_BASE64_1024, keyAlgorithm, algorithm, longMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_2048, keyAlgorithm, algorithm, shortMessage);
    success = Signature.verify(PUBLIC_KEY_BASE64_2048, keyAlgorithm, algorithm, shortMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_2048, keyAlgorithm, algorithm, longMessage);
    success = Signature.verify(PUBLIC_KEY_BASE64_2048, keyAlgorithm, algorithm, longMessage, sign);
    Assert.assertTrue("验证失败", success);
  }

  @Test
  public void testSignStringStringStringStringString() {
    String sign = null;
    boolean success = false;

    // 1024位
    sign = Signature.sign(PRIVATE_KEY_BASE64_1024, keyAlgorithm, algorithm, shortMessage, charset);
    success = Signature.verify(PUBLIC_KEY_BASE64_1024, keyAlgorithm, algorithm, shortMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_1024, keyAlgorithm, algorithm, longMessage, charset);
    success = Signature.verify(PUBLIC_KEY_BASE64_1024, keyAlgorithm, algorithm, longMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_2048, keyAlgorithm, algorithm, shortMessage, charset);
    success = Signature.verify(PUBLIC_KEY_BASE64_2048, keyAlgorithm, algorithm, shortMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_BASE64_2048, keyAlgorithm, algorithm, longMessage, charset);
    success = Signature.verify(PUBLIC_KEY_BASE64_2048, keyAlgorithm, algorithm, longMessage, sign, charset);
    Assert.assertTrue("验证失败", success);
  }

  @Test
  public void testSignByteArrayStringStringByteArray() {
    byte[] signBytes = null;
    boolean success = false;

    // 1024位
    signBytes = Signature.sign(PRIVATE_KEY_BYTES_1024, keyAlgorithm, algorithm, shortMessageBytes);
    success = Signature.verify(PUBLIC_KEY_BYTES_1024, keyAlgorithm, algorithm, shortMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_BYTES_1024, keyAlgorithm, algorithm, longMessageBytes);
    success = Signature.verify(PUBLIC_KEY_BYTES_1024, keyAlgorithm, algorithm, longMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_BYTES_2048, keyAlgorithm, algorithm, shortMessageBytes);
    success = Signature.verify(PUBLIC_KEY_BYTES_2048, keyAlgorithm, algorithm, shortMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_BYTES_2048, keyAlgorithm, algorithm, longMessageBytes);
    success = Signature.verify(PUBLIC_KEY_BYTES_2048, keyAlgorithm, algorithm, longMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);
  }

  @Test
  public void testSignPrivateKeyStringStringString() {
    String sign = null;
    boolean success = false;

    // 1024位
    sign = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, shortMessage);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, shortMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, longMessage);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, longMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, shortMessage);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, shortMessage, sign);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, longMessage);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, longMessage, sign);
    Assert.assertTrue("验证失败", success);
  }

  @Test
  public void testSignPrivateKeyStringStringStringString() {
    String sign = null;
    boolean success = false;

    // 1024位
    sign = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, shortMessage, charset);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, shortMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, longMessage, charset);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, longMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, shortMessage, charset);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, shortMessage, sign, charset);
    Assert.assertTrue("验证失败", success);

    sign = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, longMessage, charset);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, longMessage, sign, charset);
    Assert.assertTrue("验证失败", success);
  }

  @Test
  public void testSignPrivateKeyStringStringByteArray() {
    byte[] signBytes = null;
    boolean success = false;

    // 1024位
    signBytes = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, shortMessageBytes);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, shortMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_1024, keyAlgorithm, algorithm, longMessageBytes);
    success = Signature.verify(PUBLIC_KEY_1024, keyAlgorithm, algorithm, longMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, shortMessageBytes);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, shortMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);

    signBytes = Signature.sign(PRIVATE_KEY_2048, keyAlgorithm, algorithm, longMessageBytes);
    success = Signature.verify(PUBLIC_KEY_2048, keyAlgorithm, algorithm, longMessageBytes, signBytes);
    Assert.assertTrue("验证失败", success);
  }

}
