package com.appleframework.qos.server.statistics.service;

import java.util.Date;
import java.util.List;

import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.core.entity.DayStatApp;

public interface DayStatAppService {
	
	public void createTable();

	public void save(DayStatApp dsa);
	
	public void update(DayStatApp dsa);
	
	public DayStatApp getByDate(Date date, Long consumerAppId, Long providerAppId);
	
	public List<DayStatApp> findListByDate(Date date, 
			String consumerAppName, String providerAppName);
	
	public Pagination findPageByBetweenDate(Pagination page, 
			Date startDate, Date endDate, String consumerAppName, String providerAppName);
	
	public Pagination findPageByDate(Pagination page, 
			Date startDate, String consumerAppName, String providerAppName);
}
