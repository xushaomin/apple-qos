package com.appleframework.qos.server.monitor.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.qos.server.core.entity.MinStat;
import com.appleframework.qos.server.monitor.dao.MinStatDao;
import com.appleframework.qos.server.monitor.service.AppInfoService;
import com.appleframework.qos.server.monitor.service.MinStatService;

@Service("minStatService")
@Lazy(false)
public class MinStatServiceImpl implements MinStatService {

	@Resource
	private MinStatDao minStatDao;

	@Resource
	private AppInfoService appInfoService;

	public MinStat getByMd5(String md5) {
		return minStatDao.getByMd5(md5);
	}
	
	public void update(MinStat minStat) {
		minStat.setUpdateTime(new Date());
		minStatDao.update(minStat);
	}

	public void save(MinStat minStat) {
		Long providerAppId = appInfoService.genByCode(minStat.getProviderAppName());
		Long consumerAppId = appInfoService.genByCode(minStat.getConsumerAppName());
		minStat.setProviderAppId(providerAppId);
		minStat.setConsumerAppId(consumerAppId);
		minStat.setCreateTime(new Date());
		minStatDao.insert(minStat);
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
	
	@PostConstruct
	public void createTable() {
		minStatDao.createTable();
	}
	
}
