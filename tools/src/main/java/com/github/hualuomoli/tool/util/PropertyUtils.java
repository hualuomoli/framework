package com.github.hualuomoli.tool.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件加载器
 */
public final class PropertyUtils {

  private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

  /**
   * 加载第一个可加载的配置文件
   * {@linkplain #load(String) }
   * @param filenames 资源文件
   * @return 配置信息
   */
  public static Properties loadFirst(String... filenames) {
    if (filenames == null || filenames.length == 0) {
      return null;
    }

    for (int i = 0, len = filenames.length; i < len; i++) {
      String filename = filenames[i];
      Properties prop = load(filename);
      if (prop != null) {
        return prop;
      }
    }
    return null;
  }

  /**
   * 反转加载第一个可加载的配置文件
   * {@linkplain #load(String) }
   * @param filenames 资源文件
   * @return 配置信息
   */
  public static Properties loadFirstReverse(String... filenames) {
    if (filenames == null || filenames.length == 0) {
      return null;
    }

    for (int i = filenames.length - 1; i >= 0; i--) {
      String filename = filenames[i];
      Properties prop = load(filename);
      if (prop != null) {
        return prop;
      }
    }
    return null;
  }

  /**
   * 加载配置文件,后面的配置文件覆盖前面的
   * {@linkplain #load(String) }
   * @param filenames 资源文件
   * @return 配置信息
   */
  public static Properties loadCover(String... filenames) {
    if (filenames == null || filenames.length == 0) {
      return null;
    }

    Properties p = null;

    for (String filename : filenames) {
      Properties prop = load(filename);
      p = copy(prop, p);
    }
    return p;
  }

  /**
   * 加载配置文件
   * classpath: 当前项目的路径
   * classpath*: 当前项目及jar包的路径,项目配置覆盖jar包配置
   * @param filename 文件名
   * @return 配置信息
   */
  public static Properties load(String filename) {
    if (filename == null || filename.trim().length() == 0) {
      return null;
    }

    if (filename.startsWith("classpath:")) {
      return loadFromClasspath(filename.substring("classpath:".length()));
    }
    if (filename.startsWith("classpath*:")) {
      return loadFromClasspathAndJar(filename.substring("classpath*:".length()));
    }

    return loadFile(filename);
  }

  /**
   * 复制
   * @param src 原
   * @param dest 目的
   * @return 复制后的配置信息
   */
  public static Properties copy(Properties src, Properties dest) {
    if (src == null) {
      return dest;
    }
    if (dest == null) {
      return src;
    }
    // set src configure to dest
    Set<Entry<Object, Object>> set = src.entrySet();
    for (Entry<Object, Object> entry : set) {
      dest.put(entry.getKey(), entry.getValue());
    }
    return dest;
  }

  /**
   * 加载当前项目及jar包下的配置文件,如classpath*:log4j/log4j.properties
   * @param filename 文件名
   * @return 配置信息,如果文件不存在或加载错误返回null
   */
  private static Properties loadFromClasspathAndJar(String filename) {
    List<URL> urlList = new ArrayList<URL>();

    // get url
    try {
      Enumeration<URL> rs = getClassLoader().getResources(filename);
      while (rs.hasMoreElements()) {
        urlList.add(rs.nextElement());
      }
      // sort
      Collections.sort(urlList, new Comparator<URL>() {
        private String prefix = "file:/";

        @Override
        public int compare(URL o1, URL o2) {
          String s1 = o1.getPath();
          String s2 = o2.getPath();
          if (s1.startsWith(prefix) && s2.startsWith(prefix)) {
            return this.compare(s1, s2);
          }
          if (!s1.startsWith(prefix) && !s2.startsWith(prefix)) {
            return this.compare(s1, s2);
          }
          return s1.startsWith(prefix) ? -1 : 1;
        }

        private int compare(String s1, String s2) {
          return s1.compareTo(s2);
        }
      });
    } catch (IOException e) {
      return null;
    }

    Properties p = null;

    for (URL url : urlList) {
      Properties prop = PropertyUtils.loadFromUrl(url);
      p = PropertyUtils.copy(prop, p);
    }

    return p;

  }

  /**
   * 加载classpath下的配置文件,如classpath:log4j/log4j.properties
   * @param filename 文件名
   * @return 配置信息,如果文件不存在或加载错误返回null
   */
  public static Properties loadFromClasspath(String filename) {
    URL url = getClassLoader().getResource(filename);
    return PropertyUtils.loadFromUrl(url);
  }

  /**
   * 加载文件类型的配置文件,如log4j/log4j.properties
   * @param filename 文件名
   * @return 配置信息,如果文件不存在或加载错误返回null
   */
  public static Properties loadFile(String filename) {
    URL url = getClassLoader().getResource(filename);
    return PropertyUtils.loadFromUrl(url);
  }

  /**
   * 加载配置文件,如果无法加载返回null
   * @param url 资源URL
   * @return 资源配置信息,如果资源不存在或格式错误返回null
   */
  public static Properties loadFromUrl(URL url) {
    if (url == null) {
      return null;
    }

    if (logger.isInfoEnabled()) {
      logger.info("load properties from {}", url.getPath());
    }

    InputStream is = null;

    try {
      Properties prop = new Properties();
      is = url.openStream();
      prop.load(is);
      return prop;
    } catch (Exception e) {
      logger.warn("can't load properties {}", url.getPath(), e);
      return null;
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
        }
      }
    }
    // end
  }

  /**
   * 获取classloader
   * @return class loader
   */
  private static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

}
