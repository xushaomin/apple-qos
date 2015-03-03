package com.appleframework.qos.server.statistics.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.orm.mybatis.query.PageQuery;
import com.appleframework.qos.server.core.entity.MinStat;

@Repository
public interface MinStatDao {
	
	public List<MinStat> findPage(PageQuery query);
	
}