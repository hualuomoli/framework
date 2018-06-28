package com.github.hualuomoli.sample.gateway.server.biz.type.entity;

import com.github.hualuomoli.sample.gateway.server.biz.gateway.enums.StatusEnum;

public class InArrayResponse {

  private StatusEnum status;

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "InArrayResponse [status=" + status + "]";
  }

}
