package com.appleframework.qos.collector.core;

import java.io.Serializable;
import java.util.Date;

import com.appleframework.qos.collector.core.URL;
import com.appleframework.qos.collector.core.utils.Constants;
import com.appleframework.qos.collector.core.utils.DateFormatUtils;
import com.appleframework.qos.collector.core.utils.Md5Encrypt;
import com.appleframework.qos.collector.core.utils.TimestampUtils;


public class Statistic implements Serializable {

	private static final long serialVersionUID = 2553284210080638200L;

	private Integer type;
	private String host;
	private int port = 0;

	private String providerApp;
	private String providerAdd;
	private String consumerApp;
	private String consumerAdd;

	private String service;
	private String method;

	private String errorCode = "0";

	private int success = 0;
	private int failure = 0;
	private int elapsed = 0;
	private int maxElapsed = 0;
		
	private String timestamp;

	public Statistic(URL url, int minType) {
		this.providerApp = url.getParameter(Constants.KEY_PROVIDER_APP);
		this.consumerApp = url.getParameter(Constants.KEY_CONSUMER_APP);
		this.providerAdd = url.getParameter(Constants.KEY_PROVIDER_ADD, url.getAddress());
		this.consumerAdd = url.getParameter(Constants.KEY_CONSUMER_ADD, url.getAddress());

		this.service = url.getParameter(Constants.KEY_INTERFACE);
		this.method = url.getParameter(Constants.KEY_METHOD);

		this.errorCode = url.getParameter(Constants.KEY_ERROR_CODE, "0");
		this.timestamp = url.getParameter(Constants.KEY_TIMESTAMP);
		
		if(timestamp == null) {
			//1分钟统计
			timestamp = DateFormatUtils.toString(new Date(), DateFormatUtils.pattern12);
		}
		
		//5分钟统计
		if(minType == 5) {
			timestamp = TimestampUtils.genFiveMinuteTimestamp(timestamp);
		}
		else if (minType == 10) {
			timestamp = TimestampUtils.genTenMinuteTimestamp(timestamp);
		}
		else {
			
		}
		
		this.host = url.getHost();
		this.port = url.getPort();
		this.type = url.getParameter(Constants.KEY_COLLECT_TYPE, Constants.COLLECT_TYPE_CONSUMER);
	}

	public Statistic() {
	}

	public String getProviderApp() {
		return providerApp;
	}

	public void setProviderApp(String providerApp) {
		this.providerApp = providerApp;
	}

	public String getProviderAdd() {
		return providerAdd;
	}

	public void setProviderAdd(String providerAdd) {
		this.providerAdd = providerAdd;
	}

	public String getConsumerApp() {
		return consumerApp;
	}

	public void setConsumerApp(String consumerApp) {
		this.consumerApp = consumerApp;
	}

	public String getConsumerAdd() {
		return consumerAdd;
	}

