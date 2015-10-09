package com.appleframework.qos.server.statistics.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.orm.mybatis.query.PageQuery;
import com.appleframework.qos.server.core.entity.DayStatMethod;

@Repository
public interface DayStatMethodDao {
	
	public void createTable();
	
	public void insert(DayStatMethod dsm);
	
	public void update(DayStatMethod dsm);
    	
	public DayStatMethod getByDate(MapQuery query);
	
	public List<DayStatMethod> findPage(PageQuery query);
	
}