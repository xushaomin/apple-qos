package com.appleframework.qos.server.statistics.service;

import java.util.Date;

import com.appleframework.qos.server.core.entity.DayStatMethod;

public interface DayStatMethodService {
	
	public void createTable();

	public void save(DayStatMethod dsm);
	
	public void update(DayStatMethod dsm);
	
	public DayStatMethod getByDate(Date statDate, Long consumerAppId, Long providerAppId,
			String service, String method);
	
}