	public void setConsumerAdd(String consumerAdd) {
		this.consumerAdd = consumerAdd;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFailure() {
		return failure;
	}

	public void setFailure(Integer failure) {
		this.failure = failure;
	}

	public Integer getElapsed() {
		return elapsed;
	}

	public void setElapsed(Integer elapsed) {
		this.elapsed = elapsed;
	}

	public Integer getMaxElapsed() {
		return maxElapsed;
	}

	public void setMaxElapsed(Integer maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public void setElapsed(int elapsed) {
		this.elapsed = elapsed;
	}

	public void setMaxElapsed(int maxElapsed) {
		this.maxElapsed = maxElapsed;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String toIdentityKey() {
		if(type == Constants.COLLECT_TYPE_CONSUMER)
			return this.toConsumerIdentityKey();
		else if(type == Constants.COLLECT_TYPE_PROVIDER)
			return this.toProviderIdentityKey();
		else
			return this.toLocalIdentityKey();
	}	
	
	public String toLocalIdentityKey() {
		StringBuilder buf = new StringBuilder();
		buf.append(Constants.KEY_CONSUMER_APP);
		buf.append("=");
		buf.append(this.consumerApp);
		buf.append("&");
		if (null != this.providerApp) {
			buf.append(Constants.KEY_PROVIDER_APP);
			buf.append("=");
			buf.append(this.providerApp);
			buf.append("&");
		}
		if (null != this.service) {
			buf.append(Constants.KEY_INTERFACE);
			buf.append("=");
			buf.append(this.service);
			buf.append("&");
		}
		if (null != this.method) {
			buf.append(Constants.KEY_METHOD);
			buf.append("=");
			buf.append(this.method);
			buf.append("&");
		}
		buf.append(Constants.KEY_ERROR_CODE);
		buf.append("=");
		buf.append(this.errorCode);
		buf.append("&");
		buf.append(Constants.KEY_TIMESTAMP);
		buf.append("=");
		buf.append(this.timestamp);
		return buf.toString();
	}
	
	@Override
	public String toString() {
		return "Statistic [type=" + type + ", host=" + host + ", port=" + port
				+ ", providerApp=" + providerApp + ", providerAdd="
				+ providerAdd + ", consumerApp=" + consumerApp
				+ ", consumerAdd=" + consumerAdd + ", service=" + service
				+ ", method=" + method + ", errorCode=" + errorCode
				+ ", success=" + success + ", failure=" + failure
				+ ", elapsed=" + elapsed + ", maxElapsed=" + maxElapsed
				+ ", timestamp=" + timestamp + "]";
	}

	public String toConsumerIdentityKey() {
		StringBuilder buf = new StringBuilder();
		buf.append(Constants.KEY_CONSUMER_APP);
		buf.append("=");
		buf.append(this.consumerApp);
		buf.append("&");
		if (null != this.providerApp) {
			buf.append(Constants.KEY_PROVIDER_APP);
			buf.append("=");
			buf.append(this.providerApp);
			buf.append("&");
		}
		if (null != this.service) {
			buf.append(Constants.KEY_INTERFACE);
			buf.append("=");
			buf.append(this.service);
			buf.append("&");
		}
		if (null != this.method) {
			buf.append(Constants.KEY_METHOD);
			buf.append("=");
			buf.append(this.method);
			buf.append("&");
		}
		buf.append(Constants.KEY_PROVIDER_ADD);
		buf.append("=");
		if (null != this.providerAdd) {
			buf.append(this.providerAdd.replace(":", "-"));
		} else {
			buf.append("");
		}
		buf.append("&");
		buf.append(Constants.KEY_ERROR_CODE);
		buf.append("=");
		buf.append(this.errorCode);
		buf.append("&");
		buf.append(Constants.KEY_TIMESTAMP);
		buf.append("=");
		buf.append(this.timestamp);
		return buf.toString();
	}
	
	public String toProviderIdentityKey() {
		StringBuilder buf = new StringBuilder();
		buf.append(Constants.KEY_CONSUMER_APP);
		buf.append("=");
		buf.append(this.consumerApp);
		buf.append("&");
		if (null != this.providerApp) {
			buf.append(Constants.KEY_PROVIDER_APP);
			buf.append("=");
			buf.append(this.providerApp);
			buf.append("&");
		}
		if (null != this.service) {
			buf.append(Constants.KEY_INTERFACE);
			buf.append("=");
			buf.append(this.service);
			buf.append("&");
		}
		if (null != this.method) {
			buf.append(Constants.KEY_METHOD);
			buf.append("=");
			buf.append(this.method);
			buf.append("&");
		}
		buf.append(Constants.KEY_CONSUMER_ADD);
		buf.append("=");
		buf.append(this.consumerAdd);
		buf.append(Constants.KEY_ERROR_CODE);
		buf.append("=");
		buf.append(this.errorCode);
		buf.append("&");
		buf.append(Constants.KEY_TIMESTAMP);
		buf.append("=");
		buf.append(this.timestamp);
		return buf.toString();
	}
	
	public String toMD5Key() {
		return Md5Encrypt.md5(toIdentityKey());
	}

}
