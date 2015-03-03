package com.appleframework.qos.server.statistics.dao;

import org.springframework.stereotype.Repository;

import com.appleframework.orm.mybatis.query.MapQuery;
import com.appleframework.qos.server.core.entity.DayStatNode;


@Repository
public interface DayStatNodeDao {
	
	public void createTable();
	
	public void insert(DayStatNode dsn);
	
	public void update(DayStatNode dsn);
    	
	public DayStatNode getByDate(MapQuery query);
	
	/*public List<DayStatNode> findByAppAndDay(Map<String, Object> param);

	public List<DayStatNode> findPageByAppAndDay(PageQuery query);*/

}