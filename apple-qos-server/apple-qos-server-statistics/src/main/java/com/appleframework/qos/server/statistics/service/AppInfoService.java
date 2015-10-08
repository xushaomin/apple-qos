package com.appleframework.qos.server.statistics.service;

import com.appleframework.exception.ServiceException;
import com.appleframework.model.page.Pagination;
import com.appleframework.qos.server.core.entity.AppInfo;

public interface AppInfoService {
	
	public AppInfo get(Long id);
	
	public void update(AppInfo appInfo) throws ServiceException;
			
	public boolean isUniqueByCode(String oldCode, String newCode);
	
	public boolean isExistByCode(String code);
	
	public Pagination findPage(Pagination page, String keyword);
}
