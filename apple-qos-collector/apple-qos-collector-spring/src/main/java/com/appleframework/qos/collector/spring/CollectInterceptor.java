package com.appleframework.qos.collector.spring;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.PatternMatchUtils;

import com.appleframework.exception.AppleException;
import com.appleframework.exception.ServiceUnavailableException;
import com.appleframework.qos.collector.core.CollectApi;
import com.appleframework.qos.collector.core.LoggerInit;
import com.appleframework.qos.collector.core.utils.ApplicationUtils;
import com.appleframework.qos.collector.core.utils.Constants;

public class CollectInterceptor implements MethodInterceptor {

	private List<String> ignoreBeanNameList = new ArrayList<String>();
	
	static {
		if (LoggerInit.initOK == false) {
			LoggerInit.initLogger();
		}
	}

	public Object invoke(MethodInvocation method) throws Throwable {
		if(Constants.COLLECT_START == false)
			return method.proceed();
		
		//String serviceKey = method.getMethod().getDeclaringClass().getName();
		String serviceKey = method.getThis().getClass().getCanonicalName();
		
		if (this.ignoreBeanNameList != null) {
			for (String mappedName : this.ignoreBeanNameList) {
				if (isMatch(serviceKey, mappedName)) {
					return method.proceed();
				}
			}
		}
		
		/*if (ignoreBeanNameList.contains(serviceKey)) {
			return method.proceed();
		}*/
		long start = System.currentTimeMillis(); // 记录起始时间戮
		try {
			Object result = method.proceed(); // 让调用链往下执行
			collect(method, start, false, "0");
			return result;
		} catch (AppleException e) {
			boolean error = false;
			if(e instanceof ServiceUnavailableException) {
				error = true;
			}
			String code = e.getCode();
    		if(null == code) {
    			code = "0";
    		}
			collect(method, start, error, code);
			throw e;
		} catch (Exception e) {
			collect(method, start, true, String.valueOf(e.getClass().hashCode()));
			throw e;
		}
	}

	protected boolean isMatch(String beanName, String mappedName) {
		return PatternMatchUtils.simpleMatch(mappedName, beanName);
	}
	
	public void setIgnoreBeanNameList(List<String> ignoreBeanNameList) {
		this.ignoreBeanNameList = ignoreBeanNameList;
	}

	public void setIgnoreRegexs(String ignoreRegexs) {
		if(null != ignoreRegexs && !ignoreRegexs.equals("null")) {
			if(ignoreRegexs.replaceAll(" ", "").length() > 0) {
				String[] ignoreRegexss = ignoreRegexs.trim().split(",");
				for (String ignoreRegex : ignoreRegexss) {
					ignoreBeanNameList.add(ignoreRegex);
				}
			}
		}
	}

	// 信息采集
	private void collect(MethodInvocation methodInvocation,
			long start, boolean error, String errorCode) {
		// ---- 服务信息获取 ----
		String application = ApplicationUtils.getApplicationName();
		//String service = methodInvocation.getMethod().getDeclaringClass().getName();// 获取服务名称
		String service = methodInvocation.getThis().getClass().getCanonicalName();
		int index = service.indexOf("$$");
		if(index  > -1) {
			service = service.substring(0, index);
		}
		String method = methodInvocation.getMethod().getName(); // 获取方法名

		CollectApi.collect(application, service, method, start, error, errorCode);
	}

}

