package com.appleframework.qos.server.monitor.service.impl;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.qos.server.core.entity.AppInfo;
import com.appleframework.qos.server.monitor.dao.AppInfoDao;
import com.appleframework.qos.server.monitor.service.AppInfoService;

@Service("appInfoService")
@Lazy(false)
public class AppInfoServiceImpl implements AppInfoService {

	private final ConcurrentMap<String, AtomicReference<AppInfo>> appInfoMap 
			= new ConcurrentHashMap<String, AtomicReference<AppInfo>>();

	@Resource
	private AppInfoDao appInfoDao;

	public void setAppInfoDao(AppInfoDao appInfoDao) {
		this.appInfoDao = appInfoDao;
	}

	public AppInfo getByCode(String code) {
		return appInfoDao.getByCode(code);
	}

	public void insert(AppInfo appInfo) {
		appInfoDao.insert(appInfo);
	}

	public Long genByCode(String code) {
		AtomicReference<AppInfo> reference = appInfoMap.get(code);
		if (reference == null) {
			appInfoMap.putIfAbsent(code, new AtomicReference<AppInfo>());
			reference = appInfoMap.get(code);
		}

		AppInfo current = reference.get();
		AppInfo update = null;

		if (null == current) {
			update = this.getByCode(code);
			if (null != update) {
				reference.compareAndSet(current, update);
				return update.getId();
			} else {
				update = new AppInfo();
				update.setCode(code);
				update.setName(code);
				update.setDisorder(100);
				update.setState((short)1);
				update.setCreateTime(new Date());

				this.insert(update);
				reference.compareAndSet(current, update);
				return update.getId();
			}
		} else {
			return current.getId();
		}

	}

	@PostConstruct
	public void createTable() {
		appInfoDao.createTable();
	}
	
}
