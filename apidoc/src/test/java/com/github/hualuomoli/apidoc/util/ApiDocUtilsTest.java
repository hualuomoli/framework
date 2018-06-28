package com.github.hualuomoli.apidoc.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.hualuomoli.apidoc.entity.ApiDoc;
import com.github.hualuomoli.apidoc.entity.Server;
import com.github.hualuomoli.apidoc.filter.Filter;

public class ApiDocUtilsTest {

  private static final Logger logger = LoggerFactory.getLogger(ApiDocUtils.class);

  private String apiPath = null;

  @Before
  public void before() {
    String path = ApiDocUtils.class.getClassLoader().getResource(".").getPath();
    logger.debug("path={}", path);
    apiPath = path.substring(0, path.lastIndexOf("/target")) + "/src/test/java";
    logger.debug("apiPath={}", apiPath);
  }

  @Test
  public void test() throws IOException {
    List<ApiDoc> docs = ApiDocUtils.getApiDocs(apiPath, "UTF-8", new Filter() {

      @Override
      public boolean support(String packageName, File file) {
        return packageName.startsWith("com.github.hualuomoli.apidoc.entity");
      }
    });
    logger.info("docs={}", JSON.toJSONString(docs));

    List<Server> servers = new ArrayList<Server>();
    servers.add(new Server("正式环境", "http://www.baidu.com"));

    ApiDocUtils.flush(docs, "E:/apidoc", servers);

  }

}
