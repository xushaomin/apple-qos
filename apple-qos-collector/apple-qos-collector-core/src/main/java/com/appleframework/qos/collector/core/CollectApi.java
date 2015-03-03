package com.appleframework.qos.collector.core;

import java.util.Date;

import org.apache.log4j.Logger;

import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.collector.core.utils.NetUtils;

public class CollectApi {
	
	// 回调日志单独记录
    private static Logger dataLog = Logger.getLogger(LoggerInit.LOG_NAME_GOS_DATA);
    
	static {
		if(LoggerInit.initOK == false) {
			LoggerInit.initLogger();
		}
	}
	
	public static void collect(URL url) {
		dataLog.error(url.toFullString());
	}
		
	public static void collect(String application, String service, String method, 
    		long start, boolean error, String returnCode) {
		try {
			Date date = new Date(start);
			if(start - LoggerInit.logFileGenStartTime > LoggerInit.logFileGenIntervalTime) {
				LoggerInit.initOK = false;
				LoggerInit.initLogger();
			}
			String localHost = NetUtils.getLocalHost();
            // ---- 服务信息获取 ----
        	long timestamp = System.currentTimeMillis();
            long elapsed = timestamp - start; // 计算调用耗时
                    
            URL url = new URL(Constants.KEY_COUNT_PROTOCOL, localHost, 0, 
                    Constants.KEY_CONSUMER_APP, application,
                    Constants.KEY_INTERFACE, service,
                    Constants.KEY_METHOD, method,
                    error ? Constants.KEY_FAILURE : Constants.KEY_SUCCESS, "1",
                	Constants.KEY_ELAPSED, String.valueOf(elapsed),
                    Constants.KEY_ERROR_CODE, returnCode,
                    Constants.KEY_COLLECT_TYPE, String.valueOf(Constants.COLLECT_TYPE_LOCAL),
                    Constants.KEY_TIMESTAMP, DateFormatUtils.toString(date, DateFormatUtils.pattern12));
            
            collect(url);
        } catch (Exception e) {
            
        }
	}
	
	// 信息采集
    public static void collect(String consumerName, String providerName, 
    		String providerAddress, String service, String method, 
    		long start, boolean error, String errorCode) {
    	try {
    		Date date = new Date(start);
    		if(start - LoggerInit.logFileGenStartTime > LoggerInit.logFileGenIntervalTime) {
				LoggerInit.initOK = false;
				LoggerInit.initLogger();
			}
			String localHost = NetUtils.getLocalHost();
            // ---- 服务信息获取 ----
        	long timestamp = System.currentTimeMillis();
            long elapsed = timestamp - start; // 计算调用耗时
            
            URL url = new URL(Constants.KEY_COUNT_PROTOCOL, localHost, 0, 
                    Constants.KEY_CONSUMER_APP, consumerName,
                    Constants.KEY_INTERFACE, service,
                    Constants.KEY_METHOD, method,
                    Constants.KEY_PROVIDER_APP, providerName,
                    Constants.KEY_PROVIDER_ADD, providerAddress,
                    error ? Constants.KEY_FAILURE : Constants.KEY_SUCCESS, "1",
                	Constants.KEY_ELAPSED, String.valueOf(elapsed),
                    Constants.KEY_ERROR_CODE, errorCode,
                    Constants.KEY_COLLECT_TYPE, String.valueOf(Constants.COLLECT_TYPE_CONSUMER),
                    Constants.KEY_TIMESTAMP, DateFormatUtils.toString(date, DateFormatUtils.pattern12));
            collect(url);
        } catch (Exception e) {
            
        }
    }
    
	// 一般需要统计时间 方法等调用此API
	public static void collect(String consumerName, String providerName, long start) {
		try {
			Date date = new Date(start);
    		if(start - LoggerInit.logFileGenStartTime > LoggerInit.logFileGenIntervalTime) {
				LoggerInit.initOK = false;
				LoggerInit.initLogger();
			}
			String localHost = NetUtils.getLocalHost();
			long timestamp = System.currentTimeMillis();
			long elapsed = timestamp - start; // 计算调用耗时

			URL url = new URL(Constants.KEY_COUNT_PROTOCOL, localHost, 0, 
                    Constants.KEY_CONSUMER_APP, consumerName,
                    Constants.KEY_PROVIDER_APP, providerName,
                    Constants.KEY_SUCCESS, "1",
                	Constants.KEY_ELAPSED, String.valueOf(elapsed),
                    Constants.KEY_ERROR_CODE, "0",
                    Constants.KEY_COLLECT_TYPE, String.valueOf(Constants.COLLECT_TYPE_LOCAL),
                    Constants.KEY_TIMESTAMP, DateFormatUtils.toString(date, DateFormatUtils.pattern12));
			collect(url);
		} catch (Exception e) {

		}
	}
	
	// 一般需要统计次数不需要统计 方法等调用此API
	public static void collect(String consumerName, String providerName,
			boolean error, String errorCode) {
		try {
			Date date = new Date();
			long timestamp = date.getTime();

			if (timestamp - LoggerInit.logFileGenStartTime > LoggerInit.logFileGenIntervalTime) {
				LoggerInit.initOK = false;
				LoggerInit.initLogger();
			}
			String localHost = NetUtils.getLocalHost();
			// ---- 服务信息获取 ----

			URL url = new URL(Constants.KEY_COUNT_PROTOCOL, localHost, 0,
					Constants.KEY_CONSUMER_APP, consumerName,
					Constants.KEY_PROVIDER_APP, providerName,
					error ? Constants.KEY_FAILURE : Constants.KEY_SUCCESS, "1",
					Constants.KEY_ELAPSED, String.valueOf(0), Constants.KEY_ERROR_CODE,
					errorCode, Constants.KEY_COLLECT_TYPE,
					String.valueOf(Constants.COLLECT_TYPE_LOCAL),
					Constants.KEY_TIMESTAMP, DateFormatUtils.toString(date, DateFormatUtils.pattern12));

			collect(url);
		} catch (Exception e) {

		}
	}
    
}
