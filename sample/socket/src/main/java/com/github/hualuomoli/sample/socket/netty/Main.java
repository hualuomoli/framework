package com.github.hualuomoli.sample.socket.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  /**
   * 启动socket
   * @param name socket实例名称
   * @param port 端口
   * @param binder 绑定器
   */
  public static void start(String name, int port, final Binder binder) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(); //用于处理服务器端接收客户端连接  
    EventLoopGroup workerGroup = new NioEventLoopGroup(); //进行网络通信（读写）  
    try {
      ServerBootstrap bootstrap = new ServerBootstrap(); //辅助工具类，用于服务器通道的一系列配置  
      bootstrap.group(bossGroup, workerGroup) //绑定两个线程组  
          .channel(NioServerSocketChannel.class) //指定NIO的模式  
          .childHandler(new ChannelInitializer<SocketChannel>() { //配置具体的数据处理方式  

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              binder.bind(ch.pipeline());
            }
          });
      ChannelFuture future = bootstrap.bind(port).sync();
      logger.info("{}已启动", name);
      future.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static interface Binder {
    void bind(ChannelPipeline pipeline);
  }

}
