package com.appleframework.qos.server.monitor.service;

import com.appleframework.qos.server.core.entity.AppInfo;


public interface AppInfoService {

	public AppInfo getByCode(String code);
	
	public Long genByCode(String code);
	
	public void insert(AppInfo appInfo);
	
	public void createTable();
}
