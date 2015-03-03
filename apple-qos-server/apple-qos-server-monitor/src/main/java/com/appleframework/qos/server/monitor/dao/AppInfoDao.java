package com.appleframework.qos.server.monitor.dao;


import org.springframework.stereotype.Repository;

import com.appleframework.qos.server.core.entity.AppInfo;


@Repository
public interface AppInfoDao {
	
	public AppInfo getByCode(String code);
	
	public void insert(AppInfo appInfo);
	
	public void createTable();
		    
}