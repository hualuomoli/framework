package com.github.hualuomoli.framework.plugin.mybatis.interceptor.pagination;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.github.hualuomoli.framework.plugin.mybatis.interceptor.BaseInterceptor;
import com.github.hualuomoli.framework.util.ReflectionUtils;

/**
 * 数据库分页插件，只拦截查询语句.
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PaginationInterceptor extends BaseInterceptor {

  private static final long serialVersionUID = 2276302599945811333L;

  private static final ThreadLocal<QueryInfo> LOCAL_QUERY_INFO = new ThreadLocal<QueryInfo>();
  private static final ThreadLocal<Integer> LOCAL_COUNT = new ThreadLocal<Integer>();

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    QueryInfo info = LOCAL_QUERY_INFO.get();

    if (info == null) {
      return invocation.proceed();
    }

    try {
      switch (info.queryType) {
      case PAGE:
        return this.page(invocation);
      case LIST:
        return this.list(invocation);
      case COUNT:
        return this.count(invocation);
      default:
        break;
      }
    } finally {
      LOCAL_QUERY_INFO.remove();
    }
    return invocation.proceed();
  }

  // page
  private Object page(Invocation invocation) throws Throwable {
    QueryInfo info = LOCAL_QUERY_INFO.get();

    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    Object parameter = invocation.getArgs()[1];
    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
    String originalSql = boundSql.getSql().trim();

    // 执行统计
    Executor exec = (Executor) invocation.getTarget();
    Connection conn = exec.getTransaction().getConnection();
    int count = PaginationSQLHelper.getCount(originalSql, conn, mappedStatement, parameter, boundSql, logger);
    LOCAL_COUNT.set(count);
    if (count <= 0) {
      return new ArrayList<Object>();
    }

    // 查询数据
    // 分页查询 本地化对象 修改数据库注意修改实现
    String pageSql = PaginationSQLHelper.generatePageSql(originalSql, info.pageNumber, info.pageSize, info.orderBy, dialect);
    invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
    BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
    // 解决MyBatis 分页foreach 参数失效 start
    if (ReflectionUtils.getFieldValue(boundSql, "metaParameters") != null) {
      MetaObject mo = (MetaObject) ReflectionUtils.getFieldValue(boundSql, "metaParameters");
      ReflectionUtils.setFieldValue(newBoundSql, "metaParameters", mo);
    }
    // 解决MyBatis 分页foreach 参数失效 end
    MappedStatement newMs = PaginationSQLHelper.createStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

    invocation.getArgs()[0] = newMs;

    // end
    return invocation.proceed();
  }

  // list
  private Object list(Invocation invocation) throws Throwable {
    QueryInfo info = LOCAL_QUERY_INFO.get();
    final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

    Object parameter = invocation.getArgs()[1];
    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
    // 查询列表排序

    String originalSql = boundSql.getSql().trim();

    // 查询数据
    String orderSql = PaginationSQLHelper.generateOrderSql(originalSql, info.orderBy, dialect);
    invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
    BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), orderSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
    // 解决MyBatis 分页foreach 参数失效 start
    if (ReflectionUtils.getFieldValue(boundSql, "metaParameters") != null) {
      MetaObject mo = (MetaObject) ReflectionUtils.getFieldValue(boundSql, "metaParameters");
      ReflectionUtils.setFieldValue(newBoundSql, "metaParameters", mo);
    }
    // 解决MyBatis 分页foreach 参数失效 end
    MappedStatement newMs = PaginationSQLHelper.createStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));

    invocation.getArgs()[0] = newMs;

    // end
    return invocation.proceed();
  }

  //分页
  private Object count(Invocation invocation) throws Throwable {
    MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
    Object parameter = invocation.getArgs()[1];
    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
    String originalSql = boundSql.getSql().trim();

    // 执行统计
    Executor exec = (Executor) invocation.getTarget();
    Connection conn = exec.getTransaction().getConnection();
    int count = PaginationSQLHelper.getCount(originalSql, conn, mappedStatement, parameter, boundSql, logger);
    LOCAL_COUNT.set(count);

    return null;
  }

  /**
   * 设置分页
   * @param pageNumber 当前页码
   * @param pageSize 数据数量
   */
  public static void setPagination(Integer pageNumber, Integer pageSize) {
    Validate.notNull(pageNumber, "pageNumber is null.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.isTrue(pageNumber > 0, "pageNumber must greater zero.");
    Validate.isTrue(pageSize > 0, "pageSize must greater zero.");

    QueryInfo info = getInfo();
    info.queryType = QueryTypeEnum.PAGE;
    info.pageNumber = pageNumber;
    info.pageSize = pageSize;
  }

  /**
   * 设置分页
   * @param pageNumber 当前页码
   * @param pageSize 数据数量
   * @param orderBy 排序
   */
  public static void setPagination(Integer pageNumber, Integer pageSize, String orderBy) {
    Validate.notNull(pageNumber, "pageNumber is null.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.notBlank(orderBy, "orderBy is blank.");
    Validate.isTrue(pageNumber > 0, "pageNumber must greater zero.");
    Validate.isTrue(pageSize > 0, "pageSize must greater zero.");

    QueryInfo info = getInfo();
    info.queryType = QueryTypeEnum.PAGE;
    info.pageNumber = pageNumber;
    info.pageSize = pageSize;
    info.orderBy = orderBy;
  }

  /**
   * 设置列表排序
   * @param orderBy 排序
   */
  public static void setListOrder(String orderBy) {
    Validate.notBlank(orderBy, "orderBy is blank.");

    QueryInfo info = getInfo();
    info.queryType = QueryTypeEnum.LIST;
    info.orderBy = orderBy;
  }

  /**
   * 设置只查询总数量
   */
  public static void setCountQuery() {
    QueryInfo info = getInfo();
    info.queryType = QueryTypeEnum.COUNT;
  }

  /**
   * 获取分页查询的数据总量
   * @return 数据总量
   */
  public static Integer getCount() {
    return LOCAL_COUNT.get();
  }

  private static QueryInfo getInfo() {
    QueryInfo info = LOCAL_QUERY_INFO.get();
    if (info == null) {
      info = new QueryInfo();
      LOCAL_QUERY_INFO.set(info);
    }
    return info;
  }

  private enum QueryTypeEnum {
    PAGE, LIST, COUNT;
  }

  private static class QueryInfo {
    QueryTypeEnum queryType;
    /** 当前页码 */
    Integer pageNumber;
    /** 查询数量 */
    Integer pageSize;
    /** 排序 */
    String orderBy;

    @Override
    public String toString() {
      return "QueryInfo [queryType=" + queryType + ", pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", orderBy=" + orderBy + "]";
    }

  }

}
