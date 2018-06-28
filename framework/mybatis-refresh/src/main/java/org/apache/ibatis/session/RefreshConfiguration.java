package org.apache.ibatis.session;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.parsing.XNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshConfiguration extends Configuration {

  private static final Logger logger = LoggerFactory.getLogger(RefreshConfiguration.class);

  public static Boolean inited = false;
  protected final Map<String, XNode> sqlFragments = new RefreshStrictMap<XNode>("XML fragments parsed from previous mappers");

  private static RefreshConfiguration CONFIGURATION = new RefreshConfiguration();

  public static RefreshConfiguration getInstance() {
    return CONFIGURATION;
  }

  public RefreshConfiguration() {
    super();
    CONFIGURATION = this;
  }

  public Set<String> getLoadedResources() {
    return super.loadedResources;
  }

  @Override
  public Map<String, XNode> getSqlFragments() {
    return sqlFragments;
  }

  @Override
  public void addMappedStatement(MappedStatement ms) {
    if (inited) {
      logger.debug("refresh mappedStatement id {}", ms.getId());
      super.mappedStatements.remove(ms.getId());
    }
    super.addMappedStatement(ms);
  }

  @Override
  public void addResultMap(ResultMap rm) {
    if (inited) {
      logger.debug("refresh resultMap id {}", rm.getId());
      super.resultMaps.remove(rm.getId());
    }
    super.addResultMap(rm);
  }

  @Override
  public void addParameterMap(ParameterMap pm) {
    if (inited) {
      logger.debug("refresh parameterMap id {}", pm.getId());
      super.parameterMaps.remove(pm.getId());
    }
    super.addParameterMap(pm);
  }

  @Override
  public void addCache(Cache cache) {
    if (inited) {
      logger.debug("refresh cache id {}", cache.getId());
      super.caches.remove(cache.getId());
    }
    super.addCache(cache);
  }

  @Override
  public void addKeyGenerator(String id, KeyGenerator keyGenerator) {
    if (inited) {
      logger.debug("refresh keyGenerator id {}", id);
      super.keyGenerators.remove(id);
    }
    super.addKeyGenerator(id, keyGenerator);
  }

  @SuppressWarnings("serial")
  public static class RefreshStrictMap<V> extends Configuration.StrictMap<V> {

    public RefreshStrictMap(String name) {
      super(name);
    }

    @Override
    public V put(String key, V value) {
      if (inited) {
        logger.debug("refresh sqlFragment id {}", key);
        super.remove(key);
      }
      return super.put(key, value);
    }

  }
  // end inner class
}
