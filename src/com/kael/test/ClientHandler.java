package com.kael.test;

import lib.kael.PojoMapper;
import org.codehaus.jackson.JsonNode;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ClientHandler extends SimpleChannelUpstreamHandler
{
  NettyConnector conn;

  public NettyConnector getConn()
  {
    return this.conn;
  }

  public void setConn(NettyConnector conn) {
    this.conn = conn;
  }

  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
  {
    if (this.conn != null)
    {
      this.conn.setChannel(e.getChannel());
    }
  }

  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e)
  {
    System.out.println("disconnected from gameserver ");
  }

  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
  {
    if (this.conn.getCurrentTargetCmd() == 512)
    {
      if (!(e.getMessage() instanceof String)) {
        return;
      }
      JsonNode rootNode = PojoMapper.toNode((String)e.getMessage());
      if (rootNode == null)
        return;
      int command = rootNode.get("cmd").getIntValue();
      if (command == this.conn.getCurrentTargetCmd())
      {
        this.conn.setMsgon(true);
      }
    }
    else if (this.conn.getCurrentTargetCmd() == 8)
    {
      if (!(e.getMessage() instanceof String)) {
        return;
      }
      JsonNode rootNode = PojoMapper.toNode((String)e.getMessage());
      if (rootNode == null)
        return;
      int command = rootNode.get("cmd").getIntValue();
      System.out.println((String)e.getMessage());
      if (command == 3501)
      {
        
      }
    }
    else {
      this.conn.setMsgon(true);
    }
  }

  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
  {
  }
}