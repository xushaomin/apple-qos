package com.appleframework.qos.server.statistics.service;

import java.util.Date;

import com.appleframework.qos.server.core.entity.DayStatCode;

public interface DayStatCodeService {
	
	public void createTable();

	public void save(DayStatCode dsc);
	
	public void update(DayStatCode dsc);
	
	public DayStatCode getByDate(Date date, Long consumerAppId, Long providerAppId, String errorCode);
	
	/*public List<DayStatCode> findByAppAndDay(Date startDate, Date endDate, 
			String consumerAppName, String providerAppName);
	
	public List<DayStatCode> findPageByAppAndDay(Pagination page, 
			Date startDate, Date endDate, String consumerAppName, String providerAppName);*/
}