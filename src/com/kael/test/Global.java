package com.kael.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Global
{
  public static final String label = "kael.net";
  public static final String l1 = "1";
  public static final String l2 = "2";
  public static final String PORT = "8888";
  public static final String IP = "192.168.2.30";
  public static Map<Integer, NettyConnector> Connections = new ConcurrentHashMap();
  protected static Global _instance = null;


  public static synchronized Global getInstance()
  {
    if (_instance == null)
    {
      _instance = new Global();
    }
    return _instance;
  }
}