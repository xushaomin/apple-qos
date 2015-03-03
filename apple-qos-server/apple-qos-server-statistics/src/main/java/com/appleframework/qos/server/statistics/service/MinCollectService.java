package com.appleframework.qos.server.statistics.service;

import java.util.Date;
import java.util.List;

import com.appleframework.qos.server.core.entity.MinCollect;
import com.appleframework.qos.server.statistics.model.DaySumApp;
import com.appleframework.qos.server.statistics.model.DaySumCode;
import com.appleframework.qos.server.statistics.model.DaySumNode;
import com.appleframework.qos.server.statistics.model.DaySumMethod;

public interface MinCollectService {
	
	public List<MinCollect> findAppByDate(Date collectDate);
	public List<MinCollect> findCodeByDate(Date collectDate);
	public List<MinCollect> findNodeByDate(Date collectDate);
	public List<MinCollect> findMethodByDate(Date collectDate);
	
	public DaySumApp sumSuccessByApp(Date day, Long consumerAppId, Long providerAppId);
	public DaySumApp sumFailureByApp(Date day, Long consumerAppId, Long providerAppId);
	
	public DaySumCode sumCodeByApp(Date day, Long consumerAppId, Long providerAppId, String code);
	public DaySumNode sumNodeByApp(Date day, Long consumerAppId, Long providerAppId, Long nodeId);
	
	public DaySumMethod sumSuccessByMethod(Date day, Long consumerAppId, Long providerAppId,
			String service, String method);
	public DaySumMethod sumFailureByMethod(Date day, Long consumerAppId, Long providerAppId,
			String service, String method);
}
