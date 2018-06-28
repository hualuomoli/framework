package com.github.hualuomoli.sample.framework.biz.version.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.hualuomoli.sample.framework.ControllerMockTest;

public class VersionControllerMockTest extends ControllerMockTest {

  // 0.0.1 --> 1.0.0 --> 1.0.1

  @Test
  public void testFind_old() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "0.0.1"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("0.0.1", content);
          }

        }));
  }

  @Test
  public void testFind() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "1.0.0"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.0", content);
          }

        }));
  }

  @Test
  public void testFind_new() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "1.0.1"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.1", content);
          }

        }));
  }

  @Test
  public void testFindLessMinVersion() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "0.0.0.1"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(MockMvcResultMatchers.status().is(404));
  }

  @Test
  public void testFindBetweenVersion() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "1.0.0.6"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.0", content);
          }
        }));
  }

  @Test
  public void testFindGreaterMaxVersion() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", "5.0"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.1", content);
          }
        }));
  }

  @Test
  public void testFindNoVersion() throws Exception {
    mockMvc.perform(this.get("/version/find"))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.1", content);
          }
        }));
  }

  @Test
  public void testFindEmptyVersion() throws Exception {
    mockMvc
        .perform(this.get("/version/find")//
            .header("api-version", ""))//
        .andDo(this.print()) //
        .andDo(this.content())//
        .andExpect(this.isOk()) //
        .andDo(this.content(new Dealer() {

          @Override
          public void deal(String content) {
            Assert.assertEquals("1.0.1", content);
          }
        }));
  }

}
