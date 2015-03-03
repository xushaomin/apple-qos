package com.appleframework.qos.server.statistics.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.qos.server.statistics.dao.DayStatNodeDao;
import com.appleframework.qos.server.core.entity.DayStatNode;
import com.appleframework.qos.server.statistics.service.DayStatNodeService;

@Service("dayStatNodeService")
@Lazy(false)
public class DayStatNodeServiceImpl implements DayStatNodeService {

	@Resource
	private DayStatNodeDao dayStatNodeDao;
	
	@PostConstruct
	public void createTable() {
		dayStatNodeDao.createTable();
	}

	public void save(DayStatNode dsc) {
		dsc.setCreateTime(new Date());
		dayStatNodeDao.insert(dsc);
	}
	
	public void update(DayStatNode dsc) {
		dsc.setUpdateTime(new Date());
		dayStatNodeDao.update(dsc);
	}
	
	public DayStatNode getByDate(Date date, Long consumerAppId, Long providerAppId, Long nodeId) {
		MapQuery query = MapQuery.create();
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("providerNodeId", nodeId);
		query.addParameters("statDate", date);
		return dayStatNodeDao.getByDate(query);
	}

}