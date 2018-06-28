package com.github.hualuomoli.sample.framework.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.github.hualuomoli.sample.enums.StateEnum;

//自定义枚举转换器
@MappedJdbcTypes(includeNullJdbcType = true, value = {})
@MappedTypes(value = { StateEnum.class })
public class StateTypeHandler extends BaseTypeHandler<StateEnum> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, StateEnum parameter, JdbcType jdbcType) throws SQLException {
    ps.setInt(i, parameter.value());
  }

  @Override
  public StateEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
    int value = rs.getInt(columnName);
    return this.get(value);
  }

  @Override
  public StateEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    int value = rs.getInt(columnIndex);
    return this.get(value);
  }

  @Override
  public StateEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    int value = cs.getInt(columnIndex);
    return this.get(value);
  }

  private StateEnum get(int value) {
    StateEnum[] values = StateEnum.values();
    for (StateEnum state : values) {
      if (state.value() == value) {
        return state;
      }
    }
    return null;
  }

}
