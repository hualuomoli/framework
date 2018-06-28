package com.github.hualuomoli.sample.socket.netty.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.sample.socket.netty.SocketUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class HttpSocketHandler extends ChannelInboundHandlerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(HttpSocketHandler.class);

  private final String webSocketURL;
  private WebSocketServerHandshaker handshaker;

  public HttpSocketHandler(String webSocketURL) {
    this.webSocketURL = webSocketURL;
  }

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

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    if (msg instanceof FullHttpRequest) { // 握手
      this.shake(ctx, (FullHttpRequest) msg);
    } else if (msg instanceof TextWebSocketFrame) { // 文本消息
      this.text(ctx, (TextWebSocketFrame) msg);
    } else if (msg instanceof PingWebSocketFrame) { // 二进制消息
      this.ping(ctx, (PingWebSocketFrame) msg);
    } else if (msg instanceof CloseWebSocketFrame) {
      this.close(ctx, (CloseWebSocketFrame) msg);
    } else {
      logger.debug(msg.getClass().getName());
      throw new RuntimeException(msg.getClass().getName());
    }
    // end
  }

  // 文本
  protected void text(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
  }

  // ping
  private void ping(ChannelHandlerContext ctx, PingWebSocketFrame frame) {
    ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
  }

  // 关闭连接
  private void close(ChannelHandlerContext ctx, CloseWebSocketFrame frame) {
    if (handshaker == null) {
      return;
    }
    handshaker.close(ctx.channel(), frame.retain());
  }

  // 握手请求
  private void shake(ChannelHandlerContext ctx, FullHttpRequest req) {
    if (!req.decoderResult().isSuccess() || !("websocket".equals(req.headers().get("Upgrade")))) {
      // 不支持
      this.notSupport(ctx);
      return;
    }

    WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(webSocketURL, null, false);
    handshaker = wsFactory.newHandshaker(req);
    if (handshaker == null) {
      WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
    } else {
      handshaker.handshake(ctx.channel(), req);
    }

  }

  // 不支持
  private void notSupport(ChannelHandlerContext ctx) {
    DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);

    if (res.status().code() != HttpResponseStatus.OK.code()) {
      ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
      res.content().writeBytes(buf);
      buf.release();
    }

    // 服务端向客户端发送数据
    ChannelFuture f = ctx.channel().writeAndFlush(res);
    if (res.status().code() != HttpResponseStatus.OK.code()) {
      f.addListener(ChannelFutureListener.CLOSE);
    }

  }

}
