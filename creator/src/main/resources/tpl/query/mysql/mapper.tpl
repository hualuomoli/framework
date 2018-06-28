package ${packageName}.query.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import ${packageName}.entity.${javaName};
import ${packageName}.query.entity.${javaName}Query;

// ${comment!''}
@Repository(value = "${packageName}.query.mapper.${javaName}QueryMapper")
public interface ${javaName}QueryMapper {

  /** 查询列表 */
  List<${javaName}> findList(${javaName}Query ${javaName?uncap_first}Query);

}
