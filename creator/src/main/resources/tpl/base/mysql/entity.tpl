package ${packageName}.entity;

// ${comment!''}
public class ${javaName} {

  <#list columns as column>
  /** ${column.comment!''} */
  private ${column.javaTypeName} ${column.javaName};
  </#list>

  public ${javaName}() {
  }

  // getter and setter
  <#list columns as column>

  public ${column.javaTypeName} get${column.javaName?cap_first}() {
    return this.${column.javaName};
  }

  public void set${column.javaName?cap_first}(${column.javaTypeName} ${column.javaName}) {
    this.${column.javaName} = ${column.javaName};
  }
  </#list>

  @Override
  public String toString() {
    return "${javaName} [" //
        <#list columns as column>
        + "<#if column_index gt 0>, </#if>${column.javaName}=" + ${column.javaName} //
        </#list>
        + "]";
  }

}