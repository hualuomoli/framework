package org.mybatis.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.builder.xml.RefreshXMLConfigBuilder;
import org.apache.ibatis.builder.xml.RefreshXMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RefreshConfiguration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

public class RefreshSqlSessionFactoryBean extends SqlSessionFactoryBean {

  private static final Logger logger = LoggerFactory.getLogger(RefreshSqlSessionFactoryBean.class);

  private Boolean startup;
  private Integer waitSeconds;

  private Resource[] mapperLocations;
  private Configuration configuration;
  private Resource configLocation;
  private Properties configurationProperties;

  public void setStartup(Boolean startup) {
    this.startup = startup;
  }

  public void setWaitSeconds(Integer waitSeconds) {
    this.waitSeconds = waitSeconds;
  }

  @Override
  public void setMapperLocations(Resource[] mapperLocations) {
    super.setMapperLocations(mapperLocations);
    this.mapperLocations = mapperLocations;
  }

  @Override
  public void setConfiguration(Configuration configuration) {
    super.setConfiguration(configuration);
    this.configuration = configuration;
  }

  @Override
  public void setConfigLocation(Resource configLocation) {
    this.configLocation = configLocation;
  }

  public void setConfigurationProperties(Properties configurationProperties) {
    super.setConfigurationProperties(configurationProperties);
    this.configurationProperties = configurationProperties;
  }

  @Override
  protected SqlSessionFactory buildSqlSessionFactory() throws IOException {

    // 设置configLocation配置的信息
    if (this.configLocation != null) {
      RefreshXMLConfigBuilder xmlConfigBuilder = new RefreshXMLConfigBuilder(configuration, this.configLocation.getInputStream(), null, this.configurationProperties);
      xmlConfigBuilder.parse();
    }

    SqlSessionFactory factory = super.buildSqlSessionFactory();

    if (startup != null && startup) {
      System.out.println("启动Mybatis修改自动刷新......");
      RefreshConfiguration.inited = true;
      new SyncRefreshDealer(factory, mapperLocations, waitSeconds).start();
    }

    return factory;
  }

  /**
   * 刷新资源
   * @param resource 资源文件
   * @param configuration 配置信息
   * @throws NestedIOException 异常
   */
  private void refresh(String resource, Configuration configuration) throws NestedIOException {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(resource);
      RefreshXMLMapperBuilder xmlMapperBuilder = new RefreshXMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
      xmlMapperBuilder.parse();
    } catch (Exception e) {
      throw new NestedIOException("Failed to parse mapping resource: '" + resource + "'", e);
    } finally {
      ErrorContext.instance().reset();
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
        }
      }
    }

  }

  class SyncRefreshDealer extends Thread {

    private Configuration configuration;
    private Set<Location> locations;
    private int waitSeconds;

    public SyncRefreshDealer(SqlSessionFactory factory, Resource[] mapperLocations, int waitSeconds) {
      this.configuration = factory.getConfiguration();
      this.locations = new HashSet<Location>();
      this.waitSeconds = waitSeconds;
      long lastModified = System.currentTimeMillis();
      for (Resource resource : mapperLocations) {
        String filename = this.getResourceFilename(resource);
        if (filename == null) {
          continue;
        }
        logger.debug("mybatis资源文件{}", filename);
        locations.add(new Location(filename, lastModified));
      }
    }

    /**
     * 获取资源的文件的绝对路径,如果非文件资源返回null
     * @param resource 资源
     * @return 文件的资源路径
     */
    private String getResourceFilename(Resource resource) {
      try {
        return resource.getFile().getAbsolutePath();
      } catch (IOException e) {
        return null;
      }
      // end 
    }

    @Override
    public void run() {
      while (true) {

        // 刷新
        this.doRefresh(configuration, locations);

        // 休眠
        try {
          Thread.sleep(waitSeconds * 1000);
        } catch (Exception e) {
        }
        // end while
      }
    }

    /**
     * 刷新Mybatis修改的xml文件
     * @param configuration 配置信息
     * @param locations 资源信息
     */
    private void doRefresh(Configuration configuration, Set<Location> locations) {
      for (Location location : locations) {
        File file = new File(location.filename);
        if (!file.exists()) {
          continue;
        }
        if (file.lastModified() <= location.lastModified) {
          continue;
        }
        // do refresh
        logger.info("重新解析文件{}", location.filename);
        this.reload(location.filename, configuration);

        // 修改文件的最后修改时间
        location.lastModified = file.lastModified();
      }
      // end
    }

    /**
     * 重新加载Mybatis资源
     * @param filename xml文件名
     * @param configuration 配置信息
     */
    private void reload(String filename, Configuration configuration) {
      try {
        RefreshSqlSessionFactoryBean.this.refresh(filename, configuration);
      } catch (Exception e) {
        logger.error("", e);
      }
      // end
    }

    class Location {
      private String filename;
      private long lastModified;

      public Location(String filename, long lastModified) {
        super();
        this.filename = filename;
        this.lastModified = lastModified;
      }
    }

  }

}
