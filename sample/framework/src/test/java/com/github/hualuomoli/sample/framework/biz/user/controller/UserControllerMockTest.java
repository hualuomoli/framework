package com.github.hualuomoli.sample.framework.biz.user.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.hualuomoli.sample.framework.ControllerMockTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerMockTest extends ControllerMockTest {

  @Test
  public void testJson() throws Exception {
    mockMvc
        .perform(this.urlEncoded("/user/json")//
            .param("id", "1234"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk());
  }

  @Test
  public void testView() throws Exception {
    mockMvc.perform(this.get("/user/view"))//
        .andDo(this.print()) //
        .andDo(this.forwardedUrl()) //
        .andExpect(this.isOk());
  }

  @Test
  public void testFind() throws Exception {
    mockMvc
        .perform(this.urlEncoded("/user/find")//
            .param("username", "hualuomoli"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk());
  }

}
