package com.appleframework.qos.server.monitor.service;

import com.appleframework.qos.server.core.entity.MinStat;

public interface MinStatService {
	
	public MinStat getByMd5(String md5);

	public void save(MinStat minStat);
	
	public void update(MinStat minStat);
	
}
