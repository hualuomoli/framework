package com.github.hualuomoli.framework.plugin.mybatis.interceptor;

import java.io.Serializable;
import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.framework.plugin.mybatis.dialect.Dialect;

/**
 * Mybatis拦截器基类
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {

  private static final long serialVersionUID = -3796861402381050088L;

  protected static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);

  protected static final String DIALECT = "dialect";
  protected static final String DELEGATE = "delegate";
  protected static final String MAPPED_STATEMENT = "mappedStatement";

  protected Properties properties;
  protected Dialect dialect;

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public void setDialect(Dialect dialect) {
    this.dialect = dialect;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

}
