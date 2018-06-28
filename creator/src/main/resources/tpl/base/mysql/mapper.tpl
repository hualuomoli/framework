package ${packageName}.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ${packageName}.entity.${javaName};

// ${comment!''}
@Repository(value = "${packageName}.mapper.${javaName}BaseMapper")
public interface ${javaName}BaseMapper {
  <#list columns as column>
    <#if column.primary>

  /** 根据主键${column.javaName}查询 */
  ${javaName} get(${column.javaTypeName} ${column.javaName});
    </#if>
  </#list>

  /** 添加 */
  int insert(${javaName} ${javaName?uncap_first});

  /** 批量添加 */
  <T extends ${javaName}> int batchInsert(@Param(value = "list") List<T> list);
  <#list columns as column>
    <#if column.primary>

  /** 根据主键修改全部信息 */
  int update(${javaName} ${javaName?uncap_first});
    </#if>
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键修改 */
  int updateBy${column.javaName?cap_first}(${javaName} ${javaName?uncap_first});
    </#if>
  </#list>
  <#list uniques as unique>

  /** 根据唯一索引修改 */
  int updateBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${javaName} ${javaName?uncap_first});
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键删除 */
  int delete(${column.javaTypeName} ${column.javaName});
    </#if>
  </#list>
  <#list uniques as unique>

  /** 根据唯一索引删除 */
  int deleteBy${unique.firstJavaColumn.javaName?cap_first}<#list unique.nextJavaColumns as nextJavaColumn>And${nextJavaColumn.javaName?cap_first}</#list>(${javaName} ${javaName?uncap_first});
  </#list>
  <#list columns as column>
    <#if column.primary>

  /** 根据主键批量删除 */
  int deleteByArray(@Param(value = "${column.javaName}s") ${column.javaTypeName}[] ${column.javaName});
    </#if>
  </#list>

  /** 查询列表 */
  List<${javaName}> findList(${javaName} ${javaName?uncap_first});

}
