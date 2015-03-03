package com.appleframework.qos.server.statistics.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.appleframework.qos.server.core.entity.AppInfo;
import com.appleframework.qos.server.statistics.dao.AppInfoDao;
import com.appleframework.qos.server.statistics.service.AppInfoService;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoDao appInfoDao;

	
	public AppInfo get(Long id) {
		return appInfoDao.get(id);
	}
	
	public void update(AppInfo appInfo) {
		appInfoDao.update(appInfo);
	}
	
	public boolean isExistByCode(String code) {
	    if(this.countByCode(code) > 0) {
	    	return true;
	    } else {
			return false;
		}
	}
	
	public int countByCode(String code) {
		return appInfoDao.countByCode(code);
	}
	
	public boolean isUniqueByCode(String oldCode, String newCode) {
		if (StringUtils.equalsIgnoreCase(oldCode, newCode)) {
			return true;
		} else {
			if (this.isExistByCode(newCode)) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	/*public List<AppInfo> findPage(Pagination page, String keyword) {
		PageQuery query = PageQuery.create(page);
		query.addParameters("keyword", keyword);
		return appInfoDao.findPage(query);
	}*/

}
