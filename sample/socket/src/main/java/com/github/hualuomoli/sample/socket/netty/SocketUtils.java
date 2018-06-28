package com.github.hualuomoli.sample.socket.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SocketUtils {

  /**
   * 存储每一个客户端接入进来时的channel对象
   */
  private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  public static void add(Channel channel) {
    group.add(channel);
  }

  public static void remove(Channel channel) {
    group.remove(channel);
  }

  public static void write(Channel channel, Object data) {
    channel.write(data);
  }

  public static void writeAndFlush(Channel channel, Object data) {
    channel.writeAndFlush(data);
  }

  public static void write(Object data) {
    group.write(data);
  }

  public static void writeAndFlush(Object data) {
    group.writeAndFlush(data);
  }

  // 读取数据
  public static byte[] read(ByteBuf buf) {
    byte[] data = new byte[buf.readableBytes()];
    buf.readBytes(data);
    return data;
  }

}
