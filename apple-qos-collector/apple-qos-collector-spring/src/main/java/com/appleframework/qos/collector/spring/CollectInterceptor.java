package com.appleframework.qos.collector.spring;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.qos.collector.core.CollectApi;
import com.appleframework.qos.collector.core.LoggerInit;
import com.appleframework.qos.collector.core.utils.Constants;

public class CollectInterceptor implements MethodInterceptor {

	private List<String> ignoreBeanNames = new ArrayList<String>();
	
	static {
		if (LoggerInit.initOK == false) {
			LoggerInit.initLogger();
		}
	}

	public Object invoke(MethodInvocation method) throws Throwable {
		if(Constants.COLLECT_START == false)
			return method.proceed();
		
		String serviceKey = method.getMethod().getDeclaringClass().getName();
		if (ignoreBeanNames.contains(serviceKey)) {
			return method.proceed();
		}
		
		long start = System.currentTimeMillis(); // 记录起始时间戮
		try {
			Object result = method.proceed(); // 让调用链往下执行
			collect(method, start, false, "0");
			return result;
		} catch (Exception e) {
			collect(method, start, true, String.valueOf(e.getClass().hashCode()));
			throw e;
		}
	}

	public void setIgnoreBeanNames(List<String> ignoreBeanNames) {
		this.ignoreBeanNames = ignoreBeanNames;
	}

	// 信息采集
	private void collect(MethodInvocation methodInvocation,
			long start, boolean error, String returnCode) {
		// ---- 服务信息获取 ----
		String application = PropertyConfigurer.getString(Constants.APPLICATION_NAME);
		String service = methodInvocation.getMethod().getDeclaringClass().getName(); // 获取服务名称
		String method = methodInvocation.getMethod().getName(); // 获取方法名

		CollectApi.collect(application, service, method, start, error, returnCode);
	}

}
