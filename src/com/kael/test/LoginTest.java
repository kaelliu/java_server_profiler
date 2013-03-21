package com.kael.test;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.kael.protocol.LoginProtocol;

public class LoginTest extends AbstractJavaSamplerClient{
	private SampleResult sr;
	public void setupTest(JavaSamplerContext arg0)
	  {
	    System.out.println("setupTest");
	  }

	  public Arguments getDefaultParameters()
	  {
	    Arguments params = new Arguments();
	    params.addArgument("ip", "192.168.2.30");
	    params.addArgument("port", "8888");
	    return params;
	  }
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		this.sr = new SampleResult();
	    this.sr.setSampleLabel("8");
	    NettyConnector mc = new NettyConnector();

	    mc.setupSocket(arg0.getParameter("ip"), arg0.getParameter("port"));
	    int threadID = (int)Thread.currentThread().getId();
	    Global.Connections.put(Integer.valueOf(threadID), mc);
	    
	    try
	    {
	      LoginProtocol lrp = new LoginProtocol();
	      lrp.setId(1);
	      lrp.setNm("robot_kael_"+Global.Connections.size());
	      mc.setCurrentTargetCmd(lrp.getCmd());
	      while (!mc.send(lrp))
	      {
	        Thread.sleep(100L);
	      }
	      this.sr.sampleStart();
	      while (!mc.isMsgon())
	      {
	        Thread.sleep(100L);
	      }
	      mc.setCurrentTargetCmd(0);
	      mc.setMsgon(false);

	      this.sr.setSuccessful(true);
	      System.out.println("end");
	    }
	    catch (Throwable e)
	    {
	      e.printStackTrace();
	      this.sr.setSuccessful(false);
	    }
	    finally
	    {
	      this.sr.sampleEnd();
	    }
		return sr;
	}
	public void teardownTest(JavaSamplerContext arg0)
	{
	}
}
