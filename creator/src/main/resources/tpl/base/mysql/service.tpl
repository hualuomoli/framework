package ${packageName}.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.framework.entity.Page;
import com.github.hualuomoli.framework.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import com.github.hualuomoli.framework.util.CollectionUtils;
import ${packageName}.entity.${javaName};
import ${packageName}.mapper.${javaName}BaseMapper;

// ${comment!''}
@Service(value = "${packageName}.service.${javaName}BaseService")
@Transactional(readOnly = true)
public class ${javaName}BaseService {

  @Autowired
  private ${javaName}BaseMapper ${javaName?uncap_first}BaseMapper;
  <#list columns as column>
    <#if column.primary>

  /** 根据主键${column.javaName}查询 */
  public ${javaName} get(${column.javaTypeName} ${column.javaName}) {
    <#if column.javaTypeName == 'java.lang.String'>
    if (StringUtils.isBlank(${column.javaName})) {
      return null;
    }
    <#else>
    if (${column.javaName} == null) {
      return null;
    }
    </#if>

    return ${javaName?uncap_first}BaseMapper.get(${column.javaName});
  }
    </#if>
  </#list>
  <#list uniques as unique>

  /** 根据唯一索引查询 */
  public ${javaName} findBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${unique.firstJavaColumn.javaTypeName} ${unique.firstJavaColumn.javaName}<#list unique.nextJavaColumns as nextJavaColumn>, ${nextJavaColumn.javaTypeName} ${nextJavaColumn.javaName}</#list>) {
    <#-- 第一个主键 -->
    <#if unique.firstJavaColumn.javaTypeName == 'java.lang.String'>
    if (StringUtils.isBlank(${unique.firstJavaColumn.javaName})) {
      return null;
    }
    <#else>
    if (${unique.firstJavaColumn.javaName} == null) {
      return null;
    }
    </#if>
    <#-- 后面的主键,如联合主键 -->
    <#list unique.nextJavaColumns as nextJavaColumn>
    <#if nextJavaColumn.javaTypeName == 'java.lang.String'>
    if (StringUtils.isBlank(${nextJavaColumn.javaName})) {
      return null;
    }
    <#else>
    if (${nextJavaColumn.javaName} == null) {
      return null;
    }
    </#if>
    </#list>

