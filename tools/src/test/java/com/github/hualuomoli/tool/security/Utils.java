package com.github.hualuomoli.tool.security;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;

import com.github.hualuomoli.tool.security.config.Config;

public class Utils {

  /**
   * 读取长内容
   * @return 内容
   */
  public static String readContent() {
    try {
      String path = Utils.class.getClassLoader().getResource(".").getPath();
      path = path.substring(0, path.indexOf("/target"));
      return FileUtils.readFileToString(new File(path + "/src/test/java", "/com/github/hualuomoli/tool/security/long.txt"), Config.CHARSET);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取内容字节信息
   * @param content 内容
   * @return 字节信息
   */
  public static byte[] getBytes(String content) {
    try {
      return content.getBytes(Config.CHARSET);
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }

}
