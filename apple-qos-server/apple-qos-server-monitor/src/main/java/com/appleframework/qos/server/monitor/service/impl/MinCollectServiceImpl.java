package com.appleframework.qos.server.monitor.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.server.core.entity.MinCollect;
import com.appleframework.qos.server.monitor.dao.MinCollectDao;
import com.appleframework.qos.server.monitor.model.Collect;
import com.appleframework.qos.server.monitor.service.AppInfoService;
import com.appleframework.qos.server.monitor.service.MinCollectService;
import com.appleframework.qos.server.monitor.service.NodeInfoService;

@Service("minCollectService")
@Lazy(false)
public class MinCollectServiceImpl implements MinCollectService {

	@Resource
	private MinCollectDao minCollectDao;

	@Resource
	private AppInfoService appInfoService;

	@Resource
	private NodeInfoService nodeInfoService;

	public void save(Collect collect) {
		
		Long providerAppId = appInfoService.genByCode(collect.getProviderName());
		Long consumerAppId = appInfoService.genByCode(collect.getConsumerName());
				
		Long providerNodeId = nodeInfoService.genByIp(this.removePort(collect.getProviderAddr()));
		Long consumerNodeId = nodeInfoService.genByIp(this.removePort(collect.getConsumerAddr()));

		MinCollect sc = new MinCollect();
		sc.setProviderAppId(providerAppId);
		sc.setProviderAppName(collect.getProviderName());
		sc.setProviderNodeId(providerNodeId);
		sc.setProviderAddr(collect.getProviderAddr().replace("-", ":"));

		sc.setConsumerAppId(consumerAppId);
		sc.setConsumerAppName(collect.getConsumerName());
		sc.setConsumerNodeId(consumerNodeId);
		sc.setConsumerAddr(collect.getConsumerAddr().replace("-", ":"));
		
		sc.setCreateTime(new Date());
		sc.setElapsed(collect.getElapsed());
		sc.setFailure(collect.getFailure());
		sc.setMaxElapsed(collect.getMaxElapsed());
		sc.setMethod(collect.getMethod());

		sc.setService(collect.getService());
		sc.setSuccess(collect.getSuccess());
		sc.setCollectTime(collect.getCollectTime());
		sc.setType(collect.getType());
		
		int avgElapsed = 0;
		if(collect.getSuccess() != 0) {
			avgElapsed = collect.getElapsed() / collect.getSuccess();
		}
		sc.setAvgElapsed(avgElapsed);
		sc.setErrorCode(collect.getErrorCode());
		
		String collectTime = sc.getCollectTime() + "";
		Date collectDate = DateFormatUtils.toDate(collectTime, DateFormatUtils.pattern12);

		sc.setCollectDate(collectDate);
		minCollectDao.insert(sc);
	}
	
	@PostConstruct
	public void createTable() {
		minCollectDao.createTable();
	}
		
	public String removePort(String ip) {
		int i = ip.indexOf(':');
		int j = ip.indexOf('-');
        if (i > 0 && j < 0) {
        	ip = ip.substring(0, i);
        	return ip;
        }
        else if(i < 0 && j > 0) {
        	ip = ip.substring(0, j);
        	return ip;
        }
        else {
        	return ip;
        }
	}

}
