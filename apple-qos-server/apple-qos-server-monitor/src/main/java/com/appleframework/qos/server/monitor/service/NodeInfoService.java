package com.appleframework.qos.server.monitor.service;

import com.appleframework.qos.server.core.entity.NodeInfo;

public interface NodeInfoService {

	public NodeInfo getByIp(String ip);

	public Long genByIp(String ip);
	
	public void createTable();
}
