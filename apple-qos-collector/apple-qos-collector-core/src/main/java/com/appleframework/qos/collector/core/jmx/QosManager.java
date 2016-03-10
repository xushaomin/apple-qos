package com.appleframework.qos.collector.core.jmx;

import com.appleframework.qos.collector.core.utils.Constants;

/**
 * Main. (API, Static, ThreadSafe)
 * 
 * @author Cruise.Xu
 */
public class QosManager implements QosManagerMBean {
	
	@Override
	public String getName() {
		return "QosManager";
	}
	
	@Override
	public void start() {
		Constants.COLLECT_START = true;
	}

	@Override
	public void stop() {
		Constants.COLLECT_START = false;
		
	}

	@Override
	public boolean isRunning() {
		return Constants.COLLECT_START;
	}
   
}