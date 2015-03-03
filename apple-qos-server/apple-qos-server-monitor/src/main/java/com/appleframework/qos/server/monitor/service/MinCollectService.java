package com.appleframework.qos.server.monitor.service;

import com.appleframework.qos.server.monitor.model.Collect;

public interface MinCollectService {

	public void save(Collect collect);
	
	public void createTable();
}
