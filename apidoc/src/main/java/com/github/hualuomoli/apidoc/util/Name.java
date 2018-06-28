package com.github.hualuomoli.apidoc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

class Name {

  private static final Properties prop;

  static final String API_MODULE;
  static final String API_METHOD;
  static final String API_TITLE;
  static final String API_ERROR;

  static final String PARAMETER_REQUIRED;
  static final String PARAMETER_MAXLENGTH;
  static final String PARAMETER_SAMPLE;

  static {
    prop = init();
    API_MODULE = prop.getProperty("api.module");
    API_METHOD = prop.getProperty("api.method");
    API_TITLE = prop.getProperty("api.title");
    API_ERROR = prop.getProperty("api.error");

    PARAMETER_REQUIRED = prop.getProperty("parameter.required");
    PARAMETER_MAXLENGTH = prop.getProperty("parameter.maxLength");
    PARAMETER_SAMPLE = prop.getProperty("parameter.sample");
  }

  private static Properties init() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    URL url = loader.getResource("apidoc.properties");

    if (url == null) {
      return null;
    }

    InputStream is = null;
    try {
      Properties prop = new Properties();
      is = url.openStream();
      prop.load(is);
      return prop;
    } catch (Exception e) {
      throw new RuntimeException(e);
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

}
