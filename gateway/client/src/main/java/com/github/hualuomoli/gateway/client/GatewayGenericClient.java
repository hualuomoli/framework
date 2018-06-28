package com.github.hualuomoli.gateway.client;

import java.util.List;
import java.util.Map;

import com.github.hualuomoli.gateway.client.entity.Page;
import com.github.hualuomoli.gateway.client.entity.Request;
import com.github.hualuomoli.gateway.client.entity.Response;
import com.github.hualuomoli.gateway.client.lang.BusinessException;
import com.github.hualuomoli.gateway.client.lang.ClientException;
import com.github.hualuomoli.gateway.client.parser.GenericParser;

/**
 * 客户端
 */
public class GatewayGenericClient<Req extends Request, Res extends Response> extends GatewayClient<Req, Res> {

  private GenericParser genericParser;

  public GatewayGenericClient(String url, String partnerId) {
    super(url, partnerId);
  }

  public GatewayGenericClient<Req, Res> setGenericParser(GenericParser genericParser) {
    this.genericParser = genericParser;
    return this;
  }

  /**
   * 调用返回object
   * @param req 请求
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> T callObject(Object req, Class<T> clazz) throws BusinessException, ClientException {
    return this.callObject(this.getMethod(req), this.getBizContent(req), clazz);
  }

  /**
   * 调用返回object
   * @param method 请求方法
   * @param bizContent 请求业务内容
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> T callObject(String method, String bizContent, Class<T> clazz) throws BusinessException, ClientException {
    String result = this.execute(method, bizContent);

    return jsonParser.parseObject(result, clazz);
  }

  /**
   * 调用返回array
   * @param req 请求
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> List<T> callArray(Object req, Class<T> clazz) throws BusinessException, ClientException {
    return this.callArray(this.getMethod(req), this.getBizContent(req), clazz);
  }

  /**
   * 调用返回object
   * @param method 请求方法
   * @param bizContent 请求业务内容
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> List<T> callArray(String method, String bizContent, Class<T> clazz) throws BusinessException, ClientException {
    String result = this.execute(method, bizContent);

    return jsonParser.parseArray(result, clazz);
  }

  /**
   * 调用返回page
   * @param req 请求
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> Page<T> callPage(Object req, Class<T> clazz) throws BusinessException, ClientException {
    return this.callPage(this.getMethod(req), this.getBizContent(req), clazz);
  }

  /**
   * 调用返回page
   * @param method 请求方法
   * @param bizContent 请求业务内容
   * @param clazz 返回类类型
   * @return 返回实例
   * @throws BusinessException 业务处理异常
   * @throws ClientException 客户端调用异常
   */
  public <T> Page<T> callPage(String method, String bizContent, Class<T> clazz) throws BusinessException, ClientException {
    String result = this.execute(method, bizContent);

    // page name
    GenericParser.PageName names = genericParser.getPageName();

    Map<String, Object> map = jsonParser.parse(result);
    Integer pageNumber = Integer.parseInt(String.valueOf(map.get(names.pageNumber())));
    Integer pageSize = Integer.parseInt(String.valueOf(map.get(names.pageSize())));
    Integer count = Integer.parseInt(String.valueOf(map.get(names.count())));

    List<T> dataList = jsonParser.parseArray(jsonParser.toJsonString(map.get(names.datas())), clazz);
    Page<T> page = new Page<T>();
    page.setPageNumber(pageNumber);
    page.setPageSize(pageSize);
    page.setCount(count);
    page.setDataList(dataList);
    return page;
  }

  /**
   * 获取请求方法
   * @param req 请求
   * @return 请求方法
   */
  private String getMethod(Object req) {
    String method = null;

    if (List.class.isAssignableFrom(req.getClass())) {
      List<?> list = (List<?>) req;
      method = genericParser.getMethod(list.get(0));
    } else {
      method = genericParser.getMethod(req);
    }
    return method;
  }

  /**
   * 获取请求内容
   * @param req 请求
   * @return 请求内容
   */
  private String getBizContent(Object req) {
    return jsonParser.toJsonString(req);
  }

}
