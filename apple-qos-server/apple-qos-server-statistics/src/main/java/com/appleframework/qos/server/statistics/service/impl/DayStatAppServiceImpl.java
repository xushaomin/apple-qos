package com.appleframework.qos.server.statistics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.model.page.Pagination;
import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.orm.mybatis.query.PageQuery;
import com.appleframework.qos.server.core.entity.DayStatApp;
import com.appleframework.qos.server.statistics.dao.DayStatAppDao;
import com.appleframework.qos.server.statistics.service.DayStatAppService;

@Service("dayStatAppService")
@Lazy(false)
public class DayStatAppServiceImpl implements DayStatAppService {

	@Resource
	private DayStatAppDao dayStatAppDao;
	
	@PostConstruct
	public void createTable() {
		dayStatAppDao.createTable();
	}

	public void save(DayStatApp dsa) {
		dsa.setCreateTime(new Date());
		dayStatAppDao.insert(dsa);
	}
	
	public void update(DayStatApp dsa) {
		dsa.setUpdateTime(new Date());
		dayStatAppDao.update(dsa);
	}
	
	public DayStatApp getByDate(Date statDate, Long consumerAppId, Long providerAppId) {
		MapQuery query = MapQuery.create();
		query.addParameters("providerAppId", providerAppId);
		query.addParameters("consumerAppId", consumerAppId);
		query.addParameters("statDate", statDate);
		return dayStatAppDao.getByDate(query);
	}
	
	public List<DayStatApp> findListByDate(Date statDate, String consumerAppName, String providerAppName) {
		MapQuery query = MapQuery.create();
		query.addParameters("consumerAppName", consumerAppName);
		query.addParameters("providerAppName", providerAppName);
		query.addParameters("statDate", statDate);
		return dayStatAppDao.findByDate(query);
	}
	
	public Pagination findPageByBetweenDate(Pagination page, 
			Date startDate, Date endDate, String consumerAppName, String providerAppName) {
		PageQuery query = PageQuery.create(page);
		query.addParameters("consumerAppName", consumerAppName);
		query.addParameters("providerAppName", providerAppName);
		query.addParameters("startDate", startDate);
		query.addParameters("endDate", endDate);
		List<DayStatApp> list = dayStatAppDao.findPageByBetweenDate(query);
		page.setList(list);
		return page;
	}
	
	public Pagination findPageByDate(Pagination page, 
			Date date, String consumerAppName, String providerAppName) {
		PageQuery query = PageQuery.create(page);
		query.addParameters("consumerAppName", consumerAppName);
		query.addParameters("providerAppName", providerAppName);
		query.addParameters("date", date);
		List<DayStatApp> list = dayStatAppDao.findPageByBetweenDate(query);
		page.setList(list);
		return page;
	}

}
