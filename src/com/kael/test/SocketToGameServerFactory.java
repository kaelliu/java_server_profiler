package com.kael.test;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class SocketToGameServerFactory
  implements ChannelPipelineFactory
{
  NettyConnector conn;

  public SocketToGameServerFactory(NettyConnector con)
  {
    this.conn = con;
  }

  public ChannelPipeline getPipeline() throws Exception {
    ChannelPipeline pipeline = Channels.pipeline();

    pipeline.addLast("decoder", new ServerDecoder());
    pipeline.addLast("encoder", new ServerEncoder());
    ClientHandler ch = new ClientHandler();
    ch.setConn(this.conn);
    pipeline.addLast("handler", ch);

    return pipeline;
  }
}