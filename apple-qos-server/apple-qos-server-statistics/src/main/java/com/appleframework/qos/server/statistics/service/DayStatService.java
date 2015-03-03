package com.appleframework.qos.server.statistics.service;

import java.util.Date;


public interface DayStatService {
	
	public void app(Date statDate);
	
	public void code(Date statDate);
	
	public void node(Date statDate);
	
	public void method(Date statDate);
}
