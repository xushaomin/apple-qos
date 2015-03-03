package com.appleframework.qos.server.statistics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.qos.server.core.entity.MinCollect;
import com.appleframework.qos.server.statistics.dao.MinCollectDao;
import com.appleframework.qos.server.statistics.model.DaySumApp;
import com.appleframework.qos.server.statistics.model.DaySumCode;
import com.appleframework.qos.server.statistics.model.DaySumNode;
import com.appleframework.qos.server.statistics.model.DaySumMethod;
import com.appleframework.qos.server.statistics.service.MinCollectService;

@Service("minCollectService")
public class MinCollectServiceImpl implements MinCollectService {

	@Resource
	private MinCollectDao minCollectDao;	
	
	public List<MinCollect> findAppByDate(Date date) {
		return minCollectDao.findAppByDate(date);
	}
	
	public List<MinCollect> findCodeByDate(Date collectDate) {
		return minCollectDao.findCodeByDate(collectDate);
	}
	
	public List<MinCollect> findNodeByDate(Date date) {
		return minCollectDao.findNodeByDate(date);
	}
	
	public List<MinCollect> findMethodByDate(Date date) {
		return minCollectDao.findMethodByDate(date);
	}
	
	public DaySumApp sumSuccessByApp(Date collectDate, Long consumerAppId, Long providerAppId) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("collectDate", collectDate);
		return minCollectDao.sumSuccessByApp(query);
	}
	
	public DaySumApp sumFailureByApp(Date collectDate, Long consumerAppId, Long providerAppId) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("collectDate", collectDate);
		return minCollectDao.sumFailureByApp(query);
	}

	public DaySumCode sumCodeByApp(Date collectDate, Long consumerAppId, Long providerAppId, String errorCode) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("collectDate", collectDate);
		query.addParameters("errorCode", errorCode);
		return minCollectDao.sumCodeByApp(query);
	}
	
	public DaySumNode sumNodeByApp(Date collectDate, Long consumerAppId, Long providerAppId, Long nodeId) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("collectDate", collectDate);
		query.addParameters("providerNodeId", nodeId);
		return minCollectDao.sumNodeByApp(query);
	}
	
	public DaySumMethod sumSuccessByMethod(Date collectDate, Long consumerAppId, Long providerAppId,
			String service, String method) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("service", service);
		query.addParameters("method", method);
		query.addParameters("collectDate", collectDate);
		return minCollectDao.sumSuccessByMethod(query);
	}
	
	public DaySumMethod sumFailureByMethod(Date collectDate, Long consumerAppId, Long providerAppId, 
			String service, String method) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("service", service);
		query.addParameters("method", method);
		query.addParameters("collectDate", collectDate);
		return minCollectDao.sumFailureByMethod(query);
	}
}
