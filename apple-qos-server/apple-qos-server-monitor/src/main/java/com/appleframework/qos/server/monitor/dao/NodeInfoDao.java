package com.appleframework.qos.server.monitor.dao;


import org.springframework.stereotype.Repository;

import com.appleframework.qos.server.core.entity.NodeInfo;

@Repository
public interface NodeInfoDao {
	
	public NodeInfo getByIp(String ip);
	
	public void insert(NodeInfo nodeInfo);
	
	public void createTable();
}