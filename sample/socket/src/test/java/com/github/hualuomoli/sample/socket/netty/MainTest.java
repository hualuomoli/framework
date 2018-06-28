package com.github.hualuomoli.sample.socket.netty;

import java.nio.charset.Charset;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.sample.socket.netty.Main.Binder;
import com.github.hualuomoli.sample.socket.netty.http.HttpSocketHandler;
import com.github.hualuomoli.sample.socket.netty.string.StringSocketHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MainTest {

  private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

  private static final int port = 8888;

  @Test
  public void testHttp() {
    Main.start("http", port, new Binder() {

      @Override
      public void bind(ChannelPipeline pipeline) {
        pipeline.addLast("http-codec", new HttpServerCodec()); // 
        // pipeline.addLast("http-decoder", new HttpRequestDecoder()); // 字节解码成HTTP请求
        // pipeline.addLast("http-encoder", new HttpResponseEncoder()); // HTTP响应编码成字节
        pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536)); // 组装成一个完整的HTTP请求或者响应
        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // 处理大文件传输
        pipeline.addLast("handler", new HttpSocketHandler("ws://localhost:8888") { // 自定义处理器
          @Override
          protected void text(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
            if ("error".equalsIgnoreCase(frame.text())) {
              throw new RuntimeException("运行时异常");
            }
            logger.info("客户端请求信息={}", frame.text());
            SocketUtils.write(ctx.channel(), new TextWebSocketFrame("服务端返回的信息"));
          }
        }); // 自定义处理器
      }
    });
    // end
  }

  @Test
  public void testString() {
    Main.start("string", port, new Binder() {

      @Override
      public void bind(ChannelPipeline pipeline) {
        pipeline.addLast("string-decoder", new StringDecoder(Charset.forName("GBK"))); // 解码
        pipeline.addLast("string-encoder", new StringEncoder(Charset.forName("GBK"))); // 编码

        pipeline.addLast("handler", new StringSocketHandler() {// 自定义处理器
          @Override
          public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String data = (String) msg;
            logger.info("客户端请求信息={}", data);
            SocketUtils.write(ctx.channel(), "服务端返回的信息");
          }
        }); // 自定义处理器
      }
    });
    // end
  }

}
