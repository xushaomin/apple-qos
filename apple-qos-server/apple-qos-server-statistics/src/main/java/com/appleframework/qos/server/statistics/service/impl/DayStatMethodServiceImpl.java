package com.appleframework.qos.server.statistics.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.qos.server.core.entity.DayStatMethod;
import com.appleframework.qos.server.statistics.dao.DayStatMethodDao;
import com.appleframework.qos.server.statistics.model.DayStatMethodSo;
import com.appleframework.qos.server.statistics.service.DayStatMethodService;

@Service("dayStatMethodService")
@Lazy(false)
public class DayStatMethodServiceImpl implements DayStatMethodService {

	@Resource
	private DayStatMethodDao dayStatMethodDao;
	
	@PostConstruct
	public void createTable() {
		dayStatMethodDao.createTable();
	}

	public void save(DayStatMethod dsa) {
		dsa.setCreateTime(new Date());
		dayStatMethodDao.insert(dsa);
	}
	
	public void update(DayStatMethod dsa) {
		dsa.setUpdateTime(new Date());
		dayStatMethodDao.update(dsa);
	}
	
	public DayStatMethod getByDate(Date statDate, Long consumerAppId, Long providerAppId,
			String service, String method) {
		MapQuery query = MapQuery.create();
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("service", service);
		query.addParameters("method", method);
		query.addParameters("statDate", statDate);
		return dayStatMethodDao.getByDate(query);
	}
	
	public Pagination findPage(Pagination page, DayStatMethodSo so, Search se) {
		page.setList(dayStatMethodDao.findPage(page, so, se));
		return page;
	}
	
}