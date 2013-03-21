package com.kael.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class NettyConnector
{
  private Channel channel = null;
  ClientBootstrap bootstrap;
  private boolean msgon = false;
  private int mid;
  private int currentTargetCmd;
  private int rid;
  private String conf_ip;
  private String conf_port;

  public int getCurrentTargetCmd()
  {
    return this.currentTargetCmd;
  }

  public void setCurrentTargetCmd(int currentTargetCmd) {
    this.currentTargetCmd = currentTargetCmd;
  }

  public boolean isMsgon() {
    return this.msgon;
  }

  public void setMsgon(boolean msgon) {
    this.msgon = msgon;
  }

  public Channel getChannel() {
    return this.channel;
  }

  public void setChannel(Channel channel) {
    this.channel = channel;
  }

  public int getRid()
  {
    return this.rid;
  }

  public void setRid(int rid) {
    this.rid = rid;
  }

  public NettyConnector()
  {
    this.bootstrap = new ClientBootstrap(
      new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
  }

  public void setupSocket(String ip, String port)
  {
    this.bootstrap.setPipelineFactory(new SocketToGameServerFactory(this));
    this.bootstrap.connect(new InetSocketAddress(ip, Integer.parseInt(port)));
  }

  public boolean send(Object obj)
    throws IOException
  {
    if (this.channel != null)
    {
      this.channel.write(obj);
      return true;
    }

    return false;
  }

  public void closeAll()
  {
    this.channel.close();
    this.bootstrap.releaseExternalResources();
  }

  public void setConf_ip(String conf_ip) {
    this.conf_ip = conf_ip;
  }

  public String getConf_ip() {
    return this.conf_ip;
  }

  public void setConf_port(String conf_port) {
    this.conf_port = conf_port;
  }

  public String getConf_port() {
    return this.conf_port;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  public int getMid() {
    return this.mid;
  }
}