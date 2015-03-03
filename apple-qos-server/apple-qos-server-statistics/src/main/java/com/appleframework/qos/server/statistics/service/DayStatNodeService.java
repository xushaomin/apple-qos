package com.appleframework.qos.server.statistics.service;

import java.util.Date;

import com.appleframework.qos.server.core.entity.DayStatNode;

public interface DayStatNodeService {
	
	public void createTable();

	public void save(DayStatNode dsn);
	
	public void update(DayStatNode dsn);
	
	public DayStatNode getByDate(Date date, Long consumerAppId, Long providerAppId, Long nodeId);
	
}
