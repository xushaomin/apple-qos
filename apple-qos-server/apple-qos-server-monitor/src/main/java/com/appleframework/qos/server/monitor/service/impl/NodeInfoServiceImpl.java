package com.appleframework.qos.server.monitor.service.impl;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.appleframework.qos.server.core.entity.NodeInfo;
import com.appleframework.qos.server.monitor.dao.NodeInfoDao;
import com.appleframework.qos.server.monitor.service.NodeInfoService;

@Service("nodeInfoService")
@Lazy(false)
public class NodeInfoServiceImpl implements NodeInfoService {
	
	private final ConcurrentMap<String, AtomicReference<NodeInfo>> nodeInfoMap 
		= new ConcurrentHashMap<String, AtomicReference<NodeInfo>>();

	@Resource
	private NodeInfoDao nodeInfoDao;

	public Long genByIp(String ip) {
		
		AtomicReference<NodeInfo> reference = nodeInfoMap.get(ip);
		if (reference == null) {
			nodeInfoMap.putIfAbsent(ip, new AtomicReference<NodeInfo>());
            reference = nodeInfoMap.get(ip);
        }

		NodeInfo current = reference.get();
		NodeInfo update = null;
		
		if(null == current) {
			update = this.getByIp(ip);
			if(null != update) {
				reference.compareAndSet(current, update);
				return update.getId();
			}
			else {
				update = new NodeInfo();
				update.setHost(ip);
				update.setIp(ip);
				update.setDisorder(100);
				update.setState((short)1);
				update.setCreateTime(new Date());
				nodeInfoDao.insert(update);
				reference.compareAndSet(current, update);
				return update.getId();
			}
		}
		else {
			return current.getId();
		}
	}

	public NodeInfo getByIp(String ip) {
		return nodeInfoDao.getByIp(ip);
	}
	
	@PostConstruct
	public void createTable() {
		nodeInfoDao.createTable();
	}

}
