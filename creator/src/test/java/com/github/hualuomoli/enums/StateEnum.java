package com.github.hualuomoli.enums;

/**
 * 数据状态
 */
public enum StateEnum {

  /** 正常 */
  NOMAL(1),
  /** 已删除 */
  DELETED(9);

  private int value;

  private StateEnum(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }

}