    ${javaName} ${javaName?uncap_first} = new ${javaName}();
    ${javaName?uncap_first}.set${unique.firstJavaColumn.javaName?cap_first}(${unique.firstJavaColumn.javaName});
    <#list unique.nextJavaColumns as nextJavaColumn>
    ${javaName?uncap_first}.set${nextJavaColumn.javaName?cap_first}(${nextJavaColumn.javaName});
    </#list>
    List<${javaName}> list = ${javaName?uncap_first}BaseMapper.findList(${javaName?uncap_first});
    if (list == null || list.size() == 0) {
      return null;
    }
    Validate.isTrue(list.size() == 1, "More Data found.");
    return list.get(0);
  }
  </#list>

  /** 添加 */
  @Transactional(readOnly = false)
  public int insert(${javaName} ${javaName?uncap_first}) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");

    return ${javaName?uncap_first}BaseMapper.insert(${javaName?uncap_first});
  }

  /** 批量添加 */
  @Transactional(readOnly = false)
  public <T extends ${javaName}> int batchInsert(List<T> list, int fetchSize) {
    if (list == null || list.size() == 0) {
      return 0;
    }
    List<List<T>> splits = CollectionUtils.split(list, fetchSize);
    int count = 0;
    for (int i = 0; i < splits.size(); i++) {
      count += ${javaName?uncap_first}BaseMapper.batchInsert(splits.get(i));
    }
    return count;
  }
  <#list columns as column>
    <#if column.primary>

  /** 根据主键${column.javaName}修改全部信息 */
  @Transactional(readOnly = false)
  public int update(${javaName} ${javaName?uncap_first}) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");

    return ${javaName?uncap_first}BaseMapper.update(${javaName?uncap_first});
  }
    </#if>
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键${column.javaName}修改 */
  @Transactional(readOnly = false)
  public int updateBy${column.javaName?cap_first}(${column.javaTypeName} ${column.javaName?uncap_first}, ${javaName} ${javaName?uncap_first}) {
    Validate.notNull(${column.javaName?uncap_first}, "${column.javaName?uncap_first} is null.");
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");

    ${javaName?uncap_first}.set${column.javaName?cap_first}(${column.javaName?uncap_first});
    return ${javaName?uncap_first}BaseMapper.updateBy${column.javaName?cap_first}(${javaName?uncap_first});
  }
    </#if>
  </#list>
  <#list uniques as unique>

  /** 根据唯一索引修改 */
  @Transactional(readOnly = false)
  public int updateBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${unique.firstJavaColumn.javaTypeName} ${unique.firstJavaColumn.javaName}<#list unique.nextJavaColumns as nextJavaColumn>, ${nextJavaColumn.javaTypeName} ${nextJavaColumn.javaName}</#list>, ${javaName} ${javaName?uncap_first}) {
    Validate.notNull(${unique.firstJavaColumn.javaName}, "${unique.firstJavaColumn.javaName} is null.");
    <#list unique.nextJavaColumns as nextJavaColumn>
    Validate.notNull(${nextJavaColumn.javaName}, "${nextJavaColumn.javaName} is null.");
    </#list>
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");

    ${javaName?uncap_first}.set${unique.firstJavaColumn.javaName?cap_first}(${unique.firstJavaColumn.javaName});
    <#list unique.nextJavaColumns as nextJavaColumn>
    ${javaName?uncap_first}.set${nextJavaColumn.javaName?cap_first}(${nextJavaColumn.javaName});
    </#list>
    return ${javaName?uncap_first}BaseMapper.updateBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${javaName?uncap_first});
  }
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键删除 */
  @Transactional(readOnly = false)
  public int delete(${column.javaTypeName} ${column.javaName}) {
    Validate.notNull(${column.javaName}, "${column.javaName} is null.");

    return ${javaName?uncap_first}BaseMapper.delete(${column.javaName});
  }
    </#if>
  </#list>
  <#list uniques as unique>

  /** 根据唯一索引删除 */
  @Transactional(readOnly = false)
  public int deleteBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${unique.firstJavaColumn.javaTypeName} ${unique.firstJavaColumn.javaName}<#list unique.nextJavaColumns as nextJavaColumn>, ${nextJavaColumn.javaTypeName} ${nextJavaColumn.javaName}</#list>) {
    Validate.notNull(${unique.firstJavaColumn.javaName}, "${unique.firstJavaColumn.javaName} is null.");
    <#list unique.nextJavaColumns as nextJavaColumn>
    Validate.notNull(${nextJavaColumn.javaName}, "${nextJavaColumn.javaName} is null.");
    </#list>

    ${javaName} ${javaName?uncap_first} = new ${javaName}();
    ${javaName?uncap_first}.set${unique.firstJavaColumn.javaName?cap_first}(${unique.firstJavaColumn.javaName});
    <#list unique.nextJavaColumns as nextJavaColumn>
    ${javaName?uncap_first}.set${nextJavaColumn.javaName?cap_first}(${nextJavaColumn.javaName});
    </#list>
    return ${javaName?uncap_first}BaseMapper.deleteBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${javaName?uncap_first});
  }
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键批量删除 */
  @Transactional(readOnly = false)
  public int deleteByArray(${column.javaTypeName}[] ${column.javaName}s) {
    if (${column.javaName}s == null || ${column.javaName}s.length == 0) {
      return 0;
    }
    return ${javaName?uncap_first}BaseMapper.deleteByArray(${column.javaName}s);
  }
    </#if>
  </#list>

  /** 查询列表 */
  public List<${javaName}> findList(${javaName} ${javaName?uncap_first}) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");

    return ${javaName?uncap_first}BaseMapper.findList(${javaName?uncap_first});
  }

  /** 查询列表排序 */
  public List<${javaName}> findList(${javaName} ${javaName?uncap_first}, String orderBy) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");
    if (StringUtils.isBlank(orderBy)) {
      return new ArrayList<${javaName}>();
    }

    // 设置排序
    PaginationInterceptor.setListOrder(orderBy);
    // 查询列表
    return ${javaName?uncap_first}BaseMapper.findList(${javaName?uncap_first});
  }

  /** 查询分页 */
  public Page findPage(${javaName} ${javaName?uncap_first}, Integer pageNo, Integer pageSize) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");
    Validate.notNull(pageNo, "pageNo is null.");
    Validate.isTrue(pageNo > 0, "invalid pageNo.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.isTrue(pageSize > 0, "invalid pageSize.");

    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize);
    // 查询
    List<${javaName}> list = ${javaName?uncap_first}BaseMapper.findList(${javaName?uncap_first});
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

  /** 查询分页 */
  public Page findPage(${javaName} ${javaName?uncap_first}, Integer pageNo, Integer pageSize, String orderBy) {
    Validate.notNull(${javaName?uncap_first}, "${javaName?uncap_first} is null.");
    Validate.notNull(pageNo, "pageNo is null.");
    Validate.isTrue(pageNo > 0, "invalid pageNo.");
    Validate.notNull(pageSize, "pageSize is null.");
    Validate.isTrue(pageSize > 0, "invalid pageSize.");
    if (StringUtils.isBlank(orderBy)) {
      return new Page(pageNo, pageSize, 0, new ArrayList<${javaName}>());
    }

    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize, orderBy);
    // 查询
    List<${javaName}> list = ${javaName?uncap_first}BaseMapper.findList(${javaName?uncap_first});
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

}
