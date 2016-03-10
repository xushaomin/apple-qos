package com.appleframework.qos.collector.core;

import java.lang.management.ManagementFactory;
import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.appleframework.qos.collector.core.jmx.QosManager;

/**
 * 
 * JMX初始化
 * 
 */
public class JmxInit {

	private static Logger logger = Logger.getLogger(JmxInit.class);

	/* 默认的Type */
	public static final String DEFAULT_TYPE = "container";
	/* type的key */
	public static final String TYPE_KEY = "type";
	/* name的key */
	public static final String ID_KEY = "id";
	/* id的key */
	public static final String DEFAULT_ID = "QosManager";

	public void init() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try {
			Hashtable<String, String> properties = new Hashtable<String, String>();
			properties.put(TYPE_KEY, DEFAULT_TYPE);
			properties.put(ID_KEY, DEFAULT_ID);

			ObjectName oname = ObjectName.getInstance("com.appleframework", properties);
			QosManager mbean = new QosManager();

			if (mbs.isRegistered(oname)) {
				mbs.unregisterMBean(oname);
			}
			mbs.registerMBean(mbean, oname);

		} catch (Exception e) {
			logger.error("注册JMX服务出错：" + e.getMessage(), e);
		}

	}

}
