package com.appleframework.qos.server.core.entity;

public class MinStat extends BaseStat {

	private static final long serialVersionUID = 6841604318045231651L;

	private Long statTime;

	private Long providerAppId;
	private String providerAppName;

	private Long consumerAppId;
	private String consumerAppName;

	private String method;
	private String service;

	private String md5;


	public Long getStatTime() {
		return statTime;
	}

	public void setStatTime(Long statTime) {
		this.statTime = statTime;
	}

	public Long getProviderAppId() {
		return providerAppId;
	}

	public void setProviderAppId(Long providerAppId) {
		this.providerAppId = providerAppId;
	}

	public String getProviderAppName() {
		return providerAppName;
	}

	public void setProviderAppName(String providerAppName) {
		this.providerAppName = providerAppName;
	}

	public Long getConsumerAppId() {
		return consumerAppId;
	}

	public void setConsumerAppId(Long consumerAppId) {
		this.consumerAppId = consumerAppId;
	}

	public String getConsumerAppName() {
		return consumerAppName;
	}

	public void setConsumerAppName(String consumerAppName) {
		this.consumerAppName = consumerAppName;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
}