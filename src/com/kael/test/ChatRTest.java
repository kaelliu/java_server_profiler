package com.kael.test;

import java.io.IOException;

import lib.kael.RandomUtil;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.kael.protocol.ChatProtocol;

public class ChatRTest extends AbstractJavaSamplerClient
{
  private SampleResult sr;

  public SampleResult runTest(JavaSamplerContext arg0)
  {
    this.sr = new SampleResult();
    this.sr.setSampleLabel("144");
    int tid = (int)Thread.currentThread().getId();
    this.sr.sampleStart();
    if (Global.Connections.containsKey(Integer.valueOf(tid)))
    {
      NettyConnector mc = (NettyConnector)Global.Connections.get(Integer.valueOf(tid));

      ChatProtocol crp = new ChatProtocol();
      crp.setMsg("kael.net/"+RandomUtil.r_between(1, 1983)+".html");
      try {
        mc.send(crp);
        while (!mc.isMsgon())
        {
          Thread.sleep(100L);
        }
        mc.setMsgon(false);
        this.sr.setSuccessful(true);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    else
    {
      this.sr.setSuccessful(false);
    }
    this.sr.sampleEnd();
    return this.sr;
  }
}