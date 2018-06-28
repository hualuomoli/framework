package ${packageName}.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.hualuomoli.framework.entity.Page;
import com.github.hualuomoli.framework.plugin.mybatis.interceptor.pagination.PaginationInterceptor;
import ${packageName}.entity.${javaName};
import ${packageName}.query.entity.${javaName}Query;
import ${packageName}.query.mapper.${javaName}QueryMapper;

// ${comment!''}
@Service(value = "${packageName}.query.service.${javaName}QueryService")
@Transactional(readOnly = true)
public class ${javaName}QueryService {

  @Autowired
  private ${javaName}QueryMapper ${javaName?uncap_first}QueryMapper;

  /** 查询列表 */
  public List<${javaName}> findList(${javaName}Query ${javaName?uncap_first}Query) {
    return ${javaName?uncap_first}QueryMapper.findList(${javaName?uncap_first}Query);
  }

  /** 查询列表排序 */
  public List<${javaName}> findList(${javaName}Query ${javaName?uncap_first}Query, String orderBy) {
    // 设置排序
    PaginationInterceptor.setListOrder(orderBy);
    // 查询列表
    return ${javaName?uncap_first}QueryMapper.findList(${javaName?uncap_first}Query);
  }

  /** 查询分页 */
  public Page findPage(${javaName}Query ${javaName?uncap_first}Query, Integer pageNo, Integer pageSize) {
    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize);
    // 查询
    List<${javaName}> list = ${javaName?uncap_first}QueryMapper.findList(${javaName?uncap_first}Query);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

  /** 查询分页 */
  public Page findPage(${javaName}Query ${javaName?uncap_first}Query, Integer pageNo, Integer pageSize, String orderBy) {
    // 设置分页
    PaginationInterceptor.setPagination(pageNo, pageSize, orderBy);
    // 查询
    List<${javaName}> list = ${javaName?uncap_first}QueryMapper.findList(${javaName?uncap_first}Query);
    // 总数量
    int count = PaginationInterceptor.getCount();
    // 组装
    return new Page(pageNo, pageSize, count, list);
  }

}
