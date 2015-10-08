package com.appleframework.qos.server.statistics.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.orm.mybatis.query.PageQuery;
import com.appleframework.qos.server.core.entity.AppInfo;

@Repository
public interface AppInfoDao {
	
	public AppInfo get(Long id);
		
	public void update(AppInfo appInfo);
	
	public Integer countByCode(String code);
	
	public List<AppInfo> findPage(PageQuery query);
		    
}