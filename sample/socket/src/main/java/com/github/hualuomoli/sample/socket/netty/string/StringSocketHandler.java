package com.github.hualuomoli.sample.socket.netty.string;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.sample.socket.netty.SocketUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class StringSocketHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(StringSocketHandler.class);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    SocketUtils.add(ctx.channel());
    logger.info("客户端与服务端连接开启...");
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    SocketUtils.remove(ctx.channel());
    logger.info("客户端与服务端连接关闭...");
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.warn("", cause);
    ctx.close();
  }

}